package com.softtek.lai.widgets.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.SyncStateContract;
import android.text.style.LineBackgroundSpan;

import com.softtek.lai.chat.Constant;
import com.softtek.lai.contants.Constants;


/**
 * Created by shellybaby on 2016/9/26.
 */
public class Textspan implements LineBackgroundSpan {
    private int mode;
//    public static final float DEFAULT_RADIUS = 3;
//    private  float radius;
    private  int color;
    private String mText;

    //    public Textspan(String mode, String mText) {
    public Textspan(int mode) {
        this.mode = mode;
        this.color = Color.rgb(41, 139, 244);
        if (this.mode== Constants.ACTIVITY){
            this.mText="活动";
//            this.radius=DEFAULT_RADIUS;
//            this.color=0;
        }else if(this.mode==Constants.CREATECLASS){
            this.mText="开班";
//            this.radius=DEFAULT_RADIUS;
//            this.color=0;
        }else if(this.mode==Constants.RESET){
              this.mText="复测";
//            this.radius=DEFAULT_RADIUS;
//            this.color=0;
        }else if(this.mode== Constants.FREE){
            this.mText="空闲";
//            this.radius=DEFAULT_RADIUS;
//            this.color=0;
        }
    }
//    public Textspan(int mode,float radius, int color) {
//        this.mode=mode;
//        this.radius = radius;
//        this.color = color;
//    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        int oldColor = p.getColor();
        float textSize = p.getTextSize();//sp

        p.setColor(color);
        float center = (right + left) / 2;
        float textLength = this.mText.length() * textSize;
        c.drawText(mText, center - textLength / 2, bottom + baseline - 8, p);
        p.setColor(oldColor);

//        int oldColor1 = p.getColor();
//        if (color != 0) {
//            p.setColor(color);
//        }
//        c.drawCircle((left + right) / 2, bottom + radius, radius, p);
//        p.setColor(oldColor);
    }
}
