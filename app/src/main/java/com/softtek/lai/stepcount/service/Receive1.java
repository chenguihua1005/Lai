package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.utils.DateUtil;

import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by John on 2016/7/16.
 */
public class Receive1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            context.startService(new Intent(context,DaemonService.class));
        } else if ("com.softtek.lai.service_destory".equals(intent.getAction())) {
            context.startService(new Intent(context,DaemonService.class));
        } else if ("com.softtek.lai.clock".equals(intent.getAction())) {
            context.startService(new Intent(context,DaemonService.class));
        }else if ("com.softtek.lai.StepService.StepClose".equals(intent.getAction())){
            context.startService(new Intent(context,StepService.class));
        }else if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            context.startService(new Intent(context,DaemonService.class));
        }else if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
            //电池电量变化了
            Log.i("电量变化了");
            context.startService(new Intent(context,DaemonService.class));
        }else if(intent.getAction().equals(Intent.ACTION_SHUTDOWN)){
            //标志手机已经关机
            SharedPreferenceService.getInstance().put("shutDown",true);
            SharedPreferenceService.getInstance().put("shutDownTime", DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate());
            Log.i("关机啦啊啦啦啦啦啦啦啦啦啦啊啦啦啦啦啦啦啦啦啦啊啦啦啦啦啦啦啦啦啦啊啦啦啦啦啦啦啦啦啦啊啦啦啦啦啦啦啦啦啦啊啦啦啦啦啦啦啦啦");

        }
    }
}
