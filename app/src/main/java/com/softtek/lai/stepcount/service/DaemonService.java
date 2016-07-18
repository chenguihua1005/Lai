package com.softtek.lai.stepcount.service;

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
        //注册广播
        closeReceiver=new StepCloseReceiver();
        IntentFilter filter=new IntentFilter(StepService.STEP_CLOSE);
        registerReceiver(closeReceiver,filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(closeReceiver);
        startService(new Intent(this,DaemonService.class));
        super.onDestroy();
    }

    public static class StepCloseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context,StepService.class));
        }
    }
}
