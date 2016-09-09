package com.softtek.lai.module.studetail.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.softtek.lai.R;
import com.softtek.lai.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2016/4/2.
 */
public class LineChartUtil {

    LineChart chart;
    Context context;

    public LineChartUtil(Context context,LineChart chart) {
        this.chart = chart;
        this.context=context;
    }


    public  void addDataSet(List<Float> datas){
        LineData data= chart.getData();
        if(data==null||datas==null||datas.size()==0){
            return;
        }

        if(data.getXValCount()==0){
            //添加x 轴数值
            for (int i = 0; i <13; i++) {
                data.getXVals().add(i + "");
            }
        }

        ArrayList<Entry> yVals = new ArrayList<>();
        //添加具体数据
        float max=0;
        for (int i = 0; i <data.getXValCount()&&i<datas.size(); i++) {
            float val=datas.get(i);
            //获取数值
            if(val==0){
                continue;
            }else{
                yVals.add(new Entry(val, i));
            }
            if(val>max){
                max=val;
            }
        }
        chart.getAxisLeft().setAxisMaxValue(max+50);
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals,null);
        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);//实心圆点
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);//背景色的开关
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        //填充背景色效果
        if(SystemUtils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_orange);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }
        data.getDataSets().clear();
        data.getDataSets().add(set1);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
    //健康记录重构方法
    public  void addData(List<Float> datas,int n,List<String> dates)
    {
        if(datas.size()==0)
        {
            n=0;
        }
        LineData data= chart.getData();
        if(data==null||datas==null||datas.size()==0){
            return;
        }

        if(data.getXValCount()==0){
            //添加x 轴数值
            for (int i = 0; i <n; i++) {

                data.getXVals().add(dates.get(i));
            }

        }
        else {
            data.getXVals().clear();
            for (int i = 0; i <n; i++) {
                //int f=n;
                data.getXVals().add(dates.get(i));
            }

        }
        ArrayList<Entry> yVals = new ArrayList<>();
        //添加具体数据
        float max=0;
        for (int i = 0; i <data.getXValCount()&&i<datas.size(); i++) {
//            float mult = (50 + 1);
//            float val = (float) (Math.random() * mult) + 3;
            if (!(datas.get(i)==0.0)) {
                float val = datas.get(i);
                //获取数值

                yVals.add(new Entry(val, i));
                if (val > max) {
                    max = val;
                }
            }
        }
        chart.getAxisLeft().setAxisMaxValue(max+10);
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals,null);
        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);//实心圆点
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);//背景色的开关

        //填充背景色效果
        if(SystemUtils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_orange);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }
        //数据按Y轴动画的效果出现
        //chart.animateY(3000, Easing.EasingOption.EaseInCubic);
        //数据按照X轴动画的效果出现
        //chart.animateX(2000);
        //chart.animateXY(2000,2000);
        data.getDataSets().clear();
        data.addDataSet(set1);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
    //莱运动今日步数
    public  void addDataf(List<Integer> datas,int n,List<String> dates)
    {
        if(datas.size()==0)
        {
            n=0;
        }
        LineData data= chart.getData();
        if(data==null||datas==null||datas.size()==0){
            return;
        }

        if(data.getXValCount()==0){
            //添加x 轴数值
            for (int i = 0; i <n; i++) {

                data.getXVals().add(dates.get(i));
            }

        }else {
            //重新添加x轴值
            data.getXVals().clear();
            for (int i = 0; i <n; i++) {
                //int f=n;
                data.getXVals().add(dates.get(i));
            }
        }
        ArrayList<Entry> yVals = new ArrayList<>();
        //添加具体数据
        float max=0;
        for (int i = 0; i <data.getXValCount()&&i<datas.size(); i++) {
                int val = datas.get(i);
                //获取数值
                yVals.add(new Entry(val, i));
                if (val > max) {
                    max = val;
                }
        }
        chart.getAxisLeft().setAxisMaxValue(max+3000);
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals,null);
        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);//实心圆点
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);//背景色的开关
        set1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int val= (int) value;

                return String.valueOf(val);
            }
        });

        //填充背景色效果
        if(SystemUtils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_orange);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }
        data.getDataSets().clear();
        data.addDataSet(set1);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}
