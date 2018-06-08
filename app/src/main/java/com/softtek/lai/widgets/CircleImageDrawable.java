package com.softtek.lai.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by shelly.xu on 4/5/2017.
 */

public class CircleImageDrawable extends Drawable {
    private Paint mPaint;
    private Bitmap bitmap;
    private Matrix matrix;
    private BitmapShader shader;
    public CircleImageDrawable(Bitmap bitmap) {
        this.bitmap=bitmap;
        shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        matrix=new Matrix();
    }
    @Override
    public void draw(Canvas canvas) {
        //获取imageView的矩形边框
        Rect rect=getBounds();
        //缩放比，使用imageView的宽和原图的宽得到一个宽的比例，在同理获取高的比例，取最大值
        /**
         * 取最大值的原因是因为：
         * view的宽高为100*200；图片的宽高为50*300；
         * 宽的比例是：2：1，高的比例是2：3
         * 最终我们应该按照宽的比例放大，而不是按照高的比例缩小；
         * 如果高的比例大于我们宽的比例那么就是取高的比例了
         * 因为我们需要让缩放后的图片，大于我们的view宽高，并保证原图比例。
         */
        float scale = Math.max(rect.width() * 1.0f / bitmap.getWidth(), rect.height()
                * 1.0f / bitmap.getHeight());
        matrix.setScale(scale,scale);
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);
        //获取imageView的最短宽高
        int min=Math.min(rect.height(),rect.width());
        //将图片永远画在imageView控件的的中心，min>>1表示圆的半径取控件最小的距离
        canvas.drawCircle(rect.centerX(),rect.centerY(),min>>1,mPaint);
    }
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    //下面两个方法主要用于在view使用wrapContent的时候使用的
    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();
    }
    @Override
    public int getIntrinsicWidth() {
        return bitmap.getWidth();
    }
}
