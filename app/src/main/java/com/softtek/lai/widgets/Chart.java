package com.softtek.lai.widgets;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.softtek.lai.R;

/**
 * Created by John on 2016/4/11.
 *
 */
public class Chart extends View{

    private static final String TAG="Chart";

    private float mFirst;//第一段弧线数值
    private float mSecond;//第二段弧线数值
    private float mThird;//第三段弧线数值
    private float fStartAngle=120;//第一段弧线的初始角度
    private float sStartAngle;//第二段弧线的初始角度
    private float tStartAngle;//第三段弧线的初始角度

    private Float text=123456f;
    private String tip="本月总减重";
    private String unit="斤";
    private int mTextUnitSize=(int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
    private int mTextTipSize=(int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private int mTextSize=(int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    private int mWidth;
    private int mHeight;
    private float mRadius;
    private int mCircleStroke;
    private int cx;
    private int cy;

    private Paint mPaintFirst;
    private Paint mPaintSecond;
    private Paint mPaintThird;
    private Paint mTextPaint;
    private Paint mTextTipPaint;
    private Paint mTextUnitPaint;
    private RectF mChartRect;
    private Rect mTextRect=new Rect();

    private boolean empty=true;

    private AnimatorSet set;

    public Chart(Context context) {
        super(context);
        init(context,null);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        //获取自定义属性
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.Chart);
        mTextSize=ta.getDimensionPixelOffset(R.styleable.Chart_numSize,mTextSize);
        mTextTipSize=ta.getDimensionPixelOffset(R.styleable.Chart_tipSize,mTextTipSize);
        mTextUnitSize=ta.getDimensionPixelOffset(R.styleable.Chart_unitSize, mTextUnitSize);
        text=ta.getFloat(R.styleable.Chart_totleNum, 0);
        ta.recycle();
        mCircleStroke=30;
        mPaintFirst =new Paint();
        mPaintFirst.setAntiAlias(true);
        mPaintFirst.setStrokeWidth(mCircleStroke);
        mPaintFirst.setColor(Color.parseColor("#ffc200"));
        mPaintFirst.setStyle(Paint.Style.STROKE);

        mPaintSecond =new Paint();
        mPaintSecond.setAntiAlias(true);
        mPaintSecond.setStrokeWidth(mCircleStroke);
        mPaintSecond.setColor(Color.parseColor("#5a9cd4"));
        mPaintSecond.setStyle(Paint.Style.STROKE);

        mPaintThird =new Paint();
        mPaintThird.setAntiAlias(true);
        mPaintThird.setStrokeWidth(mCircleStroke);
        mPaintThird.setColor(Color.parseColor("#73bc2a"));
        mPaintThird.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setStrokeWidth(2);
        mTextPaint.getTextBounds(String.valueOf(text), 0, String.valueOf(text).length(), mTextRect);

        mTextTipPaint=new Paint();
        mTextTipPaint.setAntiAlias(true);
        mTextTipPaint.setColor(Color.parseColor("#6f6f6f"));
        mTextTipPaint.setTextSize(mTextTipSize);
        mTextTipPaint.setTextAlign(Paint.Align.CENTER);
        mTextTipPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextUnitPaint=new Paint();
        mTextUnitPaint.setAntiAlias(true);
        mTextUnitPaint.setColor(Color.parseColor("#333333"));
        mTextUnitPaint.setTextSize(mTextUnitSize);
        mTextUnitPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        set=new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不考虑wrap_content
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    boolean first=false;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(!first){
            first=true;
            mWidth=right-left;
            mHeight=bottom-top;
            int min=Math.min(mWidth,mHeight);
            mRadius=min/2;
            //以中心为圆点定一个外接矩形
            cx=mWidth/2;
            cy=mHeight/2;
            mChartRect=new RectF(
                    cx-mRadius+mCircleStroke,
                    cy-mRadius+mCircleStroke,
                    cx+mRadius-mCircleStroke,
                    cy+mRadius-mCircleStroke);
        }
    }

    public void setValue(float...value){
        if(value.length<3){
            throw new RuntimeException("数值参数小于3个");
        }

        float anglePer=360/(value[0]+value[1]+value[2]);
        float angle1=anglePer*value[0];
        float angle2=anglePer*value[1];
        float angle3=anglePer*value[2];
        sStartAngle=angle1+fStartAngle;
        tStartAngle=angle2+sStartAngle;
        text=value[0] + value[1] + value[2];
        if(value[0]==0&&value[1]==0&&value[2]==0){
            empty=true;
            postInvalidate();
            return;
        }else{
            empty=false;
        }
        ValueAnimator valueAnimator1=ValueAnimator.ofFloat(0, angle1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mFirst = value;
            }
        });
        ValueAnimator valueAnimator2=ValueAnimator.ofFloat(0, angle2);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mSecond = value;
            }
        });
        ValueAnimator valueAnimator3=ValueAnimator.ofFloat(0, angle3);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mThird = value;
                postInvalidate();
            }
        });
       /* ValueAnimator valueAnimator4=ValueAnimator.ofFloat(0,totleText);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value= (Float) animation.getAnimatedValue();
                text=value;
                postInvalidate();
            }
        });*/
        set.playTogether(valueAnimator1, valueAnimator2,valueAnimator3/*,valueAnimator4*/);
        set.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(!empty){
            canvas.drawArc(mChartRect,fStartAngle, mFirst, false, mPaintFirst);
            canvas.drawArc(mChartRect, sStartAngle, mSecond, false, mPaintSecond);
            canvas.drawArc(mChartRect, tStartAngle, mThird, false, mPaintThird);
        }
        canvas.drawText(String.valueOf(text), (getRight() - getLeft()) / 2,
                (getBottom() - getTop()) / 2, mTextPaint);
        canvas.drawText(tip, (getRight() - getLeft()) / 2,
                (getBottom() - getTop()) / 2 + (mTextRect.bottom - mTextRect.top) * 1.2f, mTextTipPaint);
        canvas.drawText(unit,(getRight() - getLeft()) / 2+mTextPaint.measureText(String.valueOf(text))/2,
                (getBottom() - getTop()) / 2,mTextUnitPaint);
    }
}
