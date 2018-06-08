/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.softtek.lai.R;

public class RoundImageView extends ImageView {

    private int width;
    private int height;
    private float raduis;

    public RoundImageView(Context context) {
        super(context);
        init(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        raduis=ta.getDimension(R.styleable.RoundImageView_raduis,10);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        Log.i("", "w = " + width);
        Log.i("", "h = " + height);
        this.setMeasuredDimension(width, height);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        BitmapDrawable mDrawable = (BitmapDrawable) this.getDrawable();

        if (mDrawable == null) {
            return;
        }
        if (width == 0 || height == 0) {
            return;
        }

        Bitmap bitmap = mDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);

        //Log.i("", "bitmap w = " + bitmap.getWidth());
        //Log.i("", "bitmap h = " + bitmap.getHeight());

        canvas.drawBitmap(bitmapCircle(bitmap, width, height), 0, 0, null);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    private Bitmap bitmapCircle(Bitmap source, int newHeight, int newWidth) {
        Bitmap bmp = scaleCenterCrop(source, newHeight, newWidth);

        Bitmap bmpCircle = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(bmpCircle);
        RectF rectF = new RectF(0, 0, bmpCircle.getWidth(), bmpCircle.getHeight());

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        mCanvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);

        //mCanvas.drawCircle(bmpCircle.getWidth() / 2, bmpCircle.getHeight() / 2, bmpCircle.getWidth() / 2, paint);
        mCanvas.drawRoundRect(rectF,raduis,raduis,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(bmp, null, rectF, paint);

        return bmpCircle;
    }

    private Bitmap scaleCenterCrop(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        float xScale = (float) width / w;
        float yScale = (float) height / h;
        float scale = Math.max(xScale, yScale);

        float scaleWidth = scale * w;
        float scaleHeight = scale * h;

        float left = (width - scaleWidth) / 2;
        float top = (height - scaleHeight) / 2;

        RectF rectF = new RectF(left, top, left + scaleWidth, top + scaleHeight);

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bitmap, null, rectF, null);

        return bmp;
    }

    public void setRaduis(float raduis) {
        this.raduis = raduis;
        postInvalidate();
    }
}
