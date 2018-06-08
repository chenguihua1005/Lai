//package com.softtek.lai.module.bodygame3.conversation.service;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AlertDialog;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.hyphenate.EMCallBack;
//import com.hyphenate.EMConnectionListener;
//import com.hyphenate.EMError;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.easeui.domain.ChatUserInfoModel;
//import com.hyphenate.easeui.domain.ChatUserModel;
//import com.softtek.lai.LaiApplication;
//import com.softtek.lai.common.UserInfoModel;
//import com.softtek.lai.jpush.JpushSet;
//import com.softtek.lai.module.login.model.UserModel;
//import com.softtek.lai.module.login.view.LoginActivity;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.lang.ref.WeakReference;
//
//import zilla.libcore.file.AddressManager;
//import zilla.libcore.file.SharedPreferenceService;
//
//import static com.softtek.lai.stepcount.service.StepService.STEP_CLOSE_SELF;
//
///**
// * Created by jessica.zhang on 2016/12/22.
// */
//
//public class HXLoginService extends Service implements Runnable {
//    UserModel model;
//    String account = "";
//    private static boolean isExit = false;
//    private CloseReceive closeReceive;
//    public static final String HXLOGIN_CLOSE_SELF = "com.softtek.lai.HXLoginService.HXLOGIN_CLOSE_SELF";
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        model = UserInfoModel.getInstance().getUser();
////        android.util.Log.i("aaaaaaa", "HXLoginService 中登录的用户信息  = " + new Gson().toJson(model));
//
//        if (model == null) {
//            return;
//        }
//        isExit = false;
//        account = model.getHXAccountId();
//        closeReceive = new CloseReceive();
//        LocalBroadcastManager.getInstance(this).registerReceiver(closeReceive, new IntentFilter(HXLOGIN_CLOSE_SELF));
////        com.github.snowdream.android.util.Log.i("环信登录服务启动创建。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。" + model.getHXAccountId());
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(closeReceive);
////        com.github.snowdream.android.util.Log.i("环信登录服务关闭");
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return super.onUnbind(intent);
//    }
//
//    @Override
//    public void onRebind(Intent intent) {
//        super.onRebind(intent);
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        com.github.snowdream.android.util.Log.i("环信登录服务启动 》》》》》》》》》》》》》》》》》》》》》》》》》onStartCommand");
//        model = UserInfoModel.getInstance().getUser();
//        if(model!=null){
//            account = model.getHXAccountId();
//            if(!TextUtils.isEmpty(account)){
//                new Thread(this).start();
//            }else {
//                isExit = false;
//                stopSelf();
//            }
//        }else {
//            isExit = false;
//            stopSelf();
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    AlertDialog alertDialog;
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(STEP_CLOSE_SELF));
//            JpushSet set = new JpushSet(LaiApplication.getInstance());
//            set.setAlias("");
//            if (alertDialog == null) {
//                com.github.snowdream.android.util.Log.i("弹窗是空的");
//                WeakReference<Context> appContext = LaiApplication.getInstance().getContext();
//                if (appContext != null && appContext.get() != null) {
//                    alertDialog = new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
//                            .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
//                            .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    com.github.snowdream.android.util.Log.i("服务自杀====");
//                                    alertDialog = null;
//                                    stopSelf();
//                                    UserInfoModel.getInstance().loginOut();
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }
//                            }).setCancelable(false).create();
//                    alertDialog.show();
//                }
//
//            }
//        }
//    };
//
//    public class CloseReceive extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            isExit = false;
//            //收到广播。杀死自己
//            stopSelf();
//        }
//    }
//
//
//    @Override
//    public void run() {
//
//        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//        ChatUserModel chatUserModel = new ChatUserModel();
//        chatUserModel.setUserName(model.getNickname());
//        chatUserModel.setUserPhone(path + model.getPhoto());
//        chatUserModel.setUserId(StringUtils.isEmpty(model.getHXAccountId()) ? "" : model.getHXAccountId().toLowerCase());
//        ChatUserInfoModel.getInstance().setUser(chatUserModel);
//        final String hasEmchat = model.getHasEmchat();
//
//        String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
////        com.github.snowdream.android.util.Log.i("环信账号hxid = " + hxid + " model.getHXAccountId() = " + model.getHXAccountId());
//
//        if (hxid.equals(model.getHXAccountId())) {//本地环信Id 和登录的账号HXId 是同一个人
////            com.github.snowdream.android.util.Log.i("环信账号之前已登入 》》》》》》》》》》》》》》》》》》》》》》》》》");
//
//            path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//            chatUserModel.setUserName(model.getNickname());
//            chatUserModel.setUserPhone(path + model.getPhoto());
//            chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
//            ChatUserInfoModel.getInstance().setUser(chatUserModel);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    EMClient.getInstance().updateCurrentUserNick(model.getNickname());
//                    EMClient.getInstance().chatManager().loadAllConversations();
//                }
//            }).start();
//
////            stopSelf();//自杀
//
//        } else {
//            if ("-1".equals(hxid)) {//之前已经完全退出，
////                com.github.snowdream.android.util.Log.i("环信新账号登入 》》》》》》》》》》》》》》》》》》》》》》》》》");
//
//                EMClient.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
////                        Log.i("aaaaaaa", "登录成功............帐号 = " + account.toLowerCase());
//                        isExit = false;
//                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                        // ** manually load all local groups and
//                        SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
//                        String path = AddressManager.get("photoHost");
//                        ChatUserModel chatUserModel = new ChatUserModel();
//                        chatUserModel.setUserName(model.getNickname());
//                        chatUserModel.setUserPhone(path + model.getPhoto());
//                        chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
//                        ChatUserInfoModel.getInstance().setUser(chatUserModel);
//
//
//                        //从服务器加载和该用户相关的所有群组
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    EMClient.getInstance().updateCurrentUserNick(model.getNickname());
//                                    EMClient.getInstance().chatManager().loadAllConversations();
////                                EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
//
//                        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
//                            @Override
//                            public void onConnected() {
////                                Log.i("aaaaaaa", "登录成功............///////////////////EMClient.getInstance().addConnectionListener/////////////" + account.toLowerCase());
//                            }
//
//                            @Override
//                            public void onDisconnected(int error) {
//                                if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                                    if (!isExit)
//                                        isExit = true;
//                                    else
//                                        return;
////                                    com.github.snowdream.android.util.Log.i("环信掉线了乐乐乐乐乐乐乐乐乐乐乐乐，错误状态码=======" + error);
//                                    EMClient.getInstance().logout(true, new EMCallBack() {
//                                        @Override
//                                        public void onSuccess() {
////                                            com.github.snowdream.android.util.Log.i("退出成功=======");
//                                            SharedPreferenceService.getInstance().put("HXID", "-1");
//                                            isExit = true;
//                                            handler.sendEmptyMessage(4);
//
//                                        }
//
//                                        @Override
//                                        public void onProgress(int progress, String status) {
//                                            // TODO Auto-generated method stub
////                                            com.github.snowdream.android.util.Log.i("onProgress()=======" + status);
//
//
//                                        }
//
//                                        @Override
//                                        public void onError(int code, String message) {
//                                            // TODO Auto-generated method stub
////                                            com.github.snowdream.android.util.Log.i("onError()=======" + message);
//                                        }
//                                    });
//
//                                }
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//
//                    @Override
//                    public void onError(final int code, final String message) {
////                        com.github.snowdream.android.util.Log.i("环信登录失败///////////////////////////////////////////////////aaaaaaa");
//                        SharedPreferenceService.getInstance().put("HXID", "-1");
//                        //重新启动
//                        if(!isExit){
//                            startService(new Intent(getApplicationContext(), HXLoginService.class));
//                        }
//                    }
//                });
//
//            } else {//之前账户没有完全退出
//                HXLoginOut();
//            }
//        }
//
//
////        SharedPreferenceService.getInstance().put("HXID", "-1");
//
//
//    }
//
//
//    private void HXLoginOut() {
////        com.github.snowdream.android.util.Log.i("环信之前账号登出 》》》》》》》》》》》》》》》》》》》》》》》》》onStartCommand");
//
//        EMClient.getInstance().logout(true, new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//                SharedPreferenceService.getInstance().put("HXID", "-1");
////                stopSelf();
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                // TODO Auto-generated method stub
//            }
//        });
//    }
//}
