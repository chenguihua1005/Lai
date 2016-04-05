package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.github.snowdream.android.util.Log;

/**
 * Created by John on 2016/4/2.
 */
public class MyScrollView extends ScrollView{

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                    if(deg>45){
                        Log.i("计算得出此次的角度是="+deg+"是上下滑动");
                        return true;
                    }else{
                        Log.i("计算得出此次的角度是="+deg+"不是上下滑动");
                        return false;
                    }
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
