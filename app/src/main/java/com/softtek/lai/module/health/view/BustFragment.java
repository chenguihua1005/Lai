package com.softtek.lai.module.health.view;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * @author lareina.qiao
 * Created by John on 2016/4/12.
 */
@InjectLayout(R.layout.fragment_weight)
public class BustFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,HealthRecordManager.HealthRecordCallBack,View.OnClickListener{

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
    DateForm dateForm;
    private LineChartUtil chartUtil;
    List<Float> dates=new ArrayList<Float>();
    List<String>days=new ArrayList<String>();

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
        chart.getLegend().setEnabled(false);//去除图例
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);//不启用0轴的线

        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        YAxis rightAxis=chart.getAxisRight();
        rightAxis.setDrawZeroLine(true);
        chart.setData(new LineData());//设置一个空数据

        radio_group.setOnCheckedChangeListener(this);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
        quarter.setOnClickListener(this);
        year.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        dateForm=new DateForm();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        healthRecordManager=new HealthRecordManager(this);
        chartUtil=new LineChartUtil(getContext(),chart);
        dates.clear();
//        Log.i(""+date+datetime[0]+datetime[1]);
        iHealthyRecord=new HealthyRecordImpl();
        String nowdate7=dateForm.getPeriodDate(type,0)+"";
        String nowdate6=dateForm.getPeriodDate(type,1)+"";
        String nowdate5=dateForm.getPeriodDate(type,2)+"";
        String nowdate4=dateForm.getPeriodDate(type,3)+"";
        String nowdate3=dateForm.getPeriodDate(type,4)+"";
        String nowdate2=dateForm.getPeriodDate(type,5)+"";
        String nowdate1=dateForm.getPeriodDate(type,6)+"";
        days.add(dateForm.formdate(nowdate1));
        days.add(dateForm.formdate(nowdate2));
        days.add(dateForm.formdate(nowdate3));
        days.add(dateForm.formdate(nowdate4));
        days.add(dateForm.formdate(nowdate5));
        days.add(dateForm.formdate(nowdate6));
        days.add(dateForm.formdate(nowdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1)+" "+datetime[1],date,1);
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
        try {
            if (progressDialog!=null)
                progressDialog.dismiss();
            if(healthCircrumModel==null){
                return;
            }
            System.out.println("健康记录胸围" + healthCircrumModel.getFirstrecordtime());
            int n=healthCircrumModel.getCircumlist().size();
            for (int i=0;i<=n-1;i++) {
                dates.add(Float.parseFloat(healthCircrumModel.getCircumlist().get(i).getCircum()));
            }

            chartUtil.addData(dates,n,days);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getHealthwaistlineRecords(HealthWaistlineModel healthWaistlineModel) {

    }

    @Override
    public void getHealthhiplieRecords(HealthHiplieModel healthHiplieModel) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.bt_left: {
                switch (flag)
                {
                    case 0:
                        if (state!=true)
                        {
                            n=n+7;
                        }
                        state=true;
                        days.clear();
                        dates.clear();
                        String nowdate7 = dateForm.getPeriodDate(type, n) + "";
                        String nowdate6 = dateForm.getPeriodDate(type, n + 1) + "";
                        String nowdate5 = dateForm.getPeriodDate(type, n + 2) + "";
                        String nowdate4 = dateForm.getPeriodDate(type, n + 3) + "";
                        String nowdate3 = dateForm.getPeriodDate(type, n + 4) + "";
                        String nowdate2 = dateForm.getPeriodDate(type, n + 5) + "";
                        String nowdate1 = dateForm.getPeriodDate(type, n + 6) + "";
                        days.add(dateForm.formdate(nowdate1));
                        days.add(dateForm.formdate(nowdate2));
                        days.add(dateForm.formdate(nowdate3));
                        days.add(dateForm.formdate(nowdate4));
                        days.add(dateForm.formdate(nowdate5));
                        days.add(dateForm.formdate(nowdate6));
                        days.add(dateForm.formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1),dateForm.getDateform(nowdate7),1);
                        n = n + 7;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        if (state!=true)
                        {
                            n=n+4;
                        }
                        state=true;;
                        dates.clear();
                        days.clear();
                        type='6';
                        String monthdate4=dateForm.getPeriodDate(type,7*n)+"";
                        String monthdate3=dateForm.getPeriodDate(type,7*(n+1))+"";
                        String monthdate2=dateForm.getPeriodDate(type,7*(n+2))+"";
                        String monthdate1=dateForm.getPeriodDate(type,7*(n+3))+"";
                        days.add(dateForm.formdate(monthdate1));
                        days.add(dateForm.formdate(monthdate2));
                        days.add(dateForm.formdate(monthdate3));
                        days.add(dateForm.formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1),dateForm.getDateform(monthdate4),2);
                        n=n+4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        if (state!=true)
                        {
                            n=n+4;
                        }
                        state=true;
                        dates.clear();
                        days.clear();
                        type='6';
                        String quarterdate4=dateForm.getPeriodDate(type,21*n)+"";
                        String quarterdate3=dateForm.getPeriodDate(type,21*(n+1))+"";
                        String quarterdate2=dateForm.getPeriodDate(type,21*(n+2))+"";
                        String quarterdate1=dateForm.getPeriodDate(type,21*(n+3))+"";
                        days.add(dateForm.formdate(quarterdate1));
                        days.add(dateForm.formdate(quarterdate2));
                        days.add(dateForm.formdate(quarterdate3));
                        days.add(dateForm.formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1),dateForm.getDateform(quarterdate4),3);
                        n=n+4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        if (state!=true)
                        {
                            n=n+4;
                        }
                        dates.clear();
                        days.clear();
                        state=true;
                        type='7';
                        String yeardate4=dateForm.getPeriodDate(type,2*n)+"";
                        String yeardate3=dateForm.getPeriodDate(type,2*(n+1))+"";
                        String yeardate2=dateForm.getPeriodDate(type,2*(n+2))+"";
                        String yeardate1=dateForm.getPeriodDate(type,2*(n+3))+"";
                        String yeardate0=dateForm.getPeriodDate(type,2*(n+4))+"";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4)+"     /");
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0),dateForm.getDateform(yeardate4),4);
                        n=n+4;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
                }



            }
            break;
            case R.id.bt_right:
                switch (flag)
                {
                    case 0:
                        if (state!=false) {
                            n = n - 14;
                        }
                        else {
                            n=n-7;
                        }
                        dates.clear();
                        days.clear();
                        String nowdate7 = dateForm.getPeriodDate(type, n) + "";
                        String nowdate6 = dateForm.getPeriodDate(type, n + 1) + "";
                        String nowdate5 = dateForm.getPeriodDate(type, n + 2) + "";
                        String nowdate4 = dateForm.getPeriodDate(type, n + 3) + "";
                        String nowdate3 = dateForm.getPeriodDate(type, n + 4) + "";
                        String nowdate2 = dateForm.getPeriodDate(type, n + 5) + "";
                        String nowdate1 = dateForm.getPeriodDate(type, n + 6) + "";
                        days.add(dateForm.formdate(nowdate1));
                        days.add(dateForm.formdate(nowdate2));
                        days.add(dateForm.formdate(nowdate3));
                        days.add(dateForm.formdate(nowdate4));
                        days.add(dateForm.formdate(nowdate5));
                        days.add(dateForm.formdate(nowdate6));
                        days.add(dateForm.formdate(nowdate7));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(nowdate1),dateForm.getDateform(nowdate7),1);
                        state=false;
                        if (nowdate7.equals(dateForm.getPeriodDate(type,0)+""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 1:
                        if (state!=false) {
                            n = n - 8;
                        }
                        else {
                            n=n-4;

                        }
                        dates.clear();
                        days.clear();
                        type='6';
                        String monthdate4=dateForm.getPeriodDate(type,7*n)+"";
                        String monthdate3=dateForm.getPeriodDate(type,7*(n+1))+"";
                        String monthdate2=dateForm.getPeriodDate(type,7*(n+2))+"";
                        String monthdate1=dateForm.getPeriodDate(type,7*(n+3))+"";
                        days.add(dateForm.formdate(monthdate1));
                        days.add(dateForm.formdate(monthdate2));
                        days.add(dateForm.formdate(monthdate3));
                        days.add(dateForm.formdate(monthdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1),dateForm.getDateform(monthdate4),2);
                        state=false;
                        if (monthdate4.equals(dateForm.getPeriodDate(type,0)+""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 2:
                        if (state!=false) {
                            n = n - 8;
                        }
                        else {
                            n=n-4;

                        }
                        dates.clear();
                        days.clear();
                        type='6';
                        String quarterdate4=dateForm.getPeriodDate(type,21*n)+"";
                        String quarterdate3=dateForm.getPeriodDate(type,21*(n+1))+"";
                        String quarterdate2=dateForm.getPeriodDate(type,21*(n+2))+"";
                        String quarterdate1=dateForm.getPeriodDate(type,21*(n+3))+"";
                        days.add(dateForm.formdate(quarterdate1));
                        days.add(dateForm.formdate(quarterdate2));
                        days.add(dateForm.formdate(quarterdate3));
                        days.add(dateForm.formdate(quarterdate4));
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1),dateForm.getDateform(quarterdate4),3);
                        bt_right.setVisibility(View.VISIBLE);
                        state=false;
                        if (quarterdate4.equals(dateForm.getPeriodDate(type,0)+""))
                            bt_right.setVisibility(View.GONE);
                        break;
                    case 3:
                        if (state!=false) {
                            n = n - 8;
                        }
                        else {
                            n=n-4;

                        }
                        dates.clear();
                        days.clear();
                        type='7';
                        String yeardate4=dateForm.getPeriodDate(type,2*n)+"";
                        String yeardate3=dateForm.getPeriodDate(type,2*(n+1))+"";
                        String yeardate2=dateForm.getPeriodDate(type,2*(n+2))+"";
                        String yeardate1=dateForm.getPeriodDate(type,2*(n+3))+"";
                        String yeardate0=dateForm.getPeriodDate(type,2*(n+4))+"";
                        days.add(formyeardate(yeardate1));
                        days.add(formyeardate(yeardate2));
                        days.add(formyeardate(yeardate3));
                        days.add(formyeardate(yeardate4)+"     /");
                        progressDialog.show();
                        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0),dateForm.getDateform(yeardate4),4);
                        bt_right.setVisibility(View.VISIBLE);
                        state=false;
                        if (yeardate4.equals(dateForm.getPeriodDate(type,0)+""))
                            bt_right.setVisibility(View.GONE);
                        break;
                }


                break;
            case R.id.week:
                flag=0;
                type='6';
                n=7;
                state=true;
                bt_right.setVisibility(View.GONE);
                dates.clear();
                days.clear();
                String weekdate7=dateForm.getPeriodDate(type,0)+"";
                String weekdate6=dateForm.getPeriodDate(type,1)+"";
                String weekdate5=dateForm.getPeriodDate(type,2)+"";
                String weekdate4=dateForm.getPeriodDate(type,3)+"";
                String weekdate3=dateForm.getPeriodDate(type,4)+"";
                String weekdate2=dateForm.getPeriodDate(type,5)+"";
                String weekdate1=dateForm.getPeriodDate(type,6)+"";
                days.add(dateForm.formdate(weekdate1));
                days.add(dateForm.formdate(weekdate2));
                days.add(dateForm.formdate(weekdate3));
                days.add(dateForm.formdate(weekdate4));
                days.add(dateForm.formdate(weekdate5));
                days.add(dateForm.formdate(weekdate6));
                days.add(dateForm.formdate(weekdate7));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(weekdate1),dateForm.getDateform(weekdate7),1);

                break;
            case R.id.month:
                dates.clear();
                days.clear();
                flag=1;
                state=true;
                bt_right.setVisibility(View.GONE);
                type='6';
                n=4;
                String monthdate4=dateForm.getPeriodDate(type,0)+"";
                String monthdate3=dateForm.getPeriodDate(type,7)+"";
                String monthdate2=dateForm.getPeriodDate(type,14)+"";
                String monthdate1=dateForm.getPeriodDate(type,21)+"";
                days.add(dateForm.formdate(monthdate1));
                days.add(dateForm.formdate(monthdate2));
                days.add(dateForm.formdate(monthdate3));
                days.add(dateForm.formdate(monthdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(monthdate1),dateForm.getDateform(monthdate4),2);
                break;
            case R.id.quarter:
                dates.clear();
                days.clear();
                flag=2;
                state=true;
                bt_right.setVisibility(View.GONE);
                type='6';
                n=4;
                String quarterdate4=dateForm.getPeriodDate(type,0)+"";
                String quarterdate3=dateForm.getPeriodDate(type,21)+"";
                String quarterdate2=dateForm.getPeriodDate(type,21*2)+"";
                String quarterdate1=dateForm.getPeriodDate(type,21*3)+"";
                days.add(dateForm.formdate(quarterdate1));
                days.add(dateForm.formdate(quarterdate2));
                days.add(dateForm.formdate(quarterdate3));
                days.add(dateForm.formdate(quarterdate4));
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(quarterdate1),dateForm.getDateform(quarterdate4),3);
                break;
            case R.id.year:
                flag=3;
                dates.clear();
                days.clear();
                state=true;
                bt_right.setVisibility(View.GONE);
                type='7';
                n=4;
                String yeardate4=dateForm.getPeriodDate(type,0)+"";
                String yeardate3=dateForm.getPeriodDate(type,2)+"";
                String yeardate2=dateForm.getPeriodDate(type,4)+"";
                String yeardate1=dateForm.getPeriodDate(type,6)+"";
                String yeardate0=dateForm.getPeriodDate(type,8)+"";
                days.add(formyeardate(yeardate1));
                days.add(formyeardate(yeardate2));
                days.add(formyeardate(yeardate3));
                days.add(formyeardate(yeardate4)+"     /");
                progressDialog.show();
                healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(yeardate0),dateForm.getDateform(yeardate4),4);
                break;
        }
    }
    public String formyeardate(String nowdate)
    {
        String date;
        String sr=nowdate.substring(0,4);
        date=sr+"/"+nowdate.substring(4,6);
        return date;

    }
    public void updateBustStatus(){

        week.setChecked(true);
        flag=0;
        type='6';
        n=7;
        state=true;
        bt_right.setVisibility(View.GONE);
        bt_right.setVisibility(View.GONE);
        dates.clear();
        days.clear();
        String weekdate7=dateForm.getPeriodDate(type,0)+"";
        String weekdate6=dateForm.getPeriodDate(type,1)+"";
        String weekdate5=dateForm.getPeriodDate(type,2)+"";
        String weekdate4=dateForm.getPeriodDate(type,3)+"";
        String weekdate3=dateForm.getPeriodDate(type,4)+"";
        String weekdate2=dateForm.getPeriodDate(type,5)+"";
        String weekdate1=dateForm.getPeriodDate(type,6)+"";
        days.add(dateForm.formdate(weekdate1));
        days.add(dateForm.formdate(weekdate2));
        days.add(dateForm.formdate(weekdate3));
        days.add(dateForm.formdate(weekdate4));
        days.add(dateForm.formdate(weekdate5));
        days.add(dateForm.formdate(weekdate6));
        days.add(dateForm.formdate(weekdate7));
        progressDialog.show();
        healthRecordManager.doGetHealthcircumRecords(dateForm.getDateform(weekdate1),dateForm.getDateform(weekdate7),1);
    }
}
