package com.softtek.lai.widgets.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;


/**
 * Created by jerry.guan on 11/8/2016.
 */

public class Chart extends LinearLayout{

    private String title1;
    private String title2;

    private TextView tv_title1;
    private TextView tv_title2;
    private BrokenLine brokenLine;
    private DataLine line;

    public Chart(Context context) {
        super(context);
        init(null);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        line=new DataLine();
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.chart,this);
        TypedArray ta=getContext().obtainStyledAttributes(attrs,R.styleable.Simple_Chart);
        title1=ta.getString(R.styleable.Simple_Chart_chartTitle1);
        title2=ta.getString(R.styleable.Simple_Chart_chartTitle2);
        float xTextSize=ta.getDimension(R.styleable.Simple_Chart_xTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                10,getContext().getResources().getDisplayMetrics()));
        ta.recycle();
        tv_title1= (TextView) findViewById(R.id.tv_title1);
        tv_title2= (TextView) findViewById(R.id.tv_title2);
        brokenLine= (BrokenLine) findViewById(R.id.broken);
        brokenLine.setxTextSize(xTextSize);
        tv_title1.setText(TextUtils.isEmpty(title1)?"":title1);
        tv_title2.setText(TextUtils.isEmpty(title2)?"":title2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    public void setDate(List<String> xAxis,List<Entry> yAxis,float max){
        line.setxAxis(xAxis);
        line.setEntries(yAxis);
        line.setMaxYAxis(max);
        brokenLine.setData(line);
    }
    public void setDate(List<String> xAxis,List<Entry> yAxis,float max,int format){
        line.setxAxis(xAxis);
        line.setEntries(yAxis);
        line.setMaxYAxis(max);
        brokenLine.setData(line,format);
    }

    public List<String > getXAxis(){
        return line.getxAxis();
    }
    public List<Entry > getYAxis(){
        return line.getEntries();
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
        if(tv_title1!=null){
            tv_title1.setText(title1);
        }
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
        if(tv_title2!=null){
            tv_title2.setText(title2);
        }
    }
}
