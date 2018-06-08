package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.github.snowdream.android.util.Log;

/**
 * Created by John on 2016/4/2.
 */
public class MyScrollView2 extends ScrollView{

    public MyScrollView2(Context context) {
        super(context);
    }

    public MyScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.i("获取到的y值="+getScrollY());
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(t<=0){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

}
