package com.softtek.lai.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Created by jerry.guan on 8/12/2016.
 */
public class TimeTickListener {

    private WeakReference<Context> weakReference;
    private OnTimeTick timeTick;
    private TimeTickReceiver receiver;

    public TimeTickListener(Context context) {
        weakReference=new WeakReference<>(context);
        receiver=new TimeTickReceiver();
    }

    public void startTick(OnTimeTick tick){
        timeTick=tick;
        weakReference.get().registerReceiver(receiver,new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    public void stopTick(){
        weakReference.get().unregisterReceiver(receiver);
        receiver=null;
        timeTick=null;
    }

    public interface OnTimeTick{
        void onTick(Calendar calendar);
    }

    private class TimeTickReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            timeTick.onTick(c);
        }
    }
}
