package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
        }
    }
}
