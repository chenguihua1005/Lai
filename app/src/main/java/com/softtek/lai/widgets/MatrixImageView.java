package com.softtek.lai.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MatrixImageView extends ImageView {
    private static final int MODE_NONE = 0x00123;// 默认的触摸模式
    private static final int MODE_DRAG = 0x00321;// 拖拽模式
    private static final int MODE_ZOOM = 0x00132;// 缩放or旋转模式

    private int mode;// 当前的触摸模式

    private float preMove = 1F;// 上一次手指移动的距离
    private float saveRotate = 0F;// 保存了的角度值
    private float rotate = 0F;// 旋转的角度

    private float[] preEventCoor;// 上一次各触摸点的坐标集合

    private PointF startPointF, midPointF;// 起点、中点对象
    private Matrix currentMatrix, savedMatrix;// 当前和保存了的Matrix对象

    //原始图片
    private Bitmap mSrc;

    //控件的宽度
    private int mWidth;

    // 控件的高度
    private int mHeight;

    private PaintFlagsDrawFilter mDrawFilter;

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 实例化对象

        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        startPointF = new PointF();
        midPointF = new PointF();
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        // 模式初始化
        mode = MODE_NONE;

        Drawable drawable = getDrawable();
        mSrc = drawableToBitamp(drawable);

    }

    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // 设置宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        //match_parent或者设置的精确值获取
        //MeasureSpec.EXACTLY
        if (specMode == MeasureSpec.EXACTLY)
        {
            mWidth = specSize;
        }
        else
        {
            // 由图片决定的宽
            //getPaddingLeft(),getPaddingRight()这两个值是控件属性的向内偏移的距离值，所以的一起计算
            //区别于layout_marginLeft,两个控件的左间距值设置
            int desireByImg = getPaddingLeft() + getPaddingRight()
                    + mSrc.getWidth();

            // wrap_content
            if (specMode == MeasureSpec.AT_MOST)
            {
                //所以最小的值，宽度的话是左右内偏移距离之和
                mWidth = Math.min(desireByImg, specSize);
            } else

                mWidth = desireByImg;
        }

        // 设置高度，部分解释同上
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        //match_parent或者设置的精确值获取
        //MeasureSpec.EXACTLY
        if (specMode == MeasureSpec.EXACTLY)
        {
            mHeight = specSize;
        } else
        {
            int desire = getPaddingTop() + getPaddingBottom()
                    + mSrc.getHeight();

            // wrap_content
            if (specMode == MeasureSpec.AT_MOST)
            {
                mHeight = Math.min(desire, specSize);
            } else
                mHeight = desire;
        }

        //计算好的宽度以及高度是值，设置进去
        setMeasuredDimension(mWidth, mHeight);
    }

    //drawable转bitmap
    private Bitmap drawableToBitamp(Drawable drawable)
    {
        //从控件的src获取背景，也是drawable文件获取
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }

        //如果没有绘图一个，只不过是空白的图片
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    final Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        //消除锯齿, 图片旋转后的锯齿消除不成功，实在不行图片边缘加一些白色像素点
        canvas.setDrawFilter(mDrawFilter);
        //画经过Matrix变化后的图
        canvas.drawBitmap(mSrc, currentMatrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:// 单点接触屏幕时
                savedMatrix.set(currentMatrix);
                startPointF.set(event.getX(), event.getY());
                //单点触摸是移动模式
                mode = MODE_DRAG;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:// 第二个点接触屏幕时
                preMove = calSpacing(event);
                if (preMove > 10F) {
                    savedMatrix.set(currentMatrix);
                    // 计算两个触摸点的中点坐标
                    calMidPoint(midPointF, event);
                    //两点是旋转或者缩放模式
                    mode = MODE_ZOOM;
                }
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_UP:// 单点离开屏幕时
            case MotionEvent.ACTION_POINTER_UP:// 第二个点离开屏幕时
                mode = MODE_NONE;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_MOVE:// 触摸点移动时
            /*
             * 单点触控拖拽平移
             */
                if (mode == MODE_DRAG) {
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - startPointF.x;
                    float dy = event.getY() - startPointF.y;
                    currentMatrix.postTranslate(dx, dy);
                }
            /*
             * 两点触控拖放旋转
             */
                else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {
                    float currentMove = calSpacing(event);
                    currentMatrix.set(savedMatrix);
                /*
                 * 指尖移动距离大于10F缩放
                 */
                    if (currentMove > 10F) {
                        float scale = currentMove / preMove;
                        currentMatrix.postScale(scale, scale, midPointF.x, midPointF.y);
                    }
                /*
                 * 保持两点时旋转
                 */
                    if (preEventCoor != null) {
                        rotate = calRotation(event);
                        r = rotate - saveRotate;
                        currentMatrix.postRotate(r, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }
                break;
        }

        setImageMatrix(currentMatrix);
        return true;
    }

    float r;

    /**
     * 计算两个触摸点间的距离
     */
    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 计算两个触摸点的中点坐标
     */
    private void calMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 计算旋转角度
     *
     * @param event 事件对象
     * @return 角度值
     */
    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));
        double radius = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radius);
    }

}