package com.softtek.lai.widgets;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.utils.DisplayUtil;

/**
 * Created by jerry.guan on 10/31/2016.
 */

public class DragTextView extends TextView{

    private static int minDistance;

    private int touchSlop;
    private int screenWidth;
    private int screenHeight;
    private int screenWidthHalf;
    private OnClickListener listener;

    public DragTextView(Context context) {
        super(context);
        init();
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        touchSlop=ViewConfiguration.get(getContext()).getScaledTouchSlop();
        screenWidth= DisplayUtil.getMobileWidth(getContext());
        screenWidthHalf=screenWidth>>1;
        screenHeight=DisplayUtil.getMobileHeight(getContext());
        minDistance=DisplayUtil.dip2px(getContext(),15);
    }

    private int dx;
    private int dy;
    private int firstOffsetX;
    private int firstOffsetY;
    private boolean isDrag;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag=false;
                getParent().requestDisallowInterceptTouchEvent(true);
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)getLayoutParams();
                dx=rawX;
                dy=rawY;
                firstOffsetX = rawX- lParams.leftMargin;
                firstOffsetY = rawY- lParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX=rawX-dx;
                int moveY=rawY-dy;
                int distance= (int) Math.sqrt(moveX*moveX+moveY*moveY);
                int offsetX=rawX-firstOffsetX;
                int offsetY=rawY-firstOffsetY;
                int maxX=screenWidth-getWidth();
                int minX=getWidth();
                int minY=DisplayUtil.getStatusHeight2((Activity) getContext());
                int maxY=screenHeight-DisplayUtil.dip2px(getContext(),53)-minY-getHeight();
                if(rawX<minX){
                    offsetX=0;
                }else if(offsetX>maxX){
                    offsetX=maxX;
                }
                if(rawY<minY+getHeight()/2){
                    offsetY=0;
                }else if(rawY>maxY){
                    offsetY=maxY;
                }
                if(distance>touchSlop+minDistance){
                    isDrag=true;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
                    layoutParams.leftMargin = offsetX;
                    layoutParams.topMargin = offsetY;
                    setLayoutParams(layoutParams);
                }else {
                    isDrag=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isDrag){
                    isDrag=false;
                    if(rawX>=screenWidthHalf){
                        if(rawX>screenWidth-getWidth()){
                            rawX=screenWidth-getWidth();
                        }
                        startAnimaLeft(rawX,screenWidth-getWidth());
                    }else {
                        int from;
                        if(rawX<getWidth()){
                            from=0;
                        }else {
                            from=rawX-getWidth();
                        }
                        startAnimaLeft(from,0);
                    }
                }else {
                    if(listener!=null){
                        listener.onClick(this);
                    }
                }
                break;
        }
        return true;
    }

    private void startAnimaLeft(int from,int to){
        ObjectAnimator oa=ObjectAnimator.ofInt(new LayoutWapper(this),"leftMargin",from,to);
        oa.setDuration(500);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.setStartDelay(100);
        oa.start();
    }

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    private class LayoutWapper {
        private View target;

        public LayoutWapper(View target) {
            this.target = target;
        }

        public void setLeftMargin(int value) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
            layoutParams.leftMargin = value;
            target.setLayoutParams(layoutParams);
        }

    }
}
