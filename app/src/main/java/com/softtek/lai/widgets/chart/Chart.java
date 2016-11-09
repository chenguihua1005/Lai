package com.softtek.lai.widgets.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.softtek.lai.R;


/**
 * Created by jerry.guan on 11/8/2016.
 */

public class Chart extends LinearLayout{


    public Chart(Context context) {
        super(context);
        init();
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.chart,this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
