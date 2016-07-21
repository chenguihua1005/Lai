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
        Log.i("守护service启动");
        return START_STICKY;
    }

    StepCloseReceiver closeReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receive1.class);
        intent.setAction("com.softtek.lai.clock");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5000, pendingIntent);
        closeReceiver=new StepCloseReceiver();
        registerReceiver(closeReceiver,new IntentFilter(StepService.STEP_CLOSE));
    }

    @Override
    public void onDestroy() {
        sendBroadcast(new Intent(StepService.STEP_CLOSE));
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
