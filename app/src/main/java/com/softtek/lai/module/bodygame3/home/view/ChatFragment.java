package com.softtek.lai.module.bodygame3.home.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.ui.ConversationListFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_chat)
public class ChatFragment extends LazyBaseFragment implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.lin)
    LinearLayout lin;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    public AlertDialog.Builder builder = null;
    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;


    private EaseUI easeUI;
    private ILoginPresenter loginPresenter;
    private ProgressDialog progressDialog;
    UserModel model;

    private EMConnectionListener connectionListener;
    private static final String TAG = "ChatFragment";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 0) {
                    if (builder != null) {
                        return;
                    }
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                            .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder = null;
                                    UserInfoModel.getInstance().loginOut();
                                    LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).setCancelable(false);
                    dialog = builder.create();
                    if (!getActivity().isFinishing()) {
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }
                    }
                } else if (msg.what == 1) {
                    loginPresenter.getEMChatAccount(progressDialog);
                } else if (msg.what == 2) {
                    img_mo_message.setVisibility(View.GONE);
                    conversationListFragment = new ConversationListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lin, conversationListFragment).show(conversationListFragment)
                            .commit();
                } else {
                    Util.toastMsg("会话功能开通中，请稍后再试");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
//        if (DisplayUtil.getSDKInt() > 18) {
//            int status = DisplayUtil.getStatusHeight(getActivity());
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//            params.topMargin = status;
//            toolbar.setLayoutParams(params);
//        }

        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        tv_title.setText("会话");
        ll_left.setOnClickListener(this);
        EventBus.getDefault().register(this);
        registerBroadcastReceiver();
    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(getContext());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

//        registerMessageReceiver();

    }

//    public class MessageReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (Constants.MESSAGE_CHAT_ACTION.equals(intent.getAction())) {
//                int unreadNum = intent.getIntExtra("count", 0);
//                //更新小红点
//                updateMessage(unreadNum);
//            }
//        }
//    }

//    public void registerMessageReceiver() {
//        mMessageReceiver = new MessageReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
//        registerReceiver(mMessageReceiver, filter);
//
//    }

    @Override
    protected void onVisible() {
        isPrepared = false;
//        if (getContext() instanceof BodyGameSPActivity) {
//            BodyGameSPActivity activity = (BodyGameSPActivity) getContext();
//            activity.setAlpha(1);
//        } else if (getContext() instanceof BodyGamePCActivity) {
//            BodyGamePCActivity activity = (BodyGamePCActivity) getContext();
//            activity.setAlpha(1);
//        } else if (getContext() instanceof BodyGameSRActivity) {
//            BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
//            activity.setAlpha(1);
//        }
        super.onVisible();
    }

    @Override
    protected void lazyLoad() {
        if ("0".equals(Constants.IS_LOGINIMG)) {
            final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
//            if (HomeFragment.timer != null) {
//                HomeFragment.timer.cancel();
//            }

            Log.i(TAG, "hxid = " + hxid + "  model.getHXAccountId() = " + model.getHXAccountId());
            if (hxid.equals(model.getHXAccountId())) {
                Log.i(TAG, " 环信帐号验证通过....加载会话列表....");
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                ChatUserModel chatUserModel = new ChatUserModel();
                chatUserModel.setUserName(model.getNickname());
                chatUserModel.setUserPhone(path + model.getPhoto());
                chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                ChatUserInfoModel.getInstance().setUser(chatUserModel);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().updateCurrentUserNick(model.getNickname());
                        EMClient.getInstance().chatManager().loadAllConversations();
                    }
                }).start();
                img_mo_message.setVisibility(View.GONE);
                conversationListFragment = new ConversationListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lin, conversationListFragment).show(conversationListFragment)
                        .commit();
            } else {
                Log.i(TAG, " 环信帐号验证failed ....加载会话列表....");
                if ("-1".equals(hxid)) {
                    loginPresenter.getEMChatAccount(progressDialog);
                } else {
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    HXLoginOut();
                                }
                            }
                    ).start();
                }

            }
        } else {
            Util.toastMsg("会话功能开通中，请稍后再试");
        }


    }

    private void HXLoginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                SharedPreferenceService.getInstance().put("HXID", "-1");
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(4);
            }
        });
    }


    private void refreshUIWithMessage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        }.start();


        // jessica  同时刷新会话下面的小圆点数目
        int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
        msgIntent.putExtra("count", unreadNum);
        getContext().sendBroadcast(msgIntent);
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
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

    @Subscribe
    public void onEvent(EMChatAccountModel model) {
        if (model != null) {
            String state = model.getState();
            if ("0".equals(state)) {
                Util.toastMsg("会话功能开通中，请稍后再试");
            } else if ("-1".equals(state)) {
                Util.toastMsg("开通会话功能需要身份认证");
            } else {
                progressDialog.show();
                loginChat(progressDialog, model.getHXAccountId());
            }
        } else {
            Util.toastMsg("会话功能开通中，请稍后再试");
        }
    }

    private void loginChat(final ProgressDialog progressDialog, final String account) {
        Constants.IS_LOGINIMG = "1";
        EMClient.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                Constants.IS_LOGINIMG = "0";
                SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                ChatUserModel chatUserModel = new ChatUserModel();
                chatUserModel.setUserName(model.getNickname());
                chatUserModel.setUserPhone(path + model.getPhoto());
                chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                EMClient.getInstance().updateCurrentUserNick(model.getNickname());
                ChatUserInfoModel.getInstance().setUser(chatUserModel);
                //更新小红点
                int unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
                msgIntent.putExtra("count", unreadMsgCountTotal);
                getContext().sendBroadcast(msgIntent);
                Log.i(TAG, "发送未读消息数 = " + unreadMsgCountTotal);

                EMClient.getInstance().chatManager().loadAllConversations();

                //从服务器加改人的所有群组
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                handler.sendEmptyMessage(2);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                handler.sendEmptyMessage(4);
                Constants.IS_LOGINIMG = "0";
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();


    }

    @Override
    public void onResume() {
        super.onResume();
        // register the event listener when enter the foreground
//        EMChatManager.getInstance().registerEventListener(this,
//                new EMNotifierEvent.Event[]{
//                        EMNotifierEvent.Event.EventNewMessage,
//                        EMNotifierEvent.Event.EventOfflineMessage,
//                        EMNotifierEvent.Event.EventConversationListChanged,
//                        EMNotifierEvent.Event.EventNewCMDMessage,
//                        EMNotifierEvent.Event.EventReadAck
//                });
        //3.0
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        EaseUI.getInstance().pushActivity(getActivity());
        EaseUI.getInstance().getNotifier().reset();
        // if push service available, connect will be disconnected after app in background
        // after activity restore to foreground, reconnect
        if (!EMClient.getInstance().isConnected() && NetUtils.hasNetwork(getActivity())) {
//            EMChatManager.getInstance().reconnect();
//            EMClient.getInstance().chatManager().
//            EMClient.getInstance().
            Log.i(TAG, "环信服务器重连......");
        }
    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EaseUI.getInstance().popActivity(getActivity());
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }


    private android.app.AlertDialog.Builder conflictBuilder;
    //    private android.app.AlertDialog.Builder accountRemovedBuilder;
//    private boolean isConflictDialogShow;
//    private boolean isAccountRemovedDialogShow;
//    private BroadcastReceiver internalDebugReceiver;
    private ConversationListFragment conversationListFragment;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //getMenuInflater().inflate(R.menu.context_tab_contact, menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:

                break;
            case R.id.fl_right:

                break;
        }
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            for (EMMessage message : messages) {
                Log.i(TAG, "message = " + message);
                EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());

                // in background, do not refresh UI, notify it in notification bar
//                if(!easeUI.hasForegroundActivies()){
                ChatHelper.getInstance().getNotifier().onNewMsg(message);
//                }
            }
            // 提示新消息

            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
//            for (EMMessage message : messages) {
//                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//                final String action = cmdMsgBody.action();//获取自定义action
////                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
////                    RedPacketUtil.receiveRedPacketAckMessage(message);
////                }
//            }
            //end of red packet code

            for (EMMessage message : messages) {
                EMLog.d(TAG, "receive command message");
                //get message body
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                //red packet code : 处理红包回执透传消息
//                if(!easeUI.hasForegroundActivies()){
//                    if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                        RedPacketUtil.receiveRedPacketAckMessage(message);
//                        broadcastManager.sendBroadcast(new Intent(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//                    }
//                }

//                if (action.equals("__Call_ReqP2P_ConferencePattern")) {
//                    String title = message.getStringAttribute("em_apns_ext", "conference call");
//                    Toast.makeText(appContext, title, Toast.LENGTH_LONG).show();
//                }
                //end of red packet code
                //获取扩展属性 此处省略
                //maybe you need get extension of your message
                //message.getStringAttribute("");
                EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
            }

            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            refreshUIWithMessage();
        }
    };


}
