package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.softtek.lai.utils.DisplayUtil;

/**
 * Created by jerry.guan on 10/27/2016.
 */

public class DragTextView extends TextView {

    private int lastX, lastY;

    private int screenWidth,screenHeight;

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
        screenHeight=DisplayUtil.getMobileHeight(getContext());
        screenWidth=DisplayUtil.getMobileWidth(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;

                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }

                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }

                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }

                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }

                layout(left, top, right, bottom);

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
