package com.softtek.lai.module.mygrades.view;

//莱运动-我的成绩页面

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.eventModel.GradesEvent;
import com.softtek.lai.module.mygrades.model.GradeHonorModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;
import com.softtek.lai.module.studetail.util.LineChartUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @InjectView(R.id.ll_dayRank)
    LinearLayout ll_dayRank;
    @InjectView(R.id.ll_weekRank)
    LinearLayout ll_weekRank;

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
    LineChartUtil chartUtil;
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
    char type='6';
    int n=7;
    boolean state=true;
    List<String>days=new ArrayList<String>();
    List<Float> dates=new ArrayList<Float>();
    String nowdate7,nowdate6,nowdate5,nowdate4,nowdate3,nowdate2,nowdate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        ll_dayRank.setOnClickListener(this);
        ll_weekRank.setOnClickListener(this);

        title.setText("我的成绩");
     //   tv_right.setText("分享");
        chartUtil=new LineChartUtil(this,chart);
        ll_left.setOnClickListener(this);
        //2.折线图单位是步
        //初始化统计图
        chart.setDrawGridBackground(false);//取消统计图整体背景色
        //chart.setBackgroundColor(0xffff9c00);
        //取消描述信息,设置没有数据的时候提示信息
        chart.setDescription("");//单位：步
        chart.setNoDataTextDescription("暂无数据");
        //启用手势操作
        chart.setTouchEnabled(false);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines 重置所有限行，以避免重叠线,删除所有限制线
        leftAxis.setAxisMaxValue(100000f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);//使网格虚线
        leftAxis.setDrawZeroLine(true);//不启用0轴的线
        chart.getAxisRight().setEnabled(false);//取消右边的轴线
        chart.setData(new LineData());//设置一个空数据
        chart.getLegend().setEnabled(false);//去除图例
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
//        1.	初始时间是从用户开始参加跑团算起
//        2.	如果用户退出跑团, 那么就不用记录用户的步数; 所以退出了之前的跑团, 在加入下一个跑团之间, 是没有步数记录的. 不需要显示在折线图中.

        //调用我的成绩接口  1/1/1753 12:00:00 AM and 12/31/9999 11:59:59 PM.
        //date当前日期
        gradesService=ZillaApi.NormalRestAdapter.create(GradesService.class);

        //3.3.2	成绩勋章信息
        getGradeHonor();
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        days.clear();
        nowdate7=getPeriodDate(type,0)+"";
        nowdate6=getPeriodDate(type,1)+"";
        nowdate5=getPeriodDate(type,2)+"";
        nowdate4=getPeriodDate(type,3)+"";
        nowdate3=getPeriodDate(type,4)+"";
        nowdate2=getPeriodDate(type,5)+"";
        nowdate1=getPeriodDate(type,6)+"";
        days.add(formdate(nowdate1));
        days.add(formdate(nowdate2));
        days.add(formdate(nowdate3));
        days.add(formdate(nowdate4));
        days.add(formdate(nowdate5));
        days.add(formdate(nowdate6));
        days.add(formdate(nowdate7));
        iGradesPresenter.getStepCount(getDateform(nowdate1),getDateform(nowdate7));
    }
    public String getDateform(String nowdate)
    {
        String date;
        String sr=nowdate.substring(4,5);
        date=nowdate.substring(0,4)+"-"+nowdate.substring(4,6)+"-"+nowdate.substring(6,8);
        return date;
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

    //我的成绩
    @Subscribe
    public void onEvent(GradesEvent gradesEvent) {
        System.out.println("------------gradesEvent："+gradesEvent.getgradesModels().size());
        System.out.println("------------曲线图size()"+gradesEvent.getgradesModels().size());
        List<GradesModel>gradesModels=gradesEvent.getgradesModels();
//        if(gradesEvent==null){
//            return;
//        }
        System.out.println("健康记录" + gradesEvent.getgradesModels());
        int n=gradesEvent.getgradesModels().size();
        for (int i=0;i<=n-1;i++)
        {
            if(getDateform(nowdate1).equals(gradesEvent.getgradesModels().get(i).getDate())) {
                dates.set(0,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate2).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(1,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate3).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(2,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate4).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(3,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate5).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(4,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate6).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(5,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
            if (getDateform(nowdate7).equals(gradesEvent.getgradesModels().get(i).getDate()))
            {
                dates.set(6,Float.parseFloat(gradesEvent.getgradesModels().get(i).getTotalCnt()));
            }
        }
        chartUtil.addDataf(dates,7,days);
        dates.clear();
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
        dates.add(0f);
//        for (GradesModel gl:gradesModels){
//            System.out.println("日期"+gl.getDate()+"步数"+gl.getTotalCnt());
//            float TotalCnt=Float.parseFloat(gl.getTotalCnt());
//            dates.add(TotalCnt);
//            days.add(gl.getDate());
//        }
//        days.add(5+"/"+10);
//        days.add(mMonth+"/"+mDay);
//        chartUtil=new LineChartUtil(MyGradesActivity.this,chart);
//        chartUtil.addDataf(dates,4,days);
    }

    //3.3.2	成绩勋章信息
    public void getGradeHonor(){
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getGradeHonor(token, new Callback<ResponseData<GradeHonorModel>>() {
            @Override
            public void success(ResponseData<GradeHonorModel> gradeHonorModelResponseData, Response response) {
                int status=gradeHonorModelResponseData.getStatus();
                switch (status){
//                  {"status":500,"msg":"Invalid object name 'HL_LaiHhonor'.","data":{}}
                    case 200:
                        //总步数
                        String totalnumber=gradeHonorModelResponseData.getData().getTotalStep();
                        if (totalnumber==""){
                            tv_totalnumber.setText("0");
                        }else {
                            tv_totalnumber.setText(totalnumber);
                        }
                        //总公里数计算公式: 1公里=1428步 (单位为公里, 0.01公里, 不足0.01公里时显示0)
                        DecimalFormat format = new DecimalFormat("##0.00");
                        Double totalmileage =Double.parseDouble(totalnumber)/1428;
                        String temp = format.format(totalmileage);
                        if (totalmileage==0.0){
                            tv_totalmileage.setText("0");
                        }
                        else if (totalmileage<0.01){
                            tv_totalmileage.setText("0");
                        }
                        else {
                            tv_totalmileage.setText(temp);
                        }

                        //前三名用橙色,其他名次用绿色android:textColor="#ff9c00"
                        int Nationaldayrank=Integer.parseInt(gradeHonorModelResponseData.getData().getContryDayOrder());
                        int Rundayrank=Integer.parseInt(gradeHonorModelResponseData.getData().getDayOrder());
                        int Nationalweekrank=Integer.parseInt(gradeHonorModelResponseData.getData().getWeekOrder());
                        int Runweekrank=Integer.parseInt(gradeHonorModelResponseData.getData().getWeekOrderRG());
                        if(Nationaldayrank==1||Nationaldayrank==2||Nationaldayrank==3){
                            //0xffff00ff是int类型的数据，分组一下0x|ff|ff00ff，0x是代表颜色整数的标记，ff是表示透明度，ff00ff表示颜色，注意：这里ffff00ff必须是8个的颜色表示，不接受ff00ff这种6个的颜色表示。
                            tv_Nationaldayrank.setTextColor(0xffff9c00);
                        }
                        if(Rundayrank==1||Rundayrank==2||Rundayrank==3){
                            tv_Rundayrank.setTextColor(0xffff9c00);
                        }
                        if(Runweekrank==1||Runweekrank==2||Runweekrank==3){
                            tv_Runweekrank.setTextColor(0xffff9c00);
                        }
                        if(Nationalweekrank==1||Nationalweekrank==2||Nationalweekrank==3){
                            tv_Nationalweekrank.setTextColor(0xffff9c00);
                        }

                        //全国排名
                        tv_Nationaldayrank.setText(Nationaldayrank+"");
                        tv_Nationaldaypeople.setText(gradeHonorModelResponseData.getData().getContryDayOrderTotal()+"");
                        //跑团的排名
                        tv_Rundayrank.setText(Rundayrank+"");
                        tv_Rundaypeople.setText(gradeHonorModelResponseData.getData().getDayOrderTotal()+"");

                        tv_Nationalweekrank.setText(Nationalweekrank+"");
                        tv_Nationalweekpeople.setText(gradeHonorModelResponseData.getData().getContryDayOrderTotal()+"");
                        tv_Runweekrank.setText(Runweekrank+"");
                        tv_Runweekpeople.setText(gradeHonorModelResponseData.getData().getDayOrderTotal()+"");

                        tv_medalnumber.setText(gradeHonorModelResponseData.getData().getTotalHonor()+"");

                        //我的勋章显示
                        if (gradeHonorModelResponseData.getData().getLaiHonor().size()==0){
                            ll_honor.setVisibility(View.GONE);
                        }else {
                            //判断是否是3个勋章
                            if (gradeHonorModelResponseData.getData().getLaiHonor().size() == 1) {
                                //判断第一个勋章是什么类型
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor1.setImageResource(R.drawable.img_medal1);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor1.setImageResource(R.drawable.img_medal);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        tv_str1.setText("天使听见爱");
                                        img_honor1.setImageResource(R.drawable.img_medal);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue()) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;

                                }
                            }
                            if (gradeHonorModelResponseData.getData().getLaiHonor().size() == 2) {
                                //判断第一，第二个勋章是什么类型
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor1.setImageResource(R.drawable.img_medal1);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor1.setImageResource(R.drawable.img_medal);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        tv_str1.setText("天使听见爱");
                                        img_honor1.setImageResource(R.drawable.img_medal1);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue()) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor2.setImageResource(R.drawable.img_medal1);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor2.setImageResource(R.drawable.img_medal);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("天使听见爱");
                                        img_honor2.setImageResource(R.drawable.img_medal);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue()) {
                                            case 1:
                                                tv_str2.setText("挑战达人铜牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str2.setText("挑战达人银牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str2.setText("挑战达人金牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str2.setText("挑战明星铜牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str2.setText("挑战明星银牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str2.setText("挑战明星金牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;

                                }
                            }
                            if (gradeHonorModelResponseData.getData().getLaiHonor().size() == 3) {
                                //判断第一，第二，第三个勋章是什么类型
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor1.setImageResource(R.drawable.img_medal1);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        tv_str1.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor1.setImageResource(R.drawable.img_medal);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        tv_str1.setText("天使听见爱");
                                        img_honor1.setImageResource(R.drawable.img_medal);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue()) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor2.setImageResource(R.drawable.img_medal1);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor2.setImageResource(R.drawable.img_medal);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        tv_str2.setText("天使听见爱");
                                        img_honor2.setImageResource(R.drawable.img_medal);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue()) {
                                            case 1:
                                                tv_str2.setText("挑战达人铜牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str2.setText("挑战达人银牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str2.setText("挑战达人金牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str2.setText("挑战明星铜牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str2.setText("挑战明星银牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str2.setText("挑战明星金牌");
                                                img_honor2.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorType()) {
                                    case 1:
                                        //天数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        tv_str3.setText("连续" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "天步数一万");
                                        img_honor3.setImageResource(R.drawable.img_medal1);
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        tv_str3.setText("累计步数" + gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue() + "万");
                                        img_honor3.setImageResource(R.drawable.img_medal);
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //天使听见爱
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        tv_str3.setText("天使听见爱");
                                        img_honor3.setImageResource(R.drawable.img_medal);
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        switch (gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue()) {
                                            case 1:
                                                tv_str3.setText("挑战达人铜牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 50:
                                                tv_str3.setText("挑战达人银牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 100:
                                                tv_str3.setText("挑战达人金牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 200:
                                                tv_str3.setText("挑战明星铜牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 300:
                                                tv_str3.setText("挑战明星银牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                            case 500:
                                                tv_str3.setText("挑战明星金牌");
                                                img_honor3.setImageResource(R.drawable.img_medal1);
                                                break;
                                        }
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
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
        int flag;
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_dayRank:
                Intent intent=new Intent(MyGradesActivity.this,RankingDetailsActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
                break;
            case R.id.ll_weekRank:
                Intent intent1=new Intent(MyGradesActivity.this,RankingDetailsActivity.class);
                intent1.putExtra("flag",1);
                startActivity(intent1);
                break;
            case R.id.bt_left:
                        if (state!=true)
                        {
                            n=n+7;
                        }
                        state=true;
                        days.clear();
                         nowdate7 = getPeriodDate(type, n) + "";
                         nowdate6 = getPeriodDate(type, n + 1) + "";
                         nowdate5 = getPeriodDate(type, n + 2) + "";
                         nowdate4 = getPeriodDate(type, n + 3) + "";
                         nowdate3 = getPeriodDate(type, n + 4) + "";
                         nowdate2 = getPeriodDate(type, n + 5) + "";
                         nowdate1 = getPeriodDate(type, n + 6) + "";
                        days.add(formdate(nowdate1));
                        days.add(formdate(nowdate2));
                        days.add(formdate(nowdate3));
                        days.add(formdate(nowdate4));
                        days.add(formdate(nowdate5));
                        days.add(formdate(nowdate6));
                        days.add(formdate(nowdate7));
                        //progressDialog.show();
                        iGradesPresenter.getStepCount(getDateform(nowdate1),getDateform(nowdate7));
                        n = n + 7;
                        bt_right.setVisibility(View.VISIBLE);
                        break;
            case R.id.bt_right:
                        if (state!=false) {
                            n = n - 14;
                        }
                        else {
                            n=n-7;
                        }
                        days.clear();
                         nowdate7 = getPeriodDate(type, n) + "";
                         nowdate6 = getPeriodDate(type, n + 1) + "";
                         nowdate5 = getPeriodDate(type, n + 2) + "";
                         nowdate4 = getPeriodDate(type, n + 3) + "";
                         nowdate3 = getPeriodDate(type, n + 4) + "";
                         nowdate2 = getPeriodDate(type, n + 5) + "";
                         nowdate1 = getPeriodDate(type, n + 6) + "";
                        days.add(formdate(nowdate1));
                        days.add(formdate(nowdate2));
                        days.add(formdate(nowdate3));
                        days.add(formdate(nowdate4));
                        days.add(formdate(nowdate5));
                        days.add(formdate(nowdate6));
                        days.add(formdate(nowdate7));
                        //progressDialog.show();
                        iGradesPresenter.getStepCount(getDateform(nowdate1),getDateform(nowdate7));
                        state=false;
                        if (nowdate7.equals(getPeriodDate(type,0)+""))
                            bt_right.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
