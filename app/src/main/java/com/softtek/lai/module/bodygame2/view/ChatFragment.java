package com.softtek.lai.module.bodygame2.view;

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
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.easemob.util.NetUtils;
import com.softtek.lai.R;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.ui.ConversationListFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygame2pcnoclass.view.BodyGamePCNoClassActivity;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.home.view.HomeFragment;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_chat)
public class ChatFragment extends LazyBaseFragment implements View.OnClickListener, EMEventListener {
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

    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;


    private EaseUI easeUI;
    private ILoginPresenter loginPresenter;
    private ProgressDialog progressDialog;
    UserModel model;

    public AlertDialog.Builder builder = null;
    private EMConnectionListener connectionListener;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
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
                                getActivity().stopService(new Intent(getActivity(), StepService.class));
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
            } else if(msg.what==1) {
                loginPresenter.getEMChatAccount(progressDialog);
            }else {
                img_mo_message.setVisibility(View.GONE);
                conversationListFragment = new ConversationListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lin, conversationListFragment).show(conversationListFragment)
                        .commit();
            }
        }

    };

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(getActivity());
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }

        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        tv_title.setText("会话");
        ll_left.setOnClickListener(this);
        EventBus.getDefault().register(this);
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(final int error) {
                if (error == EMError.CONNECTION_CONFLICT) {
                    SharedPreferenceService.getInstance().put("HXID", "-1");
                    if (!getActivity().isFinishing()) {
                        EMChatManager.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                System.out.println("ChatFragment onSuccess-------");
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                }
            }

            @Override
            public void onConnected() {
                // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
            }
        };
        //EMChatManager.getInstance().addConnectionListener(connectionListener);
        registerBroadcastReceiver();
    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(getContext());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");
    }

    @Override
    protected void onVisible() {
        isPrepared = false;
        if (getContext() instanceof BodyGameSPActivity) {
            BodyGameSPActivity activity = (BodyGameSPActivity) getContext();
            activity.setAlpha(1);
        } else if (getContext() instanceof BodyGamePCActivity) {
            BodyGamePCActivity activity = (BodyGamePCActivity) getContext();
            activity.setAlpha(1);
        } else if (getContext() instanceof BodyGameSRActivity) {
            BodyGameSRActivity activity = (BodyGameSRActivity) getContext();
            activity.setAlpha(1);
        } else if (getContext() instanceof BodyGamePCNoClassActivity) {
            BodyGamePCNoClassActivity activity = (BodyGamePCNoClassActivity) getContext();
            activity.setAlpha(1);
        }
        super.onVisible();
    }

    @Override
    protected void lazyLoad() {
        final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
        if (HomeFragment.timer != null) {
            HomeFragment.timer.cancel();
        }
        if (hxid.equals(model.getHXAccountId())) {
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
            ChatUserModel chatUserModel = new ChatUserModel();
            chatUserModel.setUserName(model.getNickname());
            chatUserModel.setUserPhone(path + model.getPhoto());
            chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
            ChatUserInfoModel.getInstance().setUser(chatUserModel);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EMChatManager.getInstance().updateCurrentUserNick(model.getNickname());
                    EMChatManager.getInstance().loadAllConversations();
                }
            }).start();
            img_mo_message.setVisibility(View.GONE);
            conversationListFragment = new ConversationListFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lin, conversationListFragment).show(conversationListFragment)
                    .commit();
        } else {
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

    }

    private void HXLoginOut() {
        EMChatManager.getInstance().logout(true, new EMCallBack() {

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
            }
        });
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
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        }.start();
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
        System.out.println("model:" + model);
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
        EMChatManager.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                ChatUserModel chatUserModel = new ChatUserModel();
                chatUserModel.setUserName(model.getNickname());
                chatUserModel.setUserPhone(path + model.getPhoto());
                chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                EMChatManager.getInstance().updateCurrentUserNick(model.getNickname());
                ChatUserInfoModel.getInstance().setUser(chatUserModel);
                //更新小红点
                int unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
                Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
                msgIntent.putExtra("count", unreadMsgCountTotal);
                getContext().sendBroadcast(msgIntent);
                EMChatManager.getInstance().loadAllConversations();
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
                Util.toastMsg("登录失败，请稍候再试");
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

        try {
            getActivity().unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }
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
        EaseUI.getInstance().pushActivity(getActivity());
        EaseUI.getInstance().getNotifier().reset();
        // if push service available, connect will be disconnected after app in background
        // after activity restore to foreground, reconnect
        if (!EMChatManager.getInstance().isConnected() && NetUtils.hasNetwork(getActivity())) {
            EMChatManager.getInstance().reconnect();
        }
    }

    @Override
    public void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
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
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;
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
}
