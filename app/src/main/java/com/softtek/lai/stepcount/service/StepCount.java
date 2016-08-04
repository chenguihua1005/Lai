package com.softtek.lai.stepcount.service;

import zilla.libcore.util.Util;

/**
 * Created by John on 2016/7/10.
 */
public class StepCount implements StepCountListener {
    private int count;
    private int mCount;
    long timeOfLastPeak;
    long timeOfThisPeak;
    private StepPaseValueListener mListeners;

    @Override
    public void onStep() {
        if(timeOfLastPeak==0){
            timeOfLastPeak = System.currentTimeMillis();

        }else{
            timeOfLastPeak = timeOfThisPeak;
        }
        timeOfThisPeak = System.currentTimeMillis();
        long time=timeOfThisPeak - timeOfLastPeak;
        if (time >= 3000) {
            //时间超过3秒后之前的步数清零
            count = 1;
        } else if(time>=250){
            count++;
        }
        Util.toastMsg("当前计步器步数出发>>>count="+count+";mCount="+mCount);
        if (count == 5) {
            mCount += count;
            if (mListeners != null) {
                mListeners.stepsChanged(mCount);
            }
        }else if (count > 5) {
            mCount++;
            if (mListeners != null) {
                mListeners.stepsChanged(mCount);
            }
        }
    }

    public void setmListeners(StepPaseValueListener mListeners) {
        this.mListeners = mListeners;
    }
}
