package com.softtek.lai.module.bodygame3.home.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.softtek.lai.R;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.ui.ConversationListFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.model.HxInviteToGroupModel;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.UserModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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

    private ProgressDialog progressDialog;
    UserModel model;

    private static final String TAG = "ChatFragment";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 2) {
                    img_mo_message.setVisibility(View.GONE);
                    conversationListFragment = new ConversationListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lin, conversationListFragment).show(conversationListFragment)
                            .commit();
                } else {
                    Util.toastMsg("网络异常，请重新登录后再试");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

    }

    @Override
    protected void onVisible() {
        isPrepared = false;

        super.onVisible();
    }

    @Override
    protected void lazyLoad() {

//返回是否登录过 登录成功过没调logout方法，这个方法的返回值一直是true 如果需要判断当前是否连接到服务器，请使用isConnected()方法
        if (EMClient.getInstance().isLoggedInBefore()) {
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
            Util.toastMsg("网络异常，请重新登录后再试");
        }
    }

    private List<HxInviteToGroupModel> needGroupList = new ArrayList<HxInviteToGroupModel>();

    //    查看学员是否有加入环信群的消息
//    private void getMsgHxInviteToGroup() {
//        com.github.snowdream.android.util.Log.i(TAG, " 查看学员是否有加入环信群的消息......");
//        needGroupList.clear();
//        final ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
//        service.getMsgHxInviteToGroup(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<List<HxInviteToGroupModel>>>() {
//            @Override
//            public void success(ResponseData<List<HxInviteToGroupModel>> listResponseData, Response response) {
//                int status = listResponseData.getStatus();
//                if (200 == status) {
//                    needGroupList = listResponseData.getData();
//                    if (needGroupList != null && needGroupList.size() > 0) {
//                        for (int i = 0; i < needGroupList.size(); i++) {
//                            final HxInviteToGroupModel model = needGroupList.get(i);
//                            if (model != null) {
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
////                                        EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
//                                            EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(model.getClassGroupHxId()), String.valueOf(model.getCoachHxId()));
//
//                                            //环迅同意进群之后，告知后台
//                                            service.completeJoinHx(UserInfoModel.getInstance().getToken(), model.getClassId(), model.getMessageId(), new Callback<ResponseData>() {
//                                                @Override
//                                                public void success(ResponseData responseData, Response response) {
//                                                    if (200 == responseData.getStatus()) {
//
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void failure(RetrofitError error) {
//                                                    error.printStackTrace();
//                                                    ZillaApi.dealNetError(error);
//                                                }
//                                            });
//
//
//                                        } catch (HyphenateException e) {
//                                            e.printStackTrace();
////                                            runOnUiThread(new Runnable() {
////                                                @Override
////                                                public void run() {
////                                                    dialogDissmiss();
////                                                    Util.toastMsg("环信异常");
////                                                }
////                                            });
//                                        }
//
//                                    }
//                                }).start();
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//
//
//    }


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
                Util.toastMsg("网络异常，请重新登录后再试");
            } else if ("-1".equals(state)) {
                Util.toastMsg("开通会话功能需要身份认证");
            } else {
                progressDialog.show();
                loginChat(progressDialog, model.getHXAccountId());
            }
        } else {
            Util.toastMsg("网络异常，请重新登录后再试");
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
        unregisterBroadcastReceiver();


    }

    @Override
    public void onResume() {
        super.onResume();
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

        //查看学员是否有加入环信群的消息
//        getMsgHxInviteToGroup();


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
            //end of red packet code

            for (EMMessage message : messages) {
                EMLog.d(TAG, "receive command message");
                //get message body
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                //red packet code : 处理红包回执透传消息
                EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
            }

            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            refreshUIWithMessage();
        }

//        @Override
//        public void onMessageReadAckReceived(List<EMMessage> messages) {
//            refreshUIWithMessage();
//        }
//
//        @Override
//        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
//            refreshUIWithMessage();
//        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            refreshUIWithMessage();
        }
    };


}
