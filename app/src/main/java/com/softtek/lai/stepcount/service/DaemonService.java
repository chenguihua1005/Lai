package com.softtek.lai.stepcount.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.snowdream.android.util.Log;

/**
 * Created by jerry.guan on 7/16/2016.
 */
public class DaemonService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.i("守护service启动");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    StepCloseReceiver closeReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        closeReceiver=new StepCloseReceiver();
        registerReceiver(closeReceiver,new IntentFilter(StepService.STEP_CLOSE));

    }

    @Override
    public void onDestroy() {
        sendBroadcast(new Intent("com.softtek.lai.service_destory"));
        super.onDestroy();
        unregisterReceiver(closeReceiver);
    }

    public static class StepCloseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context,StepService.class));
        }
    }
}
