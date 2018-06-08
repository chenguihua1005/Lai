/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.utils;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by John on 2016/3/30.
 * 重新实现了CountDownTimer可以拥有暂停 和 继续的功能
 */
public abstract class JCountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    private long mPauseTime;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    private boolean mIsRunning = false;
    private boolean mPaused = false;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public JCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mPaused = false;
        mIsRunning = false;
        mHandler.removeMessages(MSG);
    }

    /**
     * reStart the countdown
     */
    public synchronized final void reStart() {
        mPaused = false;
        //更新停止时间
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mPauseTime;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
    }

    /**
     * Puse the countdown
     */
    public synchronized final void pause() {
        mPaused = true;
    }

    public final boolean isPaused() {
        return mPaused;
    }

    public final boolean isRunning() {
        return mIsRunning;
    }

    /**
     * Start the countdown.
     */
    public synchronized final JCountDownTimer start() {
        mCancelled = false;
        mPaused = false;
        mIsRunning = true;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (JCountDownTimer.this) {

                if (mCancelled || mPaused) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                    mIsRunning = false;
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    mPauseTime = millisLeft;
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
