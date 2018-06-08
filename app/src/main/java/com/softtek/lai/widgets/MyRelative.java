package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author jerry.Guan
 *         created by 2016/12/26
 */

public class MyRelative extends RelativeLayout{
    public MyRelative(Context context) {
        super(context);
    }

    public MyRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    float x;
    float y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        int action=ev.getAction();
//        switch (action){
//            case MotionEvent.ACTION_DOWN:
//                x=ev.getX();
//                y=ev.getY();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                boolean value=super.onInterceptTouchEvent(ev);
//                if(value){
//                    float deltaX=Math.abs(ev.getX()-x);
//                    float deltaY=Math.abs(ev.getY()-y);
//                    //计算x和y的角度来判断到底是否真的上下滑动按照上下45度来判断
//                    double radian=Math.atan2(deltaY,deltaX);
//                    double deg=Math.toDegrees(radian);
//                    if(deg>=60){
//                        getParent().getParent().getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
//                        return false;
//                    }else{
//                        getParent().getParent().getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                    }
//                }
//
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }



}
