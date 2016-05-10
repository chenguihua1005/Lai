package com.softtek.lai.module.mygrades.view;

//莱运动-我的成绩页面
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.eventModel.DayRankEvent;
import com.softtek.lai.module.mygrades.eventModel.GradesEvent;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.model.HonorModel;
import com.softtek.lai.module.mygrades.model.orderDataModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;
import com.softtek.lai.module.studetail.util.LineChartUtil;
import com.softtek.lai.utils.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_my_grades)
public class MyGradesActivity extends BaseActivity implements View.OnClickListener{

    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //更新时间
    @InjectView(R.id.tv_update)
    TextView tv_update;
    //总步数
    @InjectView(R.id.tv_totalnumber)
    TextView tv_totalnumber;
    //总公里数
    @InjectView(R.id.tv_totalmileage)
    TextView tv_totalmileage;
    //跑团日排名
    @InjectView(R.id.tv_Rundayrank)
    TextView tv_Rundayrank;
    //跑团日排名人数
    @InjectView(R.id.tv_Rundaypeople)
    TextView tv_Rundaypeople;
    //全国日排名
    @InjectView(R.id.tv_Nationaldayrank)
    TextView tv_Nationaldayrank;
    //全国日排名人数
    @InjectView(R.id.tv_Nationaldaypeople)
    TextView tv_Nationaldaypeople;
    //跑团周排名
    @InjectView(R.id.tv_Runweekrank)
    TextView tv_Runweekrank;
    //跑团周排名人数
    @InjectView(R.id.tv_Runweekpeople)
    TextView tv_Runweekpeople;
    //全国周排名
    @InjectView(R.id.tv_Nationalweekrank)
    TextView tv_Nationalweekrank;
    //全国周排名人数
    @InjectView(R.id.tv_Nationalweekpeople)
    TextView tv_Nationalweekpeople;
    //勋章数量
    @InjectView(R.id.tv_medalnumber)
    TextView tv_medalnumber;

    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    @InjectView(R.id.ll_honor1)
    LinearLayout ll_honor1;
    @InjectView(R.id.ll_honor2)
    LinearLayout ll_honor2;
    @InjectView(R.id.ll_honor3)
    LinearLayout ll_honor3;

    @InjectView(R.id.img_honor1)
    ImageView img_honor1;
    @InjectView(R.id.img_honor2)
    ImageView img_honor2;
    @InjectView(R.id.img_honor3)
    ImageView img_honor3;

    @InjectView(R.id.tv_str1)
    TextView tv_str1;
    @InjectView(R.id.tv_str2)
    TextView tv_str2;
    @InjectView(R.id.tv_str3)
    TextView tv_str3;



    @InjectView(R.id.chart)
    LineChart chart;
    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;
    //获取当前日期
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH)+1;
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    int mHour=c.get(Calendar.HOUR);
    int mMinute=c.get(Calendar.MINUTE);
    int mSecond=c.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        title.setText("我的成绩");
        tv_right.setText("分享");
        ll_left.setOnClickListener(this);
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
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines 重置所有限行，以避免重叠线,删除所有限制线
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);//使网格虚线
        leftAxis.setDrawZeroLine(true);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        chart.getLegend().setEnabled(false);//去除图例
    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        //我的成绩
        //SqlDateTime overflow. Must be between 1/1/1753 12:00:00 AM and 12/31/9999 11:59:59 PM.

        String a = "2006-08-14";
        String b = "2016-08-14";
        DateTime d1 = DateTime.parse(a);
        DateTime d2 = DateTime.parse(b);
//        DateFormat df=new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
//        String startdate="2013-12-08";
//        String enddate="2013-12-08";
//        try{
//            Date printDate=df.parse(a);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }

        Log.i("---------------"+d1+"--------"+d2+"-------------");

        try {
            iGradesPresenter.getStepCount(d1,d2);//yyyy-MM-dd HH:mm:ss
        } catch (Exception e) {
            e.printStackTrace();
        }

        gradesService=ZillaApi.NormalRestAdapter.create(GradesService.class);
//        getCurrentDateOrder(1);
//        getCurrentDateOrder(0);
//        getCurrentWeekOrder(1);
//        getCurrentWeekOrder(0);
        //勋章详情页--调用接口
        getStepHonor();
    }


    //我的成绩
    @Subscribe
    public void onEvent(GradesEvent gradesEvent) {
        //System.out.print("------------size()"+gradesEvent.getgradesModels().size());

        List<GradesModel>gradesModels=gradesEvent.getgradesModels();

        LineChartUtil chartUtil;
        List<Float> dates=new ArrayList<Float>();
        List<String>days=new ArrayList<String>();
//        for (GradesModel gl:gradesModels){
//            System.out.println("日期"+gl.getDate()+"步数"+gl.getTotalCnt());
//            dates.add(20000f);
//            dates.add(21000f);
//            //days.add(gl.getDate());
//            days.add(mMonth+"/"+mDay);
//            days.add(5+"/"+10);
//        }

        dates.add(20000f);
        dates.add(21000f);
        dates.add(20000f);
        days.add(5+"/"+7);
        days.add(5+"/"+8);
        days.add(mMonth+"/"+mDay);

        chartUtil=new LineChartUtil(MyGradesActivity.this,chart);
        chartUtil.addData(dates,3,days);
    }

    //我的日排名
    public void getCurrentDateOrder(final int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token,RGIdType,new Callback<ResponseData<DayRankModel>>() {

            @Override
            public void success(ResponseData<DayRankModel> listResponseData, Response response) {
                //Log.i("listResponseData",""+listResponseData);
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        //可空，1：传跑团的排名，则获取跑团内部的排名，0或者不传参数，则返回全国排名
//                        if (RGIdType==0){
//                            tv_Nationaldayrank.setText(listResponseData.getData().getOrderInfo()+"");
//                            tv_Nationaldaypeople.setText(listResponseData.getData().getOrderData().size()+"");
//                        }else if (RGIdType==1){
//                            tv_Rundayrank.setText(listResponseData.getData().getOrderInfo()+"");
//                            tv_Rundaypeople.setText(listResponseData.getData().getOrderData().size()+"");
//                        }
                        //Util.toastMsg("我的日排名--查询成功");
                        break;
                    case 500:
                        Util.toastMsg("我的日排名--查询出bug");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }
    //我的周排名
    public void getCurrentWeekOrder(final int RGIdType) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentWeekOrder(token, RGIdType,new Callback<ResponseData<DayRankModel>>() {

            @Override
            public void success(ResponseData<DayRankModel> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
//                        if (RGIdType==0){
//                            tv_Nationalweekrank.setText(listResponseData.getData().getOrderInfo()+"");
//                            tv_Nationalweekpeople.setText(listResponseData.getData().getOrderData().size()+"");
//                        }else if (RGIdType==1){
//                            tv_Runweekrank.setText(listResponseData.getData().getOrderInfo()+"");
//                            tv_Runweekpeople.setText(listResponseData.getData().getOrderData().size()+"");
//                        }
                        //Util.toastMsg("我的周排名--查询成功");
                        break;
                    case 500:
                        Util.toastMsg("我的周排名--查询出bug");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }

    //2.19.2	勋章详情页
    public void getStepHonor() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getStepHonor(token, new Callback<ResponseData<HonorModel>>() {

            @Override
            public void success(ResponseData<HonorModel> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        //总步数
                        String totalnumber=listResponseData.getData().getTotals();
                        if (totalnumber==""){
                            tv_totalnumber.setText("0");
                        }else {
                            tv_totalnumber.setText(totalnumber);
                        }
                        //总公里数计算公式: 1公里=1428步
                        DecimalFormat format = new DecimalFormat("###.##");
                        String totalmileage =String.valueOf(Float.parseFloat(totalnumber)/1428);
                        String temp = format.format(Double.parseDouble(totalmileage));
                        if (totalmileage==""){
                            tv_totalmileage.setText("0");
                        }else {
                            tv_totalmileage.setText(temp);
                        }

                        //全国排名
                        tv_Nationaldayrank.setText(listResponseData.getData().getContryDayOrder()+"");
                        tv_Nationaldaypeople.setText(listResponseData.getData().getContryDayOrderTotal()+"");
                        //跑团的排名
                        tv_Rundayrank.setText(listResponseData.getData().getDayOrder()+"");
                        tv_Rundaypeople.setText(listResponseData.getData().getDayOrderTotal()+"");

                        tv_Nationalweekrank.setText(listResponseData.getData().getWeekOrder()+"");
                        tv_Nationalweekpeople.setText(listResponseData.getData().getContryDayOrderTotal()+"");
                        tv_Runweekrank.setText(listResponseData.getData().getWeekOrderRG()+"");
                        tv_Runweekpeople.setText(listResponseData.getData().getDayOrderTotal()+"");

                        int num;
                        int Daysnum,Stepsnum,Angelsnum,Pknum;
                        //连续3天、7天、21天、30天、100天、200天、365天步数1万勋章 1表示获得，0表示未获得
                        int ThreeDays=Integer.parseInt(listResponseData.getData().getThreeDays());
                        int SevenDays=Integer.parseInt(listResponseData.getData().getSevenDays());
                        int twentyOneDays=Integer.parseInt(listResponseData.getData().getTwentyOneDays());
                        int thirtyDays=Integer.parseInt(listResponseData.getData().getThirtyDays());
                        int OneHundredDays=Integer.parseInt(listResponseData.getData().getOneHundredDays());
                        int TwoHundredyDays=Integer.parseInt(listResponseData.getData().getTwoHundredyDays());
                        int OneYearDays=Integer.parseInt(listResponseData.getData().getOneYearDays());
                        Daysnum=ThreeDays+SevenDays+twentyOneDays+thirtyDays+OneHundredDays+TwoHundredyDays+OneYearDays;
                        //步数
                        int Totals=Integer.parseInt(listResponseData.getData().getTotals());
                        String bushustr="";
                        if (Totals<100000){
                            Stepsnum=0;
                        }else if (Totals>=100000&&Totals<500000)
                        {
                            Stepsnum=1;
                            bushustr="累计步数10万";
                        }else if (Totals>=500000&&Totals<1000000)
                        {
                            Stepsnum=2;
                            bushustr="累计步数50万";
                        }
                        else if (Totals>=1000000&&Totals<2000000)
                        {
                            Stepsnum=3;
                            bushustr="累计步数100万";
                        }
                        else if (Totals>=100000&&Totals<5000000)
                        {
                            Stepsnum=4;
                            bushustr="累计步数200万";
                        }
                        else if (Totals>=5000000&&Totals<10000000)
                        {
                            Stepsnum=5;
                            bushustr="累计步数500万";
                        }else if (Totals>=10000000){
                            int i=(Totals-10000000)/10000000;
                            Stepsnum=6+i;
                            if (Stepsnum==6){

                            }
                            bushustr="累计步数1000万";
                        }


//                        int honortype=1;
//                        switch (honortype){
//                            case 1:
//                                Util.toastMsg("连续天数勋章");
//                                break;
//                            case 2:
//                                Util.toastMsg("累积步数勋章");
//                                break;
//                            case 3:
//                                Util.toastMsg("天使听见爱勋章");
//                                break;
//                            case 4:
//                                Util.toastMsg("PK勋章");
//                                break;
//                        }




//                        int ThreeDays=0;
//                        int SevenDays=1;
//                        int twentyOneDays=0;
//                        int thirtyDays=1;
//                        int OneHundredDays=1;
//                        int TwoHundredyDays=0;
//                        int OneYearDays=0;


                        //      4)	PK勋章:
//                        a)	pk 成功1次可获得挑战达人铜牌
//                        b)	pk 成功50次可获得挑战达人银牌
//                        c)	pk 成功100次可获得挑战达人金牌
//                        d)	pk 成功200次可获得挑战明星铜牌
//                        e)	pk 成功300次可获得挑战明星银牌
//                        f)	pk 成功500次可获得挑战明星金牌


//                        //步数
//                        int Totals=Integer.parseInt(listResponseData.getData().getTotals());
//                        //步数勋章数量
//                        int honornum=0;
//                        String bushustr="";
//                        //天数勋章数量
//                        //int Daynum=ThreeDays+SevenDays+twentyOneDays+thirtyDays+OneHundredDays+TwoHundredyDays+OneYearDays;
//                        int Daynum=0;
//                        //总勋章数量
//                        int num=4;//Daynum+honornum;

//                        if (Totals<100000){
//                                honornum=0;
//                        }else if (Totals>=100000&&Totals<500000)
//                        {
//                                honornum=1;
//                                bushustr="累计步数10万";
//                        }else if (Totals>=500000&&Totals<1000000)
//                        {
//                                honornum=2;
//                                bushustr="累计步数50万";
//                        }
//                        else if (Totals>=1000000&&Totals<2000000)
//                        {
//                                honornum=3;
//                                bushustr="累计步数100万";
//                        }
//                        else if (Totals>=100000&&Totals<5000000)
//                        {
//                                honornum=4;
//                                bushustr="累计步数200万";
//                        }
//                        else if (Totals>=5000000&&Totals<10000000)
//                        {
//                            honornum=5;
//                            bushustr="累计步数500万";
//                        }else if (Totals>=10000000){
//                            int i=(Totals-10000000)/10000000;
//                            honornum=6+i;
//                           // bushustr="累计步数1000万";
//                        }

//                        String medalnumber=String.valueOf(num);
//                        tv_medalnumber.setText(medalnumber);
//                        if (num==0){
//                            ll_honor.setVisibility(View.GONE);
//                        }else if(num==1){
//                            if (honornum==0){
//                                tv_str1.setText("连续3天步数一万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                            }else {
//                                tv_str1.setText("累计步数10万");
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                            }
//                        }else if(num==2){
//                            if(honornum==0){
//                                tv_str1.setText("连续3天步数一万");
//                                tv_str2.setText("连续7天步数一万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                img_honor2.setImageResource(R.drawable.img_medal1);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                            }else if(honornum==1){
//                                tv_str1.setText("连续3天步数一万");
//                                tv_str2.setText("累计步数10万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                            }else if(honornum==2){
//                                tv_str1.setText("累计步数10万");
//                                tv_str2.setText("累计步数50万");
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                            }
//                        }else if(num==3){
//                            if(honornum==0){
//                                tv_str1.setText("连续3天步数一万");
//                                tv_str2.setText("连续7天步数一万");
//                                tv_str3.setText("连续20天步数一万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                img_honor2.setImageResource(R.drawable.img_medal1);
//                                img_honor3.setImageResource(R.drawable.img_medal1);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }else if(honornum==1){
//                                tv_str1.setText("连续3天步数一万");
//                                tv_str2.setText("累计步数10万");
//                                tv_str3.setText("连续7天步数一万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                img_honor3.setImageResource(R.drawable.img_medal1);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }else if(honornum==2){
//                                tv_str1.setText("连续3天步数一万");
//                                tv_str2.setText("累计步数10万");
//                                tv_str3.setText("累计步数50万");
//                                img_honor1.setImageResource(R.drawable.img_medal1);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                img_honor3.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }else if(honornum==3){
//                                tv_str1.setText("累计步数10万");
//                                tv_str2.setText("累计步数50万");
//                                tv_str3.setText("累计步数100万");
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                img_honor3.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }

//                        }else if(num>3){//勋章数量大于3
//
//                            // 3天勋章,7天勋章,20天勋章,30天勋章,100天勋章,200天勋章,365天勋章
//                            // Totals 总步数，步数勋章用该字段自动判断,累积步数10万、50万、100万、200万、500万……勋章 500万之后, 为1000万,2000万,3000万 按每多1000万步,增加一个新的勋章
//                            //天数勋章，累计步数勋章
//                            String daystr="";
////                            if (ThreeDays==1){daystr="连续3天步数一万";}
////                            if (SevenDays==1){daystr="连续7天步数一万";}
////                            if (twentyOneDays==1){daystr="连续20天步数一万";}
////                            if (thirtyDays==1){daystr="连续30天步数一万";}
////                            if (OneHundredDays==1){daystr="连续100天步数一万";}
////                            if (TwoHundredyDays==1){daystr="连续200天步数一万";}
////                            if (OneYearDays==1){daystr="连续365天步数一万";}
//
//
////                            if(ThreeDays ==1){
////                                daystr="连续3天步数一万";
////                                if (SevenDays==1){
////                                    daystr="连续7天步数一万";
////                                    if (twentyOneDays==1){
////                                        daystr="连续20天步数一万";
////                                        if (thirtyDays==1){
////                                            daystr="连续30天步数一万";
////                                            if ( OneHundredDays==1){
////                                                daystr="连续100天步数一万";
////                                                if (TwoHundredyDays ==1){
////                                                    daystr="连续200天步数一万";
////                                                    if (OneYearDays==1){
////                                                        daystr="连续365天步数一万";
////                                                    }
////                                                }
////                                            }
////                                        }
////                                    }
////                                }
////                            }else{
////
////                            }
////                            System.out.println("---------------------------------"+daystr);
//
//
//
//
//
//                            //显示用户所拥有的最新的3个勋章
//                            //天数勋章Daynum
//                            if (Daynum==0){
//                                tv_str1.setText("累积步数10万");
//                                tv_str2.setText("累计步数50万");
//                                tv_str3.setText("累计步数100万");
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                img_honor3.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }else if (Daynum==1){
//                                tv_str1.setText("连续365天步数一万");
//                                tv_str2.setText("累计步数50万");
//                                tv_str3.setText("累计步数100万");
//                                img_honor1.setImageResource(R.drawable.img_medal);
//                                img_honor2.setImageResource(R.drawable.img_medal);
//                                img_honor3.setImageResource(R.drawable.img_medal);
//                                ll_honor1.setVisibility(View.VISIBLE);
//                                ll_honor2.setVisibility(View.VISIBLE);
//                                ll_honor3.setVisibility(View.VISIBLE);
//                            }else if (Daynum==2) {
//
//                            }else if (Daynum==3) {
//
//                            }else if (Daynum==4) {
//
//                            }else if (Daynum==5) {
//
//                            }else if (Daynum==6) {
//
//                            }else if (Daynum==7) {
//
//                            }
//                        }

                        //Util.toastMsg("我的勋章--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的勋章--查询出bug");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
