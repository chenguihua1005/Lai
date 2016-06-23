/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.easemob.easeui.model.EaseNotifier;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;
import com.easemob.util.NetUtils;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.view.LoginActivity;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_conversation_list)
public class ConversationListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener, EMEventListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.lin)
    LinearLayout lin;

    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;

    // 未读消息textview
    private TextView unreadLabel;
    private EaseUI easeUI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("会话列表");
        tv_right.setText("通讯录");
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        easeUI = EaseUI.getInstance();
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            ChatHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
        registerBroadcastReceiver();
        EaseUI.getInstance().getNotifier().reset();
        conversationListFragment = new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lin, conversationListFragment).show(conversationListFragment)
                .commit();
    }

    /**
     * 监听事件
     */
    @Override
    public void onEvent(EMNotifierEvent event) {
        switch (event.getEvent()) {
            case EventNewMessage: // 普通消息
                EMMessage message = (EMMessage) event.getData();
                // 提示新消息
                ChatHelper.getInstance().getNotifier().onNewMsg(message);

                refreshUIWithMessage();
                break;
            case EventOfflineMessage: {
                refreshUIWithMessage();
                break;
            }

            case EventConversationListChanged: {
                refreshUIWithMessage();
                break;
            }
            case EventNewCMDMessage:

                break;
            case EventReadAck:
                // TODO 这里当此消息未加载到内存中时，ackMessage会为null，消息的删除会失败
                EMMessage ackMessage = (EMMessage) event.getData();
                EMConversation conversation = EMChatManager.getInstance().getConversation(ackMessage.getTo());
                // 判断接收到ack的这条消息是不是阅后即焚的消息，如果是，则说明对方看过消息了，对方会销毁，这边也删除(现在只有txt iamge file三种消息支持 )
                if (ackMessage.getBooleanAttribute(EaseConstant.EASE_ATTR_READFIRE, false)
                        && (ackMessage.getType() == EMMessage.Type.TXT
                        || ackMessage.getType() == EMMessage.Type.VOICE
                        || ackMessage.getType() == EMMessage.Type.IMAGE)) {
                    // 判断当前会话是不是只有一条消息，如果只有一条消息，并且这条消息也是阅后即焚类型，当对方阅读后，这边要删除，会话会被过滤掉，因此要加载上一条消息
                    if (conversation.getAllMessages().size() == 1 && conversation.getLastMessage().getMsgId().equals(ackMessage.getMsgId())) {
                        if (ackMessage.getChatType() == EMMessage.ChatType.Chat) {
                            conversation.loadMoreMsgFromDB(ackMessage.getMsgId(), 1);
                        } else {
                            conversation.loadMoreGroupMsgFromDB(ackMessage.getMsgId(), 1);
                        }
                    }
                    conversation.removeMessage(ackMessage.getMsgId());
                }
                refreshUIWithMessage();
                break;
            default:
                break;
        }
    }

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        });
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(Constant.REFRESH_GROUP_MONEY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
                String action = intent.getAction();
                if (action.equals(Constant.REFRESH_GROUP_MONEY_ACTION)) {
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();

        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }


    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    @Override
    public void onResume() {
        super.onResume();

        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{
                        EMNotifierEvent.Event.EventNewMessage,
                        EMNotifierEvent.Event.EventOfflineMessage,
                        EMNotifierEvent.Event.EventConversationListChanged,
                        EMNotifierEvent.Event.EventNewCMDMessage,
                        EMNotifierEvent.Event.EventReadAck
                });
        EaseUI.getInstance().pushActivity(this);
        // if push service available, connect will be disconnected after app in background
        // after activity restore to foreground, reconnect
        if (!EMChatManager.getInstance().isConnected() && NetUtils.hasNetwork(this)) {
            EMChatManager.getInstance().reconnect();
        }
    }

    @Override
    protected void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        EaseUI.getInstance().popActivity(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }


    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;
    private ConversationListFragment conversationListFragment;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        ChatHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!ConversationListActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(ConversationListActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
                        startActivity(new Intent(ConversationListActivity.this, LoginActivity.class));
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e("", "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }

    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        ChatHelper.getInstance().logout(false, null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!ConversationListActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(ConversationListActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage("此用户已被移除");
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        startActivity(new Intent(ConversationListActivity.this, LoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e("", "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //getMenuInflater().inflate(R.menu.context_tab_contact, menu);
    }

    //发送消息方法
    //==========================================================================
    protected void sendTextMessage(String content, String toChatUsername,EMConversation conversation,int type) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        sendMessage(message,conversation,type);
    }

    protected void sendMessage(EMMessage message,EMConversation conversation,int type) {
        ChatUserModel chatUserModel = ChatUserInfoModel.getInstance().getUser();
        message.setAttribute("nickname", chatUserModel.getUserName());
        message.setAttribute("avatarURL", chatUserModel.getUserPhone());
        message.setAttribute("userId", chatUserModel.getUserId());

        //发送消息
        EMChatManager.getInstance().sendMessage(message, null);

        if (TextUtils.isEmpty(conversation.getExtField())) {
            setProfile(conversation,type);
        }
    }
    protected void setProfile(EMConversation conversation,int type) {
        String name;
        String photo;
        if(type==1){
            name = "jarvis0105";
            photo = "https://o8nbxcohc.qnssl.com/testimage.png";
        }else {
            name="jarvis0106";
            photo = "http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=QQ%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3713114485,4026763287&os=1327419601,3327449670&simid=4183989323,678832023&pn=0&rn=1&di=15537223790&ln=1000&fr=&fmq=1466411200910_R&fm=rs10&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=&istype=0&ist=&jit=&bdtype=0&gsm=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fwww.onegreen.net%2FQQ%2FUploadFiles%2F201006%2F2010062617333267.jpg&rpstart=0&rpnum=0&ctd=1466411205736^3_1349X667%1";
        }
        conversation.setExtField(name + "," + photo);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(this,ContantListActivity.class));
//                Intent intent = new Intent(ConversationListActivity.this, ChatActivity.class);
//                intent.putExtra(Constant.EXTRA_USER_ID, "18261576083");
//                intent.putExtra("name", "fdsfsdf");
//                intent.putExtra("photo", "https://o8nbxcohc.qnssl.com/testimage.png");
//                startActivity(intent);
//                new Thread(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                EMConversation conversation = EMChatManager.getInstance().getConversation("jarvis0105");
//                                conversation.markAllMessagesAsRead();
//                                sendTextMessage("test", "jarvis0105",conversation,1);
//                            }
//                        }
//                ).start();
//
//                new Thread(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                EMConversation conversation = EMChatManager.getInstance().getConversation("jarvis0106");
//                                conversation.markAllMessagesAsRead();
//                                sendTextMessage("test", "jarvis0106",conversation,2);
//                            }
//                        }
//                ).start();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
