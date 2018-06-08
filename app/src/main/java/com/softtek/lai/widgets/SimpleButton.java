package com.softtek.lai.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.softtek.lai.R;

public class SimpleButton extends View {

	private Bitmap normalBitmap;
	private Bitmap pitchBitmap;
	
	private Paint bitmapPaint;
	private Paint mTextPaint;
	private int currentAlpha; 
	private Rect srcRect;
	private RectF dstRect;
	private int width,height;
	private String mText="";
	private int mTextSize=(int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
	private Rect mTextBound=new Rect();

	public SimpleButton(Context context) {
		super(context);
		init(context);
	}

	public SimpleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttr(context, attrs, 0);
		init(context);
	}
	

	public SimpleButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttr(context, attrs, defStyleAttr);
		init(context);
	}

	//初始化控件
	private void init(Context context){
		
		bitmapPaint=new Paint();
		bitmapPaint.setAntiAlias(true);
		mTextPaint=new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		// 得到text绘制范围
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}
	private void initAttr(Context context,AttributeSet attrs, int defStyle){
		//获取配置属性
		TypedArray tArray= context.obtainStyledAttributes(attrs, R.styleable.weixin);
		//获取属性
		normalBitmap= ((BitmapDrawable)tArray.getDrawable(R.styleable.weixin_normalBitmap)).getBitmap();
		pitchBitmap=((BitmapDrawable)tArray.getDrawable(R.styleable.weixin_pitchBitmap)).getBitmap();
		mText=tArray.getString(R.styleable.weixin_text);
		boolean alpha=tArray.getBoolean(R.styleable.weixin_wx_alpha, false);
		tArray.recycle();
		currentAlpha=alpha?255:0;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {		
		super.onSizeChanged(w, h, oldw, oldh);
		width=getWidth();
		height=getHeight();
		if(srcRect==null){
			srcRect=new Rect();
			srcRect.left = 0;
			srcRect.right = normalBitmap.getWidth();
			srcRect.top = 0;
			srcRect.bottom = normalBitmap.getHeight();
		}
		if (dstRect == null) {
			dstRect = new RectF();
			
			//double ratio = 0.1;
			//高度
			dstRect.top = (float) (height / 4f);
			dstRect.bottom = (float) (height * 9 / 13);

			int bitmapWidth = (int) (normalBitmap.getWidth()
					* (dstRect.bottom - dstRect.top) / normalBitmap.getHeight());
			//目标区域居中
			dstRect.left = (width - bitmapWidth) / 2;
			dstRect.right = dstRect.left + bitmapWidth;
			
			//System.out.println("top:"+dstRect.top+" bottom:"+dstRect.bottom+" left:"+dstRect.left+" right:"+dstRect.right);
			//System.out.println("高:"+(dstRect.bottom-dstRect.top)+" 宽:"+(dstRect.left-dstRect.right));
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);
		canvas.save();		
		bitmapPaint.setAlpha(255-currentAlpha);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		canvas.drawBitmap(normalBitmap, srcRect,dstRect, bitmapPaint);
		
		bitmapPaint.setAlpha(currentAlpha);
		canvas.drawBitmap(pitchBitmap, srcRect, dstRect, bitmapPaint);
		drawSourceText(canvas, currentAlpha);
		drawTargetText(canvas, currentAlpha);
		/*canvas.drawText(mText, dstRect.left + dstRect.width() / 2
						- mTextBound.width() / 2,
				dstRect.bottom + mTextBound.height(), mTextPaint);*/
		canvas.restore();
	}

	private void drawSourceText(Canvas canvas, int alpha)
	{
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xffCCCCCC);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, dstRect.left + dstRect.width() / 2
						- mTextBound.width() / 2,
				dstRect.bottom + mTextBound.height()+5, mTextPaint);
	}

	private void drawTargetText(Canvas canvas, int alpha)
	{
		mTextPaint.setColor(0xFF000000);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, dstRect.left + dstRect.width() / 2
						- mTextBound.width() / 2,
				dstRect.bottom + mTextBound.height()+5, mTextPaint);

	}

	public void setProgress(float progress) {
		if (progress > 100) {
			throw new RuntimeException("progress do not > 100");
		}
		currentAlpha = (int) (255 * progress);		
		invalidate();
	}
}
