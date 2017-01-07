package com.softtek.lai.widgets.meetmehorizontallistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by curry.zhang on 1/6/2017.
 */

public class MyListview extends ListView {

//    private GestureDetector mGestureDetector;
//    OnTouchListener mGestureListener;

    private int yDown;
    private int xDown;

    public MyListview(Context context) {
        super(context);
    }

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
//        setFadingEdgeLength(0);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev)
//                && mGestureDetector.onTouchEvent(ev);
//    }


//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                float distanceX, float distanceY) {
//            if (Math.abs(distanceY) > Math.abs(distanceX)) {
//                return true;
//            }
//            return false;
//        }

//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            int x1 = (int) e1.getX();
//            int x2 = (int) e2.getX();
//            int y1 = (int) e1.getY();
//            int y2 = (int) e2.getY();
//            int diffX = Math.abs(x1 - x2);
//            int diffY = Math.abs(y1 - y2);
//            return diffY - diffX > 0;
//        }
//    }
}
