package com.softtek.lai.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.view.ActivitydetailActivity;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message2.view.ExamineActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.message2.view.MessageConfirmActivity;
import com.softtek.lai.module.sport.view.RunSportActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
            //processCustomMessage(context, bundle);
            String extra=bundle.getString(JPushInterface.EXTRA_EXTRA);
            if(!extra.isEmpty()){
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //拿到通知类型
                    int msgType=json.optInt("msgtype");
                    if(msgType==6){//收到了被移除班级的通知
                        UpdateClass clazz=new UpdateClass();
                        clazz.setStatus(2);
                        ClassModel model=new ClassModel();
                        model.setClassId(json.optString("classcode"));
                        clazz.setModel(model);
                        EventBus.getDefault().post(clazz);
                        UserInfoModel.getInstance().clearClassSave(model.getClassId());
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            }
            Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
            WeakReference<Context> appContext= LaiApplication.getInstance().getContext();
            if(appContext!=null&&appContext.get() instanceof RunSportActivity){
                Intent runIntent=new Intent(context,RunSportActivity.class);
                runIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(runIntent);

            }else{
                String extra=bundle.getString(JPushInterface.EXTRA_EXTRA);
                String token= UserInfoModel.getInstance(context).getToken();
                if(TextUtils.isEmpty(token)){
                    Intent i = new Intent(context, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }else if(!extra.isEmpty()){
                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        String ascription=json.optString("ascription");
                        if(ascription!=null&&!ascription.equals(String.valueOf(UserInfoModel.getInstance().getUserId()))){
                            return;
                        }
                        //拿到通知类型
                        int msgType=json.optInt("msgtype");
                        if(msgType==0||msgType==4){//普通通知直接跳转到消息列表
                            //默认跳转到消息中心
                            Intent i = new Intent(context, Message2Activity.class);
                            i.putExtras(bundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }else if(msgType==1){//申请加入班级通知
                            //进入申请确认界面
                            Intent i = new Intent(context, ExamineActivity.class);
                            i.putExtra("msgId",json.optString("msgId"));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }else if(msgType==2){//邀请加入班级通知
                            //进入邀请确认界面
                            Intent i = new Intent(context, MessageConfirmActivity.class);
                            i.putExtra("msgId",json.optString("msgId"));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }else if(msgType==3){//点击跳转到活动详情
                            //进入活动界面
                            Intent i = new Intent(context, ActivitydetailActivity.class);
                            i.putExtra("activityId",json.optString("activityId"));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }
                }else {
                    //默认跳转到消息中心
                    Intent i = new Intent(context, Message2Activity.class);
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(Constants.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(Constants.KEY_MESSAGE, message);

        try {
            JSONObject extraJson = new JSONObject(extras);
            if (null != extraJson && extraJson.length() > 0) {
                msgIntent.putExtra(Constants.KEY_EXTRAS, extras);
            }
        } catch (JSONException e) {

        }
        context.sendBroadcast(msgIntent);

    }
}
