package com.softtek.lai.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jerry.guan on 9/29/2016.
 */
public class MySwipRefreshView extends SwipeRefreshLayout{
    public MySwipRefreshView(Context context) {
        super(context);
    }

    public MySwipRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float x;
    float y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                x=ev.getX();
                y=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                boolean value=super.onInterceptTouchEvent(ev);
                if(value){
                    float deltaX=Math.abs(ev.getX()-x);
                    float deltaY=Math.abs(ev.getY()-y);
                    //计算x和y的角度来判断到底是否真的上下滑动按照上下45度来判断
                    double radian=Math.atan2(deltaY,deltaX);
                    double deg=Math.toDegrees(radian);
                    if(deg>=60){
                        return true;
                    }else{
                        return false;
                    }
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
