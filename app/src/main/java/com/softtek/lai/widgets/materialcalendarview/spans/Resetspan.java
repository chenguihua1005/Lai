package com.softtek.lai.widgets.materialcalendarview.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import android.util.TypedValue;

import com.softtek.lai.contants.Constants;


/**
 * Created by shellybaby on 2016/9/26.
 */
public class Resetspan implements LineBackgroundSpan {

    private Context mContext;
    private int color;
    private String mText;


    public Resetspan(Context context,String weekTh) {
        this.mContext = context;
        this.color = Color.rgb(0, 0, 0);
        this.mText = "复测"+weekTh;
    }


    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        //文字
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics()));
        float center = (right + left) / 2;
        float textLength = paint.measureText(mText);
        c.drawText(mText, center - textLength / 2, bottom + baseline - 13, paint);
    }
}
