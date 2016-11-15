package com.softtek.lai.widgets.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;


/**
 * Created by shellybaby on 2016/9/26.
 */
public class Textspan implements LineBackgroundSpan {
    private String mode;
    private int color;
    private String mText;

    //    public Textspan(String mode, String mText) {
    public Textspan(String mode) {
        this.mode = mode;
        this.color = Color.rgb(41, 139, 244);
//        if (this.mode.equals(AppConstant.Atype)) {
//            mText = "A";
//        } else if (this.mode.equals(AppConstant.Btype)) {
//            mText = "B";
//        } else if (this.mode.equals(AppConstant.Ctype)) {
//            mText = "C";
//        } else if (this.mode.equals(AppConstant.D1type)) {
//            mText = "本休";
//        } else if (this.mode.equals(AppConstant.D2type)) {
//            mText = "调休";
//        } else if (this.mode.equals(AppConstant.D3type)) {
//            mText = "年假";
//        } else if (this.mode.equals(AppConstant.D4type)) {
//            mText = "事假";
//        } else if (this.mode.equals(AppConstant.D5type)) {
//            mText = "病假";
//        } else if (this.mode.equals(AppConstant.D6type)) {
//            mText = "婚假";
//        } else if (this.mode.equals(AppConstant.D7type)) {
//            mText = "产假";
//        }
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        int oldColor = p.getColor();
        float textSize = p.getTextSize();//sp

        p.setColor(color);
        float center = (right + left) / 2;
        float textLength = this.mText.length() * textSize;
        c.drawText(mText, center - textLength / 2, bottom + baseline - 8, p);
        p.setColor(oldColor);

//        int oldColor = paint.getColor();
//        if (color != 0) {
//            paint.setColor(color);
//        }
//        canvas.drawCircle((left + right) / 2, bottom + radius, radius, paint);
//        paint.setColor(oldColor);
    }
}
