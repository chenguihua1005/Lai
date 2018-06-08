package com.softtek.lai.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * 将每一个波纹作为单独的一个View,使用canvas+ValueAnimator绘制.
 * 然后外部父布局继承FrameLayout,利用addView+开始动画 -> 结束动画+removeView
 * 不断添加移除子View来实现多个波纹扩散效果.
 * @author jerry.Guan
 * @date 2016/10/16
 */

public class OnceRippleView extends View{

    public OnceRippleView(Context context) {
        super(context);
    }

    public OnceRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint paint = new Paint();

    {
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }


    private int fromRadius;
    private int toRadius;
    private int color;
    private int strokeWidth;
    private int duration;
    private Interpolator interpolator;

    private int currentRadius;


    @Override
    protected void onDraw(Canvas canvas) {
        if (duration == 0)
            return;
        canvas.translate(getWidth() / 2, getHeight() / 2);

        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);

        float perAlpha = (float) 255 / (float) (toRadius - fromRadius);
        int useAlpha = (int) (255 - (perAlpha * (float) (currentRadius - fromRadius)));
        paint.setAlpha(useAlpha);

        canvas.drawCircle(0, 0, currentRadius - strokeWidth, paint);
    }


    /*
     *
     *
     */


    /**
     * @param fromRadius  初始半径
     * @param toRadius    目标半径
     * @param color       颜色
     * @param strokeWidth 圆圈的strokeWidth
     * @param duration    动画时间
     */
    public void init(int fromRadius, int toRadius, int color, int strokeWidth, int duration, Interpolator interpolator) {
        this.fromRadius = fromRadius;
        this.toRadius = toRadius;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.duration = duration;
        this.interpolator = interpolator;
    }


    public void start(final Runnable overCallback) {
        if (duration == 0)
            return;
        ValueAnimator anim = ValueAnimator.ofInt(fromRadius, toRadius);
        anim.setDuration(duration);
        anim.setInterpolator(interpolator);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                currentRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                if (overCallback != null)
                    overCallback.run();
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();
    }
}
