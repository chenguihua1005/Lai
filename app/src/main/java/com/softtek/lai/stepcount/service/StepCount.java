package com.softtek.lai.stepcount.service;

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
        if ((timeOfThisPeak - timeOfLastPeak) >= 3000) {
            count = 1;
        } else {
            count++;
        }
        if (count == 5) {
            mCount = mCount + count;
            if (mListeners != null) {
                mListeners.stepsChanged(mCount);
            }
            return;
        }
        if (count > 5) {
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
