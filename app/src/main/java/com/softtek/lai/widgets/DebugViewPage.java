package com.softtek.lai.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by John on 2016/4/12.
 */
public class DebugViewPage extends ViewPager{



    public DebugViewPage(Context context) {
        super(context);
    }

    public DebugViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        try {
            return super.onInterceptTouchEvent(arg0);
        } catch (Exception e) {
            // ignore it
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            // ignore it
        }
        return false;
    }
}
