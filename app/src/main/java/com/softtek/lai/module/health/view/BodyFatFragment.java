package com.softtek.lai.module.health.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.snowdream.android.util.Log;
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
public class BodyFatFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,HealthyRecordImpl.HealthyRecordCallback,View.OnClickListener{

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
    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;


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
    char type='6';
    int n=7;
    boolean state=true;
    int flag=0;
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
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
    }

//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }

    @Override
    protected void initDatas() {
        chartUtil=new LineChartUtil(getContext(),chart);


        String nowdate7=getPeriodDate(type,0)+"";
        String nowdate6=getPeriodDate(type,1)+"";
        String nowdate5=getPeriodDate(type,2)+"";
        String nowdate4=getPeriodDate(type,3)+"";
        String nowdate3=getPeriodDate(type,4)+"";
        String nowdate2=getPeriodDate(type,5)+"";
        String nowdate1=getPeriodDate(type,6)+"";
        days.add(formdate(nowdate1));
        days.add(formdate(nowdate2));
        days.add(formdate(nowdate3));
        days.add(formdate(nowdate4));
        days.add(formdate(nowdate5));
        days.add(formdate(nowdate6));
        days.add(formdate(nowdate7));
        dates.add(15f);
        dates.add(18f);
        dates.add(6.3f);
        chartUtil.addData(dates,7,days);
        days.clear();
        dates.clear();
        Log.i("");


    }
    public String formdate(String nowdate)
    {
        String date;
        String sr=nowdate.substring(4,5);
        if (nowdate.substring(4,5).equals("0"))
        {
             date=nowdate.substring(5,6)+"/"+nowdate.substring(6,8);
        }
        else {
            date=nowdate.substring(4,6)+"/"+nowdate.substring(6,8);

    }
        return date;

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
            int n=0,num=7;
            if (n>=7)
            {
                break;
            }
            else {
                n++;
                for (int i=0;i<healthDateModel.getWeekDate().size();i++) {

                    if (healthDateModel.getWeekDate().get(i).getCreateDate()==years+"-"+months+"-"+days)
                        dates.add(Float.parseFloat(healthDateModel.getWeekDate().get(i).getPysical()));
                    else {
                        dates.set(num--,0f);
//                        dates.add(0f);
                    }
//                    if (healthDateModel.getWeekDate().get(i).getCreateDate()==years+""+months+""+(Integer.parseInt(days)-1))
//                        String month = st.getStartDate().substring(5, 7);
//                    Student lis = new Student(st.getPhoto(), st.getUserName(), st.getMobile(), tomonth(month), st.getWeekth(), st.getAMStatus());
//                    studentList.add(lis);
//                    queryAdapter.updateData(studentList);
                }
            }

        }

    }
    /**
     * 获取阶段日期
     * @param  dateType
     * @author Yangtse
     */
    //使用方法 char datetype = '7';
    public static StringBuilder getPeriodDate(char dateType,int n) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int hour; // 需要更改的小时
        int day; // 需要更改的天数
        switch (dateType) {
            case '0': // 1小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 1;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '1': // 2小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 2;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '2': // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '3': // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '4': // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                // System.out.println(df.format(c.getTime()));
                break;
            case '5': // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
            case '6': // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - n;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
            case '7': // 一个月前
                day = c.get(Calendar.DAY_OF_MONTH) - 30*n;
                c.set(Calendar.DAY_OF_MONTH, day);
                // System.out.println(df.format(c.getTime()));
                break;
        }
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        System.out.println("strDate------->"+strForwardDate+"-"+c.getTimeInMillis());
        return strForwardDate;
        //return c.getTimeInMillis();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.bt_left: {

                if (state!=true)
                {
                    n=n+7;
                }
                state=true;
                days.clear();
                String nowdate7 = getPeriodDate(type, n) + "";
                String nowdate6 = getPeriodDate(type, n + 1) + "";
                String nowdate5 = getPeriodDate(type, n + 2) + "";
                String nowdate4 = getPeriodDate(type, n + 3) + "";
                String nowdate3 = getPeriodDate(type, n + 4) + "";
                String nowdate2 = getPeriodDate(type, n + 5) + "";
                String nowdate1 = getPeriodDate(type, n + 6) + "";
                days.add(formdate(nowdate1));
                days.add(formdate(nowdate2));
                days.add(formdate(nowdate3));
                days.add(formdate(nowdate4));
                days.add(formdate(nowdate5));
                days.add(formdate(nowdate6));
                days.add(formdate(nowdate7));
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                chartUtil.addData(dates, 7, days);
                dates.clear();
                days.clear();
                n = n + 7;
                bt_right.setVisibility(View.VISIBLE);

            }
                break;
            case R.id.bt_right:
                days.clear();

                if (state!=false) {
                    n = n - 14;
                }
                else {
                    n=n-7;
                }
                String nowdate7 = getPeriodDate(type, n) + "";
                String nowdate6 = getPeriodDate(type, n + 1) + "";
                String nowdate5 = getPeriodDate(type, n + 2) + "";
                String nowdate4 = getPeriodDate(type, n + 3) + "";
                String nowdate3 = getPeriodDate(type, n + 4) + "";
                String nowdate2 = getPeriodDate(type, n + 5) + "";
                String nowdate1 = getPeriodDate(type, n + 6) + "";
                days.add(formdate(nowdate1));
                days.add(formdate(nowdate2));
                days.add(formdate(nowdate3));
                days.add(formdate(nowdate4));
                days.add(formdate(nowdate5));
                days.add(formdate(nowdate6));
                days.add(formdate(nowdate7));
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                chartUtil.addData(dates, 7, days);
                dates.clear();
                days.clear();
                state=false;
                if (nowdate7.equals(getPeriodDate(type,0)+""))
                    bt_right.setVisibility(View.GONE);
//                n = n - 7;
//                sta=false;
                break;
            case R.id.week:
                flag=0;

                break;
            case R.id.month:
                flag=1;
                String monthdate4=getPeriodDate(type,0)+"";
                String monthdate3=getPeriodDate(type,7)+"";
                String monthdate2=getPeriodDate(type,14)+"";
                String monthdate1=getPeriodDate(type,21)+"";
                days.add(formdate(monthdate1));
                days.add(formdate(monthdate2));
                days.add(formdate(monthdate3));
                days.add(formdate(monthdate4));
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                chartUtil.addData(dates,4,days);
                days.clear();
                dates.clear();
                break;
            case R.id.quarter:
                flag=1;
                String quarterdate4=getPeriodDate(type,0)+"";
                String quarterdate3=getPeriodDate(type,21)+"";
                String quarterdate2=getPeriodDate(type,21*2)+"";
                String quarterdate1=getPeriodDate(type,21*3)+"";
                days.add(formdate(quarterdate1));
                days.add(formdate(quarterdate2));
                days.add(formdate(quarterdate3));
                days.add(formdate(quarterdate4));
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                chartUtil.addData(dates,4,days);
                days.clear();
                dates.clear();
                break;
            case R.id.year:
                type='7';
                String yeardate4=getPeriodDate(type,0)+"";
                String yeardate3=getPeriodDate(type,1)+"";
                String yeardate2=getPeriodDate(type,2)+"";
                String yeardate1=getPeriodDate(type,3)+"";
                days.add(formyeardate(yeardate1));
                days.add(formyeardate(yeardate2));
                days.add(formyeardate(yeardate3));
                days.add(formyeardate(yeardate4));
                dates.add(15f);
                dates.add(18f);
                dates.add(6.3f);
                chartUtil.addData(dates,4,days);
                days.clear();
                dates.clear();
                break;
        }
    }
    public String formyeardate(String nowdate)
    {
        String date;
        String sr=nowdate.substring(0,4);
//        if (nowdate.substring(4,5).equals("0"))
//        {
//            date=nowdate.substring(5,6)+"/"+nowdate.substring(6,8);
//        }
//        else {
//            date=nowdate.substring(4,6)+"/"+nowdate.substring(6,8);
//
//        }
        date=sr+"/"+nowdate.substring(4,6);
        return date;

    }

}
