package com.softtek.lai.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 7/29/2016.
 */
public class ScreenListener {

    private WeakReference<Context> mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;


    public ScreenListener(Context mContext) {
        this.mContext = new WeakReference(mContext);
        mScreenReceiver=new ScreenBroadcastReceiver();
    }

    //注册监听
    public void registerListener(ScreenStateListener listener){
        mScreenStateListener=listener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.get().registerReceiver(mScreenReceiver, filter);
    }

    //解除注册
    public void unregisterListener(){
        mContext.get().unregisterReceiver(mScreenReceiver);
        mScreenStateListener=null;
        mContext.clear();

    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                mScreenStateListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                mScreenStateListener.onScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                mScreenStateListener.onUserPresent();
            }
        }
    }

    public interface ScreenStateListener{

        void onScreenOn();

        void onScreenOff();

        void onUserPresent();
    }
}
