package com.softtek.lai.stepcount.service;

import com.github.snowdream.android.util.Log;

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
        if(time>200&&time<3000){
            count++;
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
        }else {
            count=1;
        }

    }

    public void setmListeners(StepPaseValueListener mListeners) {
        this.mListeners = mListeners;
    }
}
