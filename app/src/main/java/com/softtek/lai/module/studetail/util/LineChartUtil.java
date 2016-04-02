package com.softtek.lai.module.studetail.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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


    public  void addDataSet(List<String> datas){
        LineData data= chart.getData();
        if(data==null){
            return;
        }

        if(data.getXValCount()==0){
            //添加x 轴数值
            for (int i = 0; i < 12; i++) {
                data.getXVals().add(i + "");
            }
        }
        ArrayList<Entry> yVals = new ArrayList<>();
        //添加具体数据
        float max=0;
        for (int i = 0; i < data.getXValCount(); i++) {
            float mult = (50 + 1);
            float val = (float) (Math.random() * mult) + 3;
            yVals.add(new Entry(val, i));
            if(val>max){
                max=val;
            }
        }
        chart.getAxisLeft().setAxisMaxValue(max);
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
        chart.animateXY(2000,2000);
        data.getDataSets().clear();
        data.addDataSet(set1);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}
