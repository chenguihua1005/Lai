package com.softtek.lai.widgets.materialcalendarview.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.softtek.lai.contants.Constants;

/**
 * Span to draw a dot centered under a section of text
 */
public class DotSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;
    private int mode;
    private final float radius;
    private  int color;
    private Context mContext;
    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see#DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public DotSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see#DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public DotSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see#DotSpan(float, int)
     */
    public DotSpan(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param radius radius for the dot
     * @param color  color of the dot
     */
    public DotSpan(float radius, int color,int mode) {
          this.mode=mode;
        if (this.mode== Constants.ACTIVITY){
            this.color= Color.rgb(237, 118, 108);
//
        }else if(this.mode==Constants.CREATECLASS){
            this.color=color;
//
        }else if(this.mode==Constants.RESET){
            this.color= Color.rgb(247, 171, 38);
//
        }else if(this.mode== Constants.FREE){
            this.color=color;
        }
        this.radius = radius;
//
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        canvas.drawCircle((left + right) / 2, bottom + radius+25, radius, paint);
        paint.setColor(oldColor);
    }
}
