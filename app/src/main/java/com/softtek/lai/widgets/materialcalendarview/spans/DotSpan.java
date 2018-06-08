package com.softtek.lai.widgets.materialcalendarview.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import android.util.TypedValue;

import com.softtek.lai.contants.Constants;
import com.softtek.lai.widgets.materialcalendarview.MaterialCalendarView;

/**
 * Span to draw a dot centered under a section of text
 */
public class DotSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;
    private int mode;
    private  float radius;
    private  int color;
    private int radiuscolor;
    private String mText;
    private Context mContext;
    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see#DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
//    public DotSpan() {
//        this.radius = DEFAULT_RADIUS;
//        this.color = 0;
//    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see#DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
//    public DotSpan(int color) {
//        this.radius = DEFAULT_RADIUS;
//        this.color = color;
//    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see#DotSpan(float, int)
     */
//    public DotSpan(float radius) {
//        this.radius = radius;
//        this.color = 0;
//    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @paramradius radius for the dot
     * @paramcolor  color of the dot
     */


    public DotSpan(Context context, int mode, int radius) {
        this.mContext = context;
        this.mode = mode;
        if (this.mode == Constants.ACTIVITY) {
            this.color = Color.rgb(0, 0, 0);
            this.mText = "活动";
            this.radius = radius;
            this.radiuscolor = Color.rgb(237, 118, 108);

        } else if (this.mode == Constants.CREATECLASS) {
            this.color = Color.rgb(0, 0, 0);
            this.mText = "开班";

        } else if (this.mode == Constants.RESET) {
            this.color = Color.rgb(0, 0, 0);
            this.mText = "复测";
            this.radius = radius;
            this.radiuscolor = Color.rgb(247, 171, 38);

        } else if (this.mode == Constants.FREE) {
            this.color = Color.rgb(204, 204, 204);
            this.mText = "空闲";

        }
    }

//    public DotSpan(float radius, int color,int mode) {
//          this.mode=mode;
//        if (this.mode== Constants.ACTIVITY){
//            this.color= Color.rgb(237, 118, 108);
//        }else if(this.mode==Constants.CREATECLASS){
//            this.color=color;
//
//        }else if(this.mode==Constants.RESET){
//            this.color= Color.rgb(247, 171, 38);
//
//        }else if(this.mode== Constants.FREE){
//            this.color=color;
//        }
//        this.radius = radius;
//    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {

        //文字
        Paint p = new Paint();
        p.setColor(color);
        p.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics()));
        float center = (right + left) / 2;
        float textLength = p.measureText(mText);
        canvas.drawText(mText, center - textLength / 2, bottom + baseline - 13, p);


        //圆点
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        canvas.drawCircle((left + right) / 2, bottom + radius+29, radius, paint);
        paint.setColor(oldColor);


    }
}
