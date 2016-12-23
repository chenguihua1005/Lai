package com.softtek.lai.module.bodygame3.conversation.service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;

import org.apache.commons.lang3.StringUtils;

import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by jessica.zhang on 2016/12/22.
 */

public class HXLoginService extends Service implements Runnable{
    UserModel model;
    String account = "";
    public static boolean hasExit = false;//尚未弹出
    //    static int count = 0;
    AlertDialog dialog;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }

        account = model.getHXAccountId();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        ChatUserModel chatUserModel = new ChatUserModel();
        chatUserModel.setUserName(model.getNickname());
        chatUserModel.setUserPhone(path + model.getPhoto());
        chatUserModel.setUserId(StringUtils.isEmpty(model.getHXAccountId()) ? "" : model.getHXAccountId().toLowerCase());

        ChatUserInfoModel.getInstance().setUser(chatUserModel);
        final String hasEmchat = model.getHasEmchat();

        if ("0".equals(Constants.IS_LOGINIMG)) {
            Constants.IS_LOGINIMG = "1";
            EMClient.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.i("aaaaaaa", "登录成功............帐号 = " + account.toLowerCase());
                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                    // ** manually load all local groups and
                    hasExit = false;
                    Constants.IS_LOGINIMG = "0";
                    SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
                    String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                    ChatUserModel chatUserModel = new ChatUserModel();
                    chatUserModel.setUserName(model.getNickname());
                    chatUserModel.setUserPhone(path + model.getPhoto());
                    chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                    ChatUserInfoModel.getInstance().setUser(chatUserModel);


                    //从服务器加载和该用户相关的所有群组
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().updateCurrentUserNick(model.getNickname());
                                EMClient.getInstance().chatManager().loadAllConversations();
//                                EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(final int code, final String message) {
                    Constants.IS_LOGINIMG = "0";
                    com.github.snowdream.android.util.Log.i("环信登录失败///////////////////////////////////////////////////aaaaaaa");

                }
            });

            EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
                @Override
                public void onConnected() {
                    Log.i("aaaaaaa", "登录成功............///////////////////////////////////////////////////////////" + account.toLowerCase());
                }

                @Override
                public void onDisconnected(int error) {
                    com.github.snowdream.android.util.Log.i("环信掉线了乐乐乐乐乐乐乐乐乐乐乐乐，错误状态码=======" + error);
                    if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        Looper.prepare();
                        if (dialog == null || !dialog.isShowing()) {
                            dialog = new AlertDialog.Builder(getApplicationContext(), R.style.AlertDialogTheme)
                                    .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                                    .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hasExit = false;
                                            UserInfoModel.getInstance().loginOut();
                                            LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        }
                                    }).setCancelable(false).create();
                            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                            dialog.show();
                        }
                        Looper.loop();
//                        EMClient.getInstance().logout(true, new EMCallBack() {
//                            @Override
//                            public void onSuccess() {
//                                com.github.snowdream.android.util.Log.i("环信退出=======");
//
//                                if (count == 1) {
////                                if (!hasExit) {
//                                    Looper.prepare();
//                                    AlertDialog dialog = new AlertDialog.Builder(getApplicationContext(), R.style.AlertDialogTheme)
//                                            .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
//                                            .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    hasExit = false;
//                                                    count = 0;
//                                                    UserInfoModel.getInstance().loginOut();
//                                                    LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
//                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    startActivity(intent);
//                                                }
//                                            }).setCancelable(false).create();
//                                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//                                    dialog.show();
//                                    Looper.loop();
//                                }
//                            }
////                            }
//
//                            @Override
//                            public void onProgress(int progress, String status) {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onError(int code, String message) {
//                                // TODO Auto-generated method stub
////                                hasExit = true;
//                            }
//                        });
                    }
                }
            });

        }
    }
}
