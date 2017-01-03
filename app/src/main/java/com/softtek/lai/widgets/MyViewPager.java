package com.softtek.lai.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shelly.xu on 12/26/2016.
 */

public class MyViewPager extends ViewPager {

    private ViewPager child;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setChild(ViewPager child) {
        this.child = child;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(child ==null){
            return super.dispatchTouchEvent(ev);
        }
        return child.dispatchTouchEvent(ev);
    }
}
