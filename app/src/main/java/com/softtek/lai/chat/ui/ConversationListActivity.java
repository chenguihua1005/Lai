/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.util.EMLog;
import com.softtek.lai.R;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_conversation_list)
public class ConversationListActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, EMMessageListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.lin)
    LinearLayout lin;

    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;

    // 未读消息textview
    private TextView unreadLabel;
    private EaseUI easeUI;

    UserModel model;

    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("会话");
        // iv_email.setImageResource(R.drawable.img_chat_contant);
        ll_left.setOnClickListener(this);
        //fl_right.setOnClickListener(this);
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
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    @Override
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        ChatUserModel chatUserModel = new ChatUserModel();
        chatUserModel.setUserName(model.getNickname());
        chatUserModel.setUserPhone(path + model.getPhoto());
        chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
        ChatUserInfoModel.getInstance().setUser(chatUserModel);
        // register the event listener when enter the foreground

        // jessica
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EaseAtMessageHelper.get().parseMessages(messages);
            }

//            @Override
//            public void onMessageReadAckReceived(List<EMMessage> messages) {
//
//            }
//
//            @Override
//            public void onMessageDeliveryAckReceived(List<EMMessage> messages) {
//            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }
        });

        EaseUI.getInstance().pushActivity(this);
        // if push service available, connect will be disconnected after app in background
        // after activity restore to foreground, reconnect

    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().addMessageListener(this);
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
                        Intent intent = new Intent(ConversationListActivity.this, LoginActivity.class);
                        startActivity(intent);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        // notify new message
        for (EMMessage message : messages) {
//            DemoHelper.getInstance().getNotifier().onNewMsg(message);
        }
        refreshUIWithMessage();

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

//    @Override
//    public void onMessageReadAckReceived(List<EMMessage> list) {
//
//    }
//
//    @Override
//    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
//
//    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

}
