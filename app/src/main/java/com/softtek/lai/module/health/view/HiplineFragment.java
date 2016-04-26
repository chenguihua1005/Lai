package com.softtek.lai.module.health.view;

import android.app.ProgressDialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.health.model.HealthCircrumModel;
import com.softtek.lai.module.health.model.HealthFatModel;
import com.softtek.lai.module.health.model.HealthHiplieModel;
import com.softtek.lai.module.health.model.HealthUpArmGirthModel;
import com.softtek.lai.module.health.model.HealthWaistlineModel;
import com.softtek.lai.module.health.model.HealthWeightModel;
import com.softtek.lai.module.health.model.HealthdoLegGirthModel;
import com.softtek.lai.module.health.model.HealthupLegGirthModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.presenter.HealthRecordManager;
import com.softtek.lai.module.health.presenter.HealthyRecordImpl;
import com.softtek.lai.module.health.presenter.IHealthyRecord;
import com.softtek.lai.module.studetail.util.LineChartUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by John on 2016/4/12.
 */
@InjectLayout(R.layout.fragment_weight)
public class HiplineFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,HealthRecordManager.HealthRecordCallBack{

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
    List<Float> ceshi=new ArrayList<Float>();
    List<String>days=new ArrayList<String>();
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
    IHealthyRecord iHealthyRecord;
    private ProgressDialog progressDialog;
    //    PysicalManager pysicalManager;
    SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String    date    =    sDateFormat.format(new    java.util.Date());
    String[] datetime=date.split(" ");
    HealthRecordManager healthRecordManager;
    @Override
    protected void initViews() {
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

    @Override
    protected void initDatas() {
        healthRecordManager=new HealthRecordManager(this);
        chartUtil=new LineChartUtil(getContext(),chart);
        dates.clear();
//        pysicalManager=new PysicalManager(getContext());
        Log.i(""+date+datetime[0]+datetime[1]);
        iHealthyRecord=new HealthyRecordImpl();
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
        healthRecordManager.doGetHealthhiplieRecords(date,getDateform(nowdate1)+" "+datetime[1],1);
//        iHealthyRecord.doGetHealthhiplieRecords(date,getDateform(nowdate1)+" "+datetime[1],1);

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
    public String getDateform(String nowdate)
    {
        String date;
        String sr=nowdate.substring(4,5);
        date=nowdate.substring(0,4)+"-"+nowdate.substring(4,6)+"-"+nowdate.substring(6,8);
        return date;

    }
    @Subscribe
    public void getFatList(HealthHiplieModel healthHiplieModel) {
        System.out.println("健康记录体重" + healthHiplieModel.getFirstrecordtime());
        int n=healthHiplieModel.getHiplielist().size();
        for (int i=healthHiplieModel.getHiplielist().size()-1;i>-1;i--) {
            dates.add(Float.parseFloat(healthHiplieModel.getHiplielist().get(i).getHiplie()));
        }
        ceshi.add(dates.get(0));
        ceshi.add(dates.get(1));
        ceshi.add(dates.get(2));
        ceshi.add(dates.get(3));

        chartUtil.addData(dates,n,days);
    }

    @Override
    public void getHealthPysicalRecords(PysicalModel pysicalModel) {

    }

    @Override
    public void getHealthWeightRecords(HealthWeightModel healthWeightModel) {

    }

    @Override
    public void getHealthfatRecords(HealthFatModel healthFatModel) {

    }

    @Override
    public void getHealthcircumRecords(HealthCircrumModel healthCircrumModel) {

    }

    @Override
    public void getHealthwaistlineRecords(HealthWaistlineModel healthWaistlineModel) {

    }

    @Override
    public void getHealthhiplieRecords(HealthHiplieModel healthHiplieModel) {
        System.out.println("健康记录臀围" + healthHiplieModel.getFirstrecordtime());
        int n=healthHiplieModel.getHiplielist().size();
        for (int i=healthHiplieModel.getHiplielist().size()-1;i>-1;i--) {
            dates.add(Float.parseFloat(healthHiplieModel.getHiplielist().get(i).getHiplie()));
        }
        ceshi.add(dates.get(0));
        ceshi.add(dates.get(1));
        ceshi.add(dates.get(2));
        ceshi.add(dates.get(3));

        chartUtil.addData(dates,n,days);
    }

    @Override
    public void getHealthupArmGirthRecords(HealthUpArmGirthModel healthUpArmGirthModel) {

    }

    @Override
    public void getGetHealthupLegGirthRecords(HealthupLegGirthModel healthupLegGirthModel) {

    }

    @Override
    public void getHealthdoLegGirthRecords(HealthdoLegGirthModel healthdoLegGirthModel) {

    }
}
