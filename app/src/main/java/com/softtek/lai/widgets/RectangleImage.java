package com.softtek.lai.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public class RectangleImage extends ImageView {
    public RectangleImage(Context context) {
        super(context);
    }

    public RectangleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height= (int) (width/2);
        setMeasuredDimension(width,height);
    }
}
