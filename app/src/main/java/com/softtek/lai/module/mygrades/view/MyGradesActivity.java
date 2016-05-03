package com.softtek.lai.module.mygrades.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_grades)
public class MyGradesActivity extends BaseActivity {

    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    protected void initViews() {
//        //初始化统计图
//        //取消统计图整体背景色
//        chart.setDrawGridBackground(false);
//        //取消描述信息,设置没有数据的时候提示信息
//        chart.setDescription("");
//        chart.setNoDataTextDescription("暂无数据");
//        //启用手势操作
//        chart.setTouchEnabled(true);
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setPinchZoom(true);
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.setAxisMaxValue(100f);
//        leftAxis.setAxisMinValue(0f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);//不启用0轴的线
//        chart.getAxisRight().setEnabled(false);//取消右边的轴线
//        chart.setData(new LineData());//设置一个空数据
//        radio_group.setOnCheckedChangeListener(this);
//        bt_left.setOnClickListener(this);
//        bt_right.setOnClickListener(this);
//        week.setOnClickListener(this);
//        month.setOnClickListener(this);
//        quarter.setOnClickListener(this);
//        year.setOnClickListener(this);
//    }
    @Override
    protected void initViews() {
        title.setText("我的成绩");
        tv_right.setText("分享");
    }

    @Override
    protected void initDatas() {

    }
}
