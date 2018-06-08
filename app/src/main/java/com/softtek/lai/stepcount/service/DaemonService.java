package com.softtek.lai.stepcount.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
        startService(new Intent(this,UploadLogService.class));
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    StepCloseReceiver closeReceiver;
//    PendingIntent pi;
//    AlarmManager am;
    @Override
    public void onCreate() {
        super.onCreate();
        closeReceiver=new StepCloseReceiver();
        registerReceiver(closeReceiver,new IntentFilter(StepService.STEP_CLOSE));
        /*am= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime();
        pi=PendingIntent.getService(this,0,new Intent(this,UploadLogService.class),0);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,3600*3000,pi);*/

    }


    @Override
    public void onDestroy() {
        sendBroadcast(new Intent("com.softtek.lai.service_destory"));
        super.onDestroy();
        //am.cancel(pi);
        unregisterReceiver(closeReceiver);
    }

    public static class StepCloseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context,StepService.class));
        }
    }
}
