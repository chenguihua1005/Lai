package com.softtek.lai.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 2016/6/21.
 */
public class HomeListener {

    private WeakReference<Context> mContext;
    private OnHomePressedListener mListener;
    private InnerRecevier mRecevier;

    // 回调接口
    public interface OnHomePressedListener {
        void onHomePressed();

        void onHomeLongPressed();
    }

    public HomeListener(Context context) {
        mContext = new WeakReference(context);
        mRecevier=new InnerRecevier();

    }

    /**
     * 开始监听，注册广播
     */
    public void startWatch(OnHomePressedListener listener) {
        mListener = listener;
        IntentFilter mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mContext.get().registerReceiver(mRecevier, mFilter);
    }

    /**
     * 停止监听，注销广播
     */
    public void stopWatch() {
        mContext.get().unregisterReceiver(mRecevier);
        mRecevier=null;
        mListener=null;
    }

    class InnerRecevier extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    //                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            // 短按home键
                            mListener.onHomePressed();
                        } else if (reason
                                .equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            // 长按home键
                            mListener.onHomeLongPressed();
                        }
                    }
                }
            }
        }
    }
}
