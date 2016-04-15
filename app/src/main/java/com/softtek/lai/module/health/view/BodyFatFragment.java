package com.softtek.lai.module.health.view;

import android.app.ProgressDialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.health.model.HealthDateModel;
import com.softtek.lai.module.health.model.MonthDateModel;
import com.softtek.lai.module.health.model.WeekDateModel;
import com.softtek.lai.module.health.presenter.HealthyRecordImpl;
import com.softtek.lai.module.health.presenter.IHealthyRecord;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.studetail.util.LineChartUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by John on 2016/4/12.
 */
@InjectLayout(R.layout.fragment_weight)
public class BodyFatFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,HealthyRecordImpl.HealthyRecordCallback{

    @InjectView(R.id.chart)
    LineChart chart;

    @InjectView(R.id.rg)
    RadioGroup radio_group;
    @InjectView(R.id.week)
    RadioButton week;
    @InjectView(R.id.month)
    RadioButton month;
    @InjectView(R.id.quarter)
    RadioButton quarter;
    @InjectView(R.id.year)
    RadioButton year;


    private LineChartUtil chartUtil;
    List<Float> dates=new ArrayList<Float>();
    List<String>days=new ArrayList<String>();
    IHealthyRecord iHealthyRecord;
    List<WeekDateModel> students=new ArrayList<WeekDateModel>();
    //时间
    Calendar c = Calendar.getInstance();
    //            取得系统日期:
    int years = c.get(Calendar.YEAR);
    int months = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    //取得系统时间：
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    private ProgressDialog progressDialog;
//    private List<MonthDateModel> banjiModelList=new ArrayList<>();



    @Override
    protected void initViews() {
//        EventBus.getDefault().register(this);
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
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        radio_group.setOnCheckedChangeListener(this);
    }

//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }

    @Override
    protected void initDatas() {
        chartUtil=new LineChartUtil(getContext(),chart);
        dates.add(15f);
        dates.add(18f);
        dates.add(6.3f);
        days.add("4/15");
        days.add("4/14");
        days.add("4/13");
        days.add("4/12");
        days.add("4/11");
        days.add("4/10");
        days.add("4/09");
        chartUtil.addDataSet(dates);
        dates.clear();
        days.clear();

    }
    @Subscribe
    public void onEvent(HealthDateModel healthDateModel){
//        banjiModelList=banji.getBanjiModels();
//        classAdapter.updateData(banjiModelList);

    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_loss_weight:

                break;
            case R.id.radio_body_fat:

                break;
            case R.id.radio_fat:

                break;
        }
    }

    @Override
    public void doGetDate(HealthDateModel healthDateModel) {
        List<WeekDateModel> weekDateModels=healthDateModel.getWeekDate();
        for (WeekDateModel wd:weekDateModels){
//            for ()
//            if (healthDateModel.getWeekDate())
//            String month=st.getStartDate().substring(5,7);
//            Student lis=new Student(st.getPhoto(),st.getUserName(),st.getMobile(),tomonth(month),st.getWeekth(),st.getAMStatus());
//            studentList.add(lis);
//            queryAdapter.updateData(studentList);
        }

    }
}
