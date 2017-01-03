package com.softtek.lai.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by shelly.xu on 12/26/2016.
 */

public class WrapRelativeLayout extends RelativeLayout {

    ViewPager viewPager;

    public WrapRelativeLayout(Context context) {
        super(context);
    }

    public WrapRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        if(viewPager == null){
//            return super.dispatchTouchEvent(ev);
//        }
//        return viewPager.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(viewPager == null){
//            return super.onInterceptTouchEvent(ev);
//        }
//        return viewPager.onInterceptTouchEvent(ev);
////        return true;
//    }
}
