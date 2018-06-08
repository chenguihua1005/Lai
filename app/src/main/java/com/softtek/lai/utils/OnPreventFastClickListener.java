package com.softtek.lai.utils;

import android.view.View;

import com.github.snowdream.android.util.Log;

/**
 * Created by John on 2016/7/23.
 */
public abstract class OnPreventFastClickListener implements View.OnClickListener{

    private long lastTime;

    @Override
    public void onClick(View v) {
        long time=System.currentTimeMillis();
        if((time-lastTime)<=500){
            Log.i("点击间隔小于500毫秒");
            lastTime=time;
            return;
        }
        lastTime=time;
        onClickListener(v);
    }

    public abstract void onClickListener(View v);
}
