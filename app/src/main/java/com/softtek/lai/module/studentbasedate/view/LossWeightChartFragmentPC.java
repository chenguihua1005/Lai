package com.softtek.lai.module.studentbasedate.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studetail.eventModel.LineChartEvent;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.module.studetail.util.LineChartUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by John on 2016/4/2.
 *
 */
@InjectLayout(R.layout.fragment_loss_weight_chart)
public class LossWeightChartFragmentPC extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private static LossWeightChartFragmentPC fragment=null;

    @InjectView(R.id.chart)
    LineChart chart;

    @InjectView(R.id.radio_group)
    RadioGroup radio_group;
    @InjectView(R.id.radio_loss_weight)
    RadioButton radio_loss_weight;
    @InjectView(R.id.radio_body_fat)
    RadioButton radio_body_fat;
    @InjectView(R.id.radio_fat)
    RadioButton radio_fat;

    private LineChartUtil chartUtil;
    private List<Float> lossWeightDatas=new ArrayList<>();
    private List<Float> bodyFatDatas=new ArrayList<>();
    private List<Float> fatDatas=new ArrayList<>();

    private IMemberInfopresenter memberInfopresenter;

    /**
     * 设置一些参数
     * @return
     */
    public static LossWeightChartFragmentPC newInstance() {
        if(fragment==null){
            fragment=new LossWeightChartFragmentPC();
        }
        return fragment;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        //初始化统计图
        //取消统计图整体背景色
        chart.setDrawGridBackground(false);
        //取消描述信息,设置没有数据的时候提示信息
        chart.setDescription("");
        chart.setNoDataTextDescription("暂无数据");
        //启用手势操作
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.getLegend().setEnabled(false);//去除图例

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        radio_group.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initDatas() {
        chartUtil=new LineChartUtil(getContext(),chart);
        //chartUtil.addDataSet(null);
        memberInfopresenter=new MemberInfoImpl(getContext(),null);
        //请求网络数据
        memberInfopresenter.getLossWeightChartDataPC();

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(LineChartEvent event){
        lossWeightDatas.clear();
        bodyFatDatas.clear();
        fatDatas.clear();
        for(int i=0;i<event.getModels().size();i++){
            StudentLinChartInfoModel model=event.getModels().get(i);
            int week=model.getWeekDay();//本次测量周
            if(i==0){
                /*
                第一条数据表示该用户本班级开始测量的第一次
                需要判断插入多少条一开始的空值，因为该用户未必是从班级一开班就开始测的，
                有可能是从班级中间开始测的
                 */
                addEmptyDate(model.getWeekDay()-1);//先插入几次空数据
                //在插入第一次数据
                lossWeightDatas.add(getFloat(model.getWeight()));
                bodyFatDatas.add(getFloat(model.getPysical()));
                fatDatas.add(getFloat(model.getFat()));
            }else{
                //先判断这一次和上一次的差
                int lastWeek=event.getModels().get(i-1).getWeekDay();
                if(week-lastWeek>0){
                    //说明中间有断层则继续插入多少空数据
                    addEmptyDate(week-lastWeek);
                }
                lossWeightDatas.add(getFloat(model.getWeight()));
                bodyFatDatas.add(getFloat(model.getPysical()));
                fatDatas.add(getFloat(model.getFat()));

            }


        }
        chartUtil.addDataSet(lossWeightDatas);

    }
    private float getFloat(String str){
        return str==null||"".equals(str)?0f:Float.parseFloat(str);
    }
    //插入几次空数据
    private void addEmptyDate(int n){
        for(int i=0;i<n;i++){
            lossWeightDatas.add(0f);
            bodyFatDatas.add(0f);
            fatDatas.add(0f);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_loss_weight:
                radio_loss_weight.setTextColor(Color.WHITE);
                radio_body_fat.setTextColor(0xFF74BB2A);
                radio_fat.setTextColor(0xFF74BB2A);
                chartUtil.addDataSet(lossWeightDatas);
                break;
            case R.id.radio_body_fat:
                radio_loss_weight.setTextColor(0xFF74BB2A);
                radio_body_fat.setTextColor(Color.WHITE);
                radio_fat.setTextColor(0xFF74BB2A);
                chartUtil.addDataSet(bodyFatDatas);
                break;
            case R.id.radio_fat:
                radio_loss_weight.setTextColor(0xFF74BB2A);
                radio_body_fat.setTextColor(0xFF74BB2A);
                radio_fat.setTextColor(Color.WHITE);
                chartUtil.addDataSet(fatDatas);
                break;
        }
    }
}
