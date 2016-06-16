package com.softtek.lai.chat;

import android.content.Context;
import android.content.Intent;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.easemob.easeui.domain.EaseEmojicon;
import com.easemob.easeui.domain.EaseEmojiconGroupEntity;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.model.EaseNotifier;
import com.easemob.easeui.utils.EaseACKUtil;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.easeui.utils.EaseUserUtils;
import com.easemob.util.EMLog;
import com.easemob.util.EasyUtils;

import java.util.List;
import java.util.Map;

public class ChatHelper {

    private static ChatHelper instance = null;
    private Context appContext;
    private EaseUI easeUI;
    private EMConnectionListener connectionListener;

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

            ChatUserModel chatUserModel = new ChatUserModel();
            chatUserModel.setUserId("jarvis0104");
            chatUserModel.setUserName("aaa");
            chatUserModel.setUserPhone("https://o8nbxcohc.qnssl.com/testimage.png");
//            chatUserModel.setUserPhone("http://172.16.98.167/UpFiles/201606141135132757684564.png");
            ChatUserInfoModel.getInstance().setUser(chatUserModel);

            connectionListener = new EMConnectionListener() {
                @Override
                public void onDisconnected(int error) {
                    if (error == EMError.USER_REMOVED) {
                        onCurrentAccountRemoved();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        onConnectionConflict();
                    }
                }

                @Override
                public void onConnected() {
                    // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
                    EaseACKUtil.getInstance(appContext).checkACKData();

                }
            };
            EMChatManager.getInstance().addConnectionListener(connectionListener);
        }
    }

    /**
     * 账号被移除
     */
    protected void onCurrentAccountRemoved() {
        Intent intent = new Intent(appContext, ConversationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

    /**
     * 账号在别的设备登录
     */
    protected void onConnectionConflict() {
        Intent intent = new Intent(appContext, ConversationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
        appContext.startActivity(intent);
    }

    void endCall() {
        try {
            EMChatManager.getInstance().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
