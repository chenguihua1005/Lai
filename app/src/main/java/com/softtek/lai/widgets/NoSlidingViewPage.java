package com.softtek.lai.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by John on 2016/4/12.
 */
public class NoSlidingViewPage extends ViewPager{

    private boolean noScroll=false;

    public NoSlidingViewPage(Context context) {
        super(context);
    }

    public NoSlidingViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (!noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
