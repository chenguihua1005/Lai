package com.softtek.lai.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.model.EaseNotifier;
import com.easemob.easeui.utils.EaseACKUtil;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.exceptions.EaseMobException;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.chat.ui.ConversationListActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;

import java.util.List;

public class ChatHelper {

    private static ChatHelper instance = null;
    private Context appContext;
    private EaseUI easeUI;
    private EMConnectionListener connectionListener;
    protected EMEventListener eventListener = null;
    public AlertDialog.Builder builder = null;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (builder != null) {
                return;
            }
            builder = new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder = null;
                            UserInfoModel.getInstance().loginOut();
                            LaiApplication.getInstance().stopService(new Intent(LaiApplication.getInstance(), StepService.class));
                            Intent intent = new Intent(LaiApplication.getInstance(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LaiApplication.getInstance().startActivity(intent);
                        }
                    }).setCancelable(false);
            builder.create().show();

        }

    };


    static public interface DataSyncListener {
        /**
         * 同步完毕
         *
         * @param success true：成功同步到数据，false失败
         */
        public void onSyncComplete(boolean success);
    }

    private ChatHelper() {
    }

    public synchronized static ChatHelper getInstance() {
        if (instance == null) {
            instance = new ChatHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        if (EaseUI.getInstance().init(context)) {
            appContext = context;
//get easeui instance
            easeUI = EaseUI.getInstance();
            EMChatManager.getInstance().setMipushConfig("2882303761517485411", "5111748585411");
            connectionListener = new EMConnectionListener() {
                @Override
                public void onDisconnected(final int error) {
                    EMChatManager.getInstance().logout(new EMCallBack() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            System.out.println("--------");
                            onConnectionConflict();
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

                @Override
                public void onConnected() {
                    // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，

                }
            };
           // EMChatManager.getInstance().addConnectionListener(connectionListener);
            registerEventListener();
            setEaseUIProviders();
        }
    }

    protected void setEaseUIProviders() {
//不设置，则使用easeui默认的
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                String nickName = "";
                try {
                    nickName = message.getStringAttribute("nickname");
                } catch (EaseMobException e) {
                    e.printStackTrace();
                }
                return nickName + ": " + ticker;
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                Intent intent = new Intent(appContext, BodyGameSPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                return intent;
            }
        });
    }

    /**
     * 账号被移除
     */
    protected void onCurrentAccountRemoved() {
        Intent intent = new Intent(appContext, ConversationListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

    /**
     * 账号在别的设备登录
     */
    protected void onConnectionConflict() {
        System.out.println("onConnectionConflict-----");
//        Intent intent = new Intent(appContext, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
//        appContext.startActivity(intent);

        handler.sendEmptyMessage(0);
    }

    void endCall() {
        try {
            EMChatManager.getInstance().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        eventListener = new EMEventListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onEvent(EMNotifierEvent event) {
                EMMessage message = null;
                if (event.getData() instanceof EMMessage) {
                    message = (EMMessage) event.getData();
                }

                switch (event.getEvent()) {
                    case EventNewMessage:
                        //应用在后台，不需要刷新UI,通知栏提示新消息
                        System.out.println("getMessage1111111111");
                        int unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
                        Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
                        msgIntent.putExtra("count", unreadMsgCountTotal);
                        appContext.sendBroadcast(msgIntent);
                        if (!easeUI.hasForegroundActivies()) {
                            getNotifier().onNewMsg(message);
                        }
                        break;
                    case EventOfflineMessage:
                        System.out.println("EventOfflineMessage--------");
                        List<EMMessage> messagess = (List<EMMessage>) event.getData();
                        for (int i = 0; i <messagess.size() ; i++) {
                            EMMessage m=messagess.get(i);
                            try {
                                String name=m.getStringAttribute("nickname");
                                System.out.println("name:"+name);
                            } catch (EaseMobException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!easeUI.hasForegroundActivies()) {
                            List<EMMessage> messages = (List<EMMessage>) event.getData();
                            getNotifier().onNewMesg(messages);
                        }
                        break;
                    // below is just giving a example to show a cmd toast, the app should not follow this
                    // so be careful of this
                    case EventNewCMDMessage:
                        //获取消息body
                        CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
                        final String action = cmdMsgBody.action;//获取自定义action
                        if (!easeUI.hasForegroundActivies()) {
                            if (action.equals(EaseConstant.EASE_ATTR_REVOKE)) {
                                EaseCommonUtils.receiveRevokeMessage(appContext, message);
                            }
                        }
                        //获取扩展属性 此处省略
                        //message.getStringAttribute("");
                        final String str = appContext.getString(com.easemob.easeui.R.string.receive_the_passthrough);

                        final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
                        IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                        if (broadCastReceiver == null) {
                            broadCastReceiver = new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    // TODO Auto-generated method stub
                                    //过滤掉红包回执消息的透传吐司
                                    if (action.equals(Constant.REFRESH_GROUP_MONEY_ACTION)) {
                                        return;
                                    }
                                    Toast.makeText(appContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
                                }
                            };

                            //注册广播接收者
                            appContext.registerReceiver(broadCastReceiver, cmdFilter);
                        }

                        Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                        broadcastIntent.putExtra("cmd_value", str + action);
                        appContext.sendBroadcast(broadcastIntent, null);

                        break;
                    case EventDeliveryAck:
                        message.setDelivered(true);
                        break;
                    case EventReadAck:
                        // TODO 这里当此消息未加载到内存中时，ackMessage会为null，消息的删除会失败
                        message.setAcked(true);
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
                        break;
                    // add other events in case you are interested in
                    default:
                        break;
                }

            }
        };

        EMChatManager.getInstance().registerEventListener(eventListener);
    }

    /**
     * 获取消息通知类
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        EMChatManager.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }
}
