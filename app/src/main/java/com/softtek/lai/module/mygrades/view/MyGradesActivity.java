package com.softtek.lai.module.mygrades.view;

//莱运动-我的成绩页面
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    @InjectView(R.id.chart)
    LineChart chart;
    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;

    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;


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
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        //chart.getLegend().setEnabled(false);//去除图例

//        LineChartUtil chartUtil;
//        List<Float> dates=new ArrayList<Float>();
//        List<String>days=new ArrayList<String>();
//        int n=7;
//        for (int i=0;i<=n-1;i++) {
//            dates.add(Float.parseFloat(i+""));
//        }
//
//        chartUtil=new LineChartUtil(MyGradesActivity.this,chart);
//        chartUtil.addData(dates,n,days);
    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        //我的成绩
        iGradesPresenter.getStepCount();

        gradesService=ZillaApi.NormalRestAdapter.create(GradesService.class);
        getCurrentDateOrder(1);
        getCurrentDateOrder(0);
        getCurrentWeekOrder(1);
        getCurrentWeekOrder(0);
        //勋章详情页
        getStepHonor();
    }

    //我的成绩
    @Subscribe
    public void onEvent(GradesEvent gradesEvent) {
        System.out.print("--------------------------gradesEvent.getgradesModels().size()"+gradesEvent.getgradesModels().size());
        System.out.println("-----------------------------------------总步数" + gradesEvent.getgradesModels().get(0).getTotalCnt()+"用户id"+gradesEvent.getgradesModels().get(0).getAccountId());
        List<GradesModel>gradesModels=gradesEvent.getgradesModels();
        for (GradesModel gl:gradesModels){
            System.out.println("日期"+gl.getDate()+",排名:"+gl.getOrderby()+"用户id"+gl.getAccountId()+"步数"+gl.getTotalCnt());
        }


        if (gradesEvent.getgradesModels().size()==0){
            tv_totalnumber.setText("0");
            tv_totalmileage.setText("0");
        }

        //总步数
        String totalnumber=gradesEvent.getgradesModels().get(0).getTotalCnt();
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
                        if (RGIdType==0){
                            tv_Nationaldayrank.setText(listResponseData.getData().getOrderInfo()+"");
                            tv_Nationaldaypeople.setText(listResponseData.getData().getOrderData().size()+"");
                        }else if (RGIdType==1){
                            tv_Rundayrank.setText(listResponseData.getData().getOrderInfo()+"");
                            tv_Rundaypeople.setText(listResponseData.getData().getOrderData().size()+"");
                        }
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
                        if (RGIdType==0){
                            tv_Nationalweekrank.setText(listResponseData.getData().getOrderInfo()+"");
                            tv_Nationalweekpeople.setText(listResponseData.getData().getOrderData().size()+"");
                        }else if (RGIdType==1){
                            tv_Runweekrank.setText(listResponseData.getData().getOrderInfo()+"");
                            tv_Runweekpeople.setText(listResponseData.getData().getOrderData().size()+"");
                        }
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
//                        3天勋章
//                        7天勋章
//                        20天勋章
//                        30天勋章
//                        100天勋章
//                        200天勋章
//                        365天勋章
                        //Totals//总步数，步数勋章用该字段自动判断


                        int ThreeDays=Integer.parseInt(listResponseData.getData().getThreeDays());
                        int SevenDays=Integer.parseInt(listResponseData.getData().getSevenDays());
                        int twentyOneDays=Integer.parseInt(listResponseData.getData().getTwentyOneDays());
                        int thirtyDays=Integer.parseInt(listResponseData.getData().getThirtyDays());
                        int OneHundredDays=Integer.parseInt(listResponseData.getData().getOneHundredDays());
                        int TwoHundredyDays=Integer.parseInt(listResponseData.getData().getTwoHundredyDays());
                        int OneYearDays=Integer.parseInt(listResponseData.getData().getOneYearDays());


                        //累积步数10万、50万、100万、200万、500万……勋章 500万之后, 为1000万,2000万,3000万 按每多1000万步,增加一个新的勋章
                        int Totals=Integer.parseInt(listResponseData.getData().getTotals());
                        int honornum=0;

//                        int Totals=40000000;

                        if (Totals<100000){
                             honornum=0;
                        }else if (Totals>=100000&&Totals<500000)
                        {
                                honornum=1;
                        }else if (Totals>=500000&&Totals<1000000)
                        {
                                honornum=2;
                        }
                        else if (Totals>=1000000&&Totals<2000000)
                        {
                                honornum=3;
                        }
                        else if (Totals>=100000&&Totals<5000000)
                        {
                                honornum=4;
                        }
                        else if (Totals>=5000000&&Totals<10000000)
                        {
                            honornum=5;
                        }else if (Totals>=10000000){
                            int i=(Totals-10000000)/10000000;
                            honornum=6+i;
                        }



                        Log.i("-------------------Totals累计步数勋章数量："+honornum);




                        //勋章数量
                        int num=ThreeDays+SevenDays+twentyOneDays+thirtyDays+OneHundredDays+TwoHundredyDays+OneYearDays+honornum;
                        String medalnumber=String.valueOf(num);
                        tv_medalnumber.setText(medalnumber);
                        if (num==0){
                            ll_honor.setVisibility(View.GONE);
                        }else if(num==1){
                            ll_honor2.setVisibility(View.GONE);
                            ll_honor3.setVisibility(View.GONE);
                        }else if(num==2){
                            ll_honor3.setVisibility(View.GONE);
                        }else if(num==3){

                        }
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
