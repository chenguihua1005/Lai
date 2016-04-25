package com.softtek.lai.module.studetail.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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
@InjectLayout(R.layout.fragment_dimension_chart)
public class DimensionChartFragment extends BaseFragment implements View.OnClickListener{

    private static DimensionChartFragment fragment=null;

    @InjectView(R.id.chart)
    LineChart chart;

    @InjectView(R.id.radio_bust)
    CheckBox radio_bust;
    @InjectView(R.id.radio_waist)
    CheckBox radio_waist;
    @InjectView(R.id.radio_arm)
    CheckBox radio_arm;
    @InjectView(R.id.radio_upper_arm)
    CheckBox radio_upper_arm;
    @InjectView(R.id.radio_thign)
    CheckBox radio_thign;
    @InjectView(R.id.radio_shin)
    CheckBox radio_shin;

    private LineChartUtil chartUtil;
    private IMemberInfopresenter memberInfopresenter;
    private List<Float> circumDatas=new ArrayList<>();
    private List<Float> waistlineDatas=new ArrayList<>();
    private List<Float> hiplieDatas=new ArrayList<>();
    private List<Float> upArmGirthDatas=new ArrayList<>();
    private List<Float> upLegGirthDatas=new ArrayList<>();
    private List<Float> doLegGirthDatas=new ArrayList<>();
    List<String> day=new ArrayList<String>();

    /**
     * 设置一些参数
     * @param params
     * @return
     */
    public static DimensionChartFragment newInstance( Map<String,String> params) {
        if(fragment==null){
            fragment=new DimensionChartFragment();
        }
        Bundle args = new Bundle();
        Set<String> keys=params.keySet();
        for(String key:keys){
            args.putString(key,params.get(key));
        }
        fragment.setArguments(args);
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

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(150f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        radio_bust.setOnClickListener(this);
        radio_waist.setOnClickListener(this);
        radio_arm.setOnClickListener(this);
        radio_upper_arm.setOnClickListener(this);
        radio_thign.setOnClickListener(this);
        radio_shin.setOnClickListener(this);
        checkBoxes.add(radio_shin);
        checkBoxes.add(radio_bust);
        checkBoxes.add(radio_thign);
        checkBoxes.add(radio_upper_arm);
        checkBoxes.add(radio_arm);
        checkBoxes.add(radio_waist);
    }

    @Override
    protected void initDatas() {
        chartUtil=new LineChartUtil(getContext(),chart);
        memberInfopresenter=new MemberInfoImpl(getContext(),null);
        Bundle args=getArguments();
        String userId=args.getString("userId");
        String classId=args.getString("classId");
        memberInfopresenter.getLossWeightChatData(userId,classId);
        day.add("4/15");
        day.add("4/14");
        day.add("4/13");
        day.add("4/12");
        day.add("4/11");
        day.add("4/10");
        day.add("4/09");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(LineChartEvent event){
        circumDatas.clear();
        waistlineDatas.clear();
        hiplieDatas.clear();
        upArmGirthDatas.clear();
        upLegGirthDatas.clear();
        doLegGirthDatas.clear();
        for(StudentLinChartInfoModel model:event.getModels()){
            circumDatas.add(getFloat(model.getWaistline()));
            waistlineDatas.add(getFloat(model.getWaistline()));
            hiplieDatas.add(getFloat(model.getHiplie()));
            upArmGirthDatas.add(getFloat(model.getUpArmGirth()));
            upLegGirthDatas.add(getFloat(model.getUpLegGirth()));
            doLegGirthDatas.add(getFloat(model.getDoLegGirth()));
        }
        chartUtil.addDataSet(circumDatas);
        chartUtil.addDataSet(waistlineDatas);
        chartUtil.addDataSet(hiplieDatas);
        chartUtil.addDataSet(upLegGirthDatas);
        chartUtil.addDataSet(upArmGirthDatas);
        chartUtil.addDataSet(doLegGirthDatas);
    }

    private float getFloat(String str){
        return str==null||"".equals(str)?0f:Float.parseFloat(str);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private int old_radio_id=R.id.radio_bust;//默认是第一个按钮
    private List<CheckBox> checkBoxes=new ArrayList<>();



    @Override
    public void onClick(View v) {
        int id=v.getId();
        ((CheckBox)v).setTextColor(Color.WHITE);
        if(id==old_radio_id){
            ((CheckBox)v).setChecked(true);
        }else {
            setOldColor(old_radio_id);
            old_radio_id=id;
        }
        //清楚其他checkBox状态
        for (CheckBox checkBox:checkBoxes){
            if(checkBox.getId()!=id){
                checkBox.setChecked(false);
            }
        }
        doTask(id);
    }
    private void doTask(int id){
        switch (id){
            case R.id.radio_bust:
                chartUtil.addDataSet(circumDatas);
                break;
            case R.id.radio_thign:
                chartUtil.addDataSet(upLegGirthDatas);
                break;
            case R.id.radio_shin:
                chartUtil.addDataSet(doLegGirthDatas);
                break;
            case R.id.radio_arm:
                chartUtil.addDataSet(hiplieDatas);
                break;
            case R.id.radio_upper_arm:
                chartUtil.addDataSet(upArmGirthDatas);
                break;
            case R.id.radio_waist:
                chartUtil.addDataSet(waistlineDatas);
                break;
        }
    }
    private void setOldColor(int id){
        switch (id){
            case R.id.radio_bust:
                radio_bust.setTextColor(0xFF74BB2A);
                break;
            case R.id.radio_thign:
                radio_thign.setTextColor(0xFF74BB2A);
                break;
            case R.id.radio_shin:
                radio_shin.setTextColor(0xFF74BB2A);
                break;
            case R.id.radio_arm:
                radio_arm.setTextColor(0xFF74BB2A);
                break;
            case R.id.radio_upper_arm:
                radio_upper_arm.setTextColor(0xFF74BB2A);
                break;
            case R.id.radio_waist:
                radio_waist.setTextColor(0xFF74BB2A);
                break;
        }
    }
}
