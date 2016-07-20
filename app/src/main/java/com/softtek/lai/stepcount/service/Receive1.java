package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by John on 2016/7/16.
 */
public class Receive1 extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
      if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
            Intent i = new Intent();
            i.setClass(context, DaemonService.class);
            context.startService(i);
        }else if("com.softtek.lai.service_destory".equals(intent.getAction())){
            Intent i = new Intent();
            i.setClass(context, DaemonService.class);
            context.startService(i);
        }else if("com.softtek.lai.clock".equals(intent.getAction())){
            Intent i = new Intent();
            i.setClass(context, DaemonService.class);
            context.startService(i);
        }
    }
}
