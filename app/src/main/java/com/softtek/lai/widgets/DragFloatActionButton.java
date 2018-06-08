package com.softtek.lai.widgets;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.utils.DisplayUtil;

/**
 * Created by jerry.guan on 2/15/2017.
 */

public class DragFloatActionButton extends FloatingActionButton{

    private int screenWidth;
    private int screenHeight;
    private int screenWidthHalf;
    private int statusHeight;
    private int touchSlop;

    public DragFloatActionButton(Context context) {
        super(context);
        init();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        touchSlop= ViewConfiguration.get(getContext()).getScaledTouchSlop();
        screenWidth= DisplayUtil.getMobileWidth(getContext());
        screenWidthHalf=screenWidth/2;
        screenHeight=DisplayUtil.getMobileHeight(getContext());
        statusHeight=DisplayUtil.getStatusHeight((Activity) getContext());
    }

    private int lastX;
    private int lastY;

    private boolean isDrag;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag=false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX=rawX;
                lastY=rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag=true;
                int dx=rawX-lastX;
                int dy=rawY-lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance= (int) Math.sqrt(dx*dx+dy*dy);
                if(distance==0){
                    isDrag=false;
                    break;
                }
                float x=getX()+dx;
                float y=getY()+dy;
                //检测是否到达边缘 左上右下
                x=x<0?0:x>screenWidth-getWidth()?screenWidth-getWidth():x;
                y=y<statusHeight?statusHeight:y+getHeight()>screenHeight-DisplayUtil.dip2px(getContext(),40)?screenHeight-getHeight()-DisplayUtil.dip2px(getContext(),40):y;
                setX(x);
                setY(y);
                lastX=rawX;
                lastY=rawY;
                //Log.i("getX="+getX()+";getY="+getY()+";screenHeight="+screenHeight);
                break;
            case MotionEvent.ACTION_UP:
                if(isDrag){
                    //恢复按压效果
                    setPressed(false);
                    Log.i("getX="+getX()+"；screenWidthHalf="+screenWidthHalf);
                    if(rawX>=screenWidthHalf){
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(screenWidth-getWidth()-getX())
                                .start();
                    }else {
                        ObjectAnimator oa=ObjectAnimator.ofFloat(this,"x",getX(),0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }
                }
                break;
        }
        return isDrag || super.onTouchEvent(event);
    }
}
