package com.softtek.lai.module.mygrades.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.mygrades.eventModel.GradesEvent;
import com.softtek.lai.module.mygrades.model.GradeHonorModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.model.ScoreModel;
import com.softtek.lai.module.mygrades.net.GradesService;
import com.softtek.lai.module.mygrades.presenter.GradesImpl;
import com.softtek.lai.module.mygrades.presenter.IGradesPresenter;
import com.softtek.lai.module.studetail.util.LineChartUtil;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/16/2016.
 * 莱运动-我的成绩页面
 */
@InjectLayout(R.layout.activity_my_grades)
public class MyGradesActivity extends BaseActivity implements View.OnClickListener {

    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;

    @InjectView(R.id.rel)
    RelativeLayout rel;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //分享功能
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.scrollView1)
    ScrollView scrollView1;

    @InjectView(R.id.ll_dayRank)
    LinearLayout ll_dayRank;
    @InjectView(R.id.ll_weekRank)
    LinearLayout ll_weekRank;
    @InjectView(R.id.rl_grade)
    RelativeLayout rl_grade;

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
    //折线图
    @InjectView(R.id.chart)
    LineChart chart;
    @InjectView(R.id.bt_left)
    Button bt_left;
    @InjectView(R.id.bt_right)
    Button bt_right;

    LineChartUtil chartUtil;
    private IGradesPresenter iGradesPresenter;
    private GradesService gradesService;
    String url;
    String value;
    String title_value;
    SelectPicPopupWindow menuWindow;
    ScoreModel scoreModel;
    private static final int LOCATION_PREMISSION = 100;


    private IGetProinfopresenter iGetProinfopresenter;

    char type = '6';
    int n = 7;
    boolean state = true;
    List<String> days = new ArrayList<>();
    List<Integer> dates = new ArrayList<>();
    String nowdate7, nowdate6, nowdate5, nowdate4, nowdate3, nowdate2, nowdate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        title.setText("我的成绩");
        iv_email.setImageResource(R.drawable.img_share_bt);
        ll_dayRank.setOnClickListener(this);
        ll_weekRank.setOnClickListener(this);
        rl_grade.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);

        //初始化统计图
        chartUtil = new LineChartUtil(this, chart);
        chart.setDrawGridBackground(false);//取消统计图整体背景色
        chart.setDescription("");//取消描述信息,设置没有数据的时候提示信息（单位：步）
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

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        dialogDissmiss();
        System.out.println(photModel);
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        String path = AddressManager.get("shareHost");
        url = path + "ShareMineSportScore?AccountId=" + UserInfoModel.getInstance().getUser().getUserid() + "&Imgpath=" + photModel.getImg();
        System.out.println("url:" + url);
        value = "我已累计跑步" + scoreModel.getTodayKaluli() + "km，总步数" + scoreModel.getTotalStep() + "步，今日全国排名第" + scoreModel.getContryDayOrder() + "名，跑团排名第" + scoreModel.getDayOrder() + "名。快来和我一起运动吧！";
        System.out.println("value:" + value);
        title_value = "莱运动, 陪伴我运动第" + scoreModel.getRgTime() + "天";
        menuWindow = new SelectPicPopupWindow(MyGradesActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(MyGradesActivity.this.findViewById(R.id.rel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(MyGradesActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(MyGradesActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(MyGradesActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initDatas() {
        iGradesPresenter = new GradesImpl();
        iGetProinfopresenter = new GetProinfoImpl(this);
        gradesService = ZillaApi.NormalRestAdapter.create(GradesService.class);
        //3.3.2	成绩勋章信息
        getGradeHonor();

        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        days.clear();
        nowdate7 = getPeriodDate(type, 0) + "";
        nowdate6 = getPeriodDate(type, 1) + "";
        nowdate5 = getPeriodDate(type, 2) + "";
        nowdate4 = getPeriodDate(type, 3) + "";
        nowdate3 = getPeriodDate(type, 4) + "";
        nowdate2 = getPeriodDate(type, 5) + "";
        nowdate1 = getPeriodDate(type, 6) + "";
        days.add(formdate(nowdate1));
        days.add(formdate(nowdate2));
        days.add(formdate(nowdate3));
        days.add(formdate(nowdate4));
        days.add(formdate(nowdate5));
        days.add(formdate(nowdate6));
        days.add(formdate(nowdate7));
        iGradesPresenter.getStepCount(getDateform(nowdate1), getDateform(nowdate7));
    }

    public String getDateform(String nowdate) {
        String date;
        date = nowdate.substring(0, 4) + "-" + nowdate.substring(4, 6) + "-" + nowdate.substring(6, 8);
        return date;
    }

    public String formdate(String nowdate) {
        String date;
        if (nowdate.substring(4, 5).equals("0")) {
            date = nowdate.substring(5, 6) + "/" + nowdate.substring(6, 8);
        } else {
            date = nowdate.substring(4, 6) + "/" + nowdate.substring(6, 8);
        }
        return date;
    }

    //我的成绩曲线图
    @Subscribe
    public void onEvent(GradesEvent gradesEvent) {
        int n = gradesEvent.getgradesModels().size();
        for (int i = 0; i <= n - 1; i++) {
            GradesModel model=gradesEvent.getgradesModels().get(i);
            int totalCnt=Integer.parseInt(model.getTotalCnt());
            if (getDateform(nowdate1).equals(model.getDate())) {
                dates.set(0, totalCnt);
            }
            if (getDateform(nowdate2).equals(model.getDate())) {
                dates.set(1, totalCnt);
            }
            if (getDateform(nowdate3).equals(model.getDate())) {
                dates.set(2, totalCnt);
            }
            if (getDateform(nowdate4).equals(model.getDate())) {
                dates.set(3, totalCnt);
            }
            if (getDateform(nowdate5).equals(model.getDate())) {
                dates.set(4, totalCnt);
            }
            if (getDateform(nowdate6).equals(model.getDate())) {
                dates.set(5, totalCnt);
            }
            if (getDateform(nowdate7).equals(model.getDate())) {
                dates.set(6, totalCnt);
            }
        }
        chartUtil.addDataf(dates, 7, days);
        dates.clear();
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
        dates.add(0);
    }

    private SpannableString getString(String value,int color,int start){
        SpannableString spannableString=new SpannableString(value);
        spannableString.setSpan(new ForegroundColorSpan(color),start,value.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    //成绩勋章信息
    public void getGradeHonor() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getGradeHonor(token, new Callback<ResponseData<GradeHonorModel>>() {
            @Override
            public void success(ResponseData<GradeHonorModel> gradeHonorModelResponseData, Response response) {
                int status = gradeHonorModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        //总步数
                        GradeHonorModel model=gradeHonorModelResponseData.getData();
                        String totalnumber = model.getTotalStep();
                        if (totalnumber == "") {
                            tv_totalnumber.setText("0");
                        } else {
                            tv_totalnumber.setText(totalnumber);
                        }
                        //总公里数计算公式: 1公里=1428步 (单位为公里, 0.01公里, 不足0.01公里时显示0)
                        DecimalFormat format = new DecimalFormat("##0.00");
                        Double totalmileage = Double.parseDouble(totalnumber) / 1428;
                        String temp = format.format(totalmileage);
                        if (totalmileage == 0.0) {
                            tv_totalmileage.setText("0");
                        } else if (totalmileage < 0.01) {
                            tv_totalmileage.setText("0");
                        } else {
                            tv_totalmileage.setText(temp);
                        }
                        String nationalDayRank=model.getContryDayOrder();
                        String runDayRank=model.getDayOrder();
                        String nationWeekRank=model.getWeekOrder();
                        String runWeekRank=model.getWeekOrderRG();
                        if("--".equals(nationalDayRank)){
                            //全国日排名
                            tv_Nationaldayrank.setText(nationalDayRank);

                        }else {
                            int Nationaldayrank = Integer.parseInt(nationalDayRank);
                            if(Nationaldayrank>0&&Nationaldayrank<=3){
                                tv_Nationaldayrank.setText(getString("全国第 "+nationalDayRank+" 名",0xffff9c00,3));
                            }else{
                                tv_Nationaldayrank.setText(getString("全国第 "+nationalDayRank+" 名",0xff74b92a,3));
                            }
                            //全国第几名

                        }
                        if("--".equals(runDayRank)){
                            //跑团日排名
                            tv_Rundayrank.setText(runDayRank);

                        }else {
                            int Rundayrank = Integer.parseInt(runDayRank);
                            if(Rundayrank>0&&Rundayrank<=3){
                                tv_Rundayrank.setText(getString("跑团第 "+runDayRank+" 名",0xffff9c00,3));
                            }else {
                                tv_Rundayrank.setText(getString("跑团第 "+runDayRank+" 名",0xff74b92a,3));
                            }
                        }
                        if("--".equals(nationWeekRank)){
                            //全国周排名
                            tv_Nationalweekrank.setText(nationWeekRank);

                        }else{
                            int Nationalweekrank = Integer.parseInt(nationWeekRank);
                            if(Nationalweekrank>0&&Nationalweekrank<=3){
                                tv_Nationalweekrank.setText(getString("全国第 "+nationWeekRank+" 名",0xffff9c00,3));
                            }else {
                                tv_Nationalweekrank.setText(getString("全国第 "+nationWeekRank+" 名",0xff74b92a,3));
                            }
                        }
                        if("--".equals(runWeekRank)){
                            //跑团团周排名
                            tv_Runweekrank.setText(runWeekRank);

                        }else{
                            int Runweekrank = Integer.parseInt(runWeekRank);
                            if(Runweekrank>0&&Runweekrank<=3){
                                tv_Runweekrank.setText(getString("跑团第 "+runWeekRank+" 名",0xffff9c00,3));
                            }else {
                                tv_Runweekrank.setText(getString("跑团第 " + runWeekRank + " 名",0xff74b92a, 3));
                            }
                        }
                        tv_Nationaldaypeople.setText("共 "+model.getContryDayOrderTotal()+" 人");
                        tv_Rundaypeople.setText("共 "+model.getDayOrderTotal()+" 人");
                        tv_Nationalweekpeople.setText("共 "+model.getContryDayOrderTotal()+" 人");
                        tv_Runweekpeople.setText("共 "+model.getDayOrderTotal()+" 人");

                        tv_medalnumber.setText("我的勋章（"+model.getTotalHonor()+"）");
                        //我的勋章显示
                        if (model.getLaiHonor().size() == 0) {
                            ll_honor.setVisibility(View.GONE);
                        } else {
                            //判断是否是3个勋章
                            if (model.getLaiHonor().size() == 1) {
                                //判断第一个勋章是什么类型
                                switch (model.getLaiHonor().get(0).getHonorType()) {
                                    case 1:
                                        //天数
                                        int value = model.getLaiHonor().get(0).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str1.setText("连续3天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str1.setText("连续7天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str1.setText("连续21天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str1.setText("连续30天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("连续100天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("连续200天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str1.setText("连续365天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = model.getLaiHonor().get(0).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str1.setText("累计步数10万");
                                                img_honor1.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("累计步数50万");
                                                img_honor1.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("累计步数100万");
                                                img_honor1.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("累计步数200万");
                                                img_honor1.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("累计步数500万");
                                                img_honor1.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str1.setText("累计步数1000万");
                                                img_honor1.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str1.setText("累计步数2000万");
                                                img_honor1.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str1.setText("累计步数3000万");
                                                img_honor1.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //
                                        tv_str1.setText("爱心天使");
                                        img_honor1.setImageResource(R.drawable.img_angel_have);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = model.getLaiHonor().get(0).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_500_have);
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
                                        int value = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str1.setText("连续3天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str1.setText("连续7天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str1.setText("连续21天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str1.setText("连续30天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("连续100天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("连续200天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str1.setText("连续365天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str1.setText("累计步数10万");
                                                img_honor1.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("累计步数50万");
                                                img_honor1.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("累计步数100万");
                                                img_honor1.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("累计步数200万");
                                                img_honor1.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("累计步数500万");
                                                img_honor1.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str1.setText("累计步数1000万");
                                                img_honor1.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str1.setText("累计步数2000万");
                                                img_honor1.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str1.setText("累计步数3000万");
                                                img_honor1.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //爱心天使
                                        tv_str1.setText("爱心天使");
                                        img_honor1.setImageResource(R.drawable.img_angel_have);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_500_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorType()) {
                                    case 1:
                                        //天数
                                        int value = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str2.setText("连续3天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str2.setText("连续7天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str2.setText("连续21天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str2.setText("连续30天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str2.setText("连续100天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str2.setText("连续200天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str2.setText("连续365天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str1.setText("累计步数10万");
                                                img_honor1.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("累计步数50万");
                                                img_honor1.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("累计步数100万");
                                                img_honor1.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("累计步数200万");
                                                img_honor1.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("累计步数500万");
                                                img_honor1.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str1.setText("累计步数1000万");
                                                img_honor1.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str1.setText("累计步数2000万");
                                                img_honor1.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str1.setText("累计步数3000万");
                                                img_honor1.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //爱心天使
                                        tv_str2.setText("爱心天使");
                                        img_honor2.setImageResource(R.drawable.img_angel_have);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str2.setText("挑战达人铜牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str2.setText("挑战达人银牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str2.setText("挑战达人金牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str2.setText("挑战明星铜牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str2.setText("挑战明星银牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str2.setText("挑战明星金牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_500_have);
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
                                        int value = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str1.setText("连续3天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str1.setText("连续7天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str1.setText("连续21天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str1.setText("连续30天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("连续100天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("连续200天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str1.setText("连续365天步数一万");
                                                img_honor1.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str1.setText("累计步数10万");
                                                img_honor1.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("累计步数50万");
                                                img_honor1.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("累计步数100万");
                                                img_honor1.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("累计步数200万");
                                                img_honor1.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("累计步数500万");
                                                img_honor1.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str1.setText("累计步数1000万");
                                                img_honor1.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str1.setText("累计步数2000万");
                                                img_honor1.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str1.setText("累计步数3000万");
                                                img_honor1.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //爱心天使
                                        tv_str1.setText("爱心天使");
                                        img_honor1.setImageResource(R.drawable.img_angel_have);
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(0).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str1.setText("挑战达人铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("挑战达人银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("挑战达人金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("挑战明星铜牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str1.setText("挑战明星银牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("挑战明星金牌");
                                                img_honor1.setImageResource(R.drawable.img_pk_500_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorType()) {
                                    case 1:
                                        //天数
                                        int value = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str2.setText("连续3天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str2.setText("连续7天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str2.setText("连续21天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str2.setText("连续30天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str2.setText("连续100天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str2.setText("连续200天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str2.setText("连续365天步数一万");
                                                img_honor2.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str2.setText("累计步数10万");
                                                img_honor2.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str2.setText("累计步数50万");
                                                img_honor2.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str2.setText("累计步数100万");
                                                img_honor2.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str2.setText("累计步数200万");
                                                img_honor2.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str2.setText("累计步数500万");
                                                img_honor2.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str2.setText("累计步数1000万");
                                                img_honor2.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str2.setText("累计步数2000万");
                                                img_honor2.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str2.setText("累计步数3000万");
                                                img_honor2.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //爱心天使
                                        tv_str2.setText("爱心天使");
                                        img_honor2.setImageResource(R.drawable.img_angel_have);
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(1).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str2.setText("挑战达人铜牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str2.setText("挑战达人银牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str2.setText("挑战达人金牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str2.setText("挑战明星铜牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str2.setText("挑战明星银牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str2.setText("挑战明星金牌");
                                                img_honor2.setImageResource(R.drawable.img_pk_500_have);
                                                break;
                                        }
                                        ll_honor2.setVisibility(View.VISIBLE);
                                        break;

                                }
                                switch (gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorType()) {
                                    case 1:
                                        //天数
                                        int value = gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        switch ((value >= 3 && value < 7) ? 3 : (value >= 7 && value < 21) ? 7 : (value >= 21 && value < 30) ? 21 : (value >= 30 && value < 100) ? 30 : (value >= 100 && value < 200) ? 100 : (value >= 200 && value < 365) ? 200 : (value >= 365) ? 365 : 365) {
                                            case 3:
                                                tv_str3.setText("连续3天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_three_have);
                                                break;
                                            case 7:
                                                tv_str3.setText("连续7天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_seven_have);
                                                break;
                                            case 21:
                                                tv_str3.setText("连续21天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_twenty_one_have);
                                                break;
                                            case 30:
                                                tv_str3.setText("连续30天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_thirty_have);
                                                break;
                                            case 100:
                                                tv_str3.setText("连续100天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_hundred_day_have);
                                                break;
                                            case 200:
                                                tv_str3.setText("连续200天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_day200_have);
                                                break;
                                            case 365:
                                                tv_str3.setText("连续365天步数一万");
                                                img_honor3.setImageResource(R.drawable.img_day365_have);
                                                break;
                                        }
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                    case 2:
                                        //步数
                                        int stepvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        switch ((stepvalue >= 10 && stepvalue < 50) ? 10 : (stepvalue >= 50 && stepvalue < 100) ? 50 : (stepvalue >= 100 && stepvalue < 200) ? 100 : (stepvalue >= 200 && stepvalue < 500) ? 200 : (stepvalue >= 500 && stepvalue < 1000) ? 500 : (stepvalue >= 1000 && stepvalue < 2000) ? 1000 : (stepvalue >= 2000 && stepvalue < 3000) ? 2000 : (stepvalue >= 3000) ? 3000 : 3000) {
                                            case 10:
                                                tv_str1.setText("累计步数10万");
                                                img_honor1.setImageResource(R.drawable.img_total_10_have);
                                                break;
                                            case 50:
                                                tv_str1.setText("累计步数50万");
                                                img_honor1.setImageResource(R.drawable.img_total_50_have);
                                                break;
                                            case 100:
                                                tv_str1.setText("累计步数100万");
                                                img_honor1.setImageResource(R.drawable.img_total_100_have);
                                                break;
                                            case 200:
                                                tv_str1.setText("累计步数200万");
                                                img_honor1.setImageResource(R.drawable.img_total_200_have);
                                                break;
                                            case 500:
                                                tv_str1.setText("累计步数500万");
                                                img_honor1.setImageResource(R.drawable.img_total_500_have);
                                                break;
                                            case 1000:
                                                tv_str1.setText("累计步数1000万");
                                                img_honor1.setImageResource(R.drawable.img_total_1000_have);
                                                break;
                                            case 2000:
                                                tv_str1.setText("累计步数2000万");
                                                img_honor1.setImageResource(R.drawable.img_total_2000_have);
                                                break;
                                            case 3000:
                                                tv_str1.setText("累计步数3000万");
                                                img_honor1.setImageResource(R.drawable.img_total_3000_have);
                                                break;
                                        }
                                        ll_honor1.setVisibility(View.VISIBLE);
                                        break;
                                    case 3:
                                        //爱心天使
                                        tv_str3.setText("爱心天使");
                                        img_honor3.setImageResource(R.drawable.img_angel_have);
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                    case 4:
                                        //PK挑战
                                        int pkvalue = gradeHonorModelResponseData.getData().getLaiHonor().get(2).getHonorVlue();
                                        switch ((pkvalue >= 1 && pkvalue < 50) ? 1 : (pkvalue >= 50 && pkvalue < 100) ? 50 : (pkvalue >= 100 && pkvalue < 200) ? 100 : (pkvalue >= 200 && pkvalue < 300) ? 200 : (pkvalue >= 300 && pkvalue < 500) ? 300 : (pkvalue >= 500) ? 500 : 500) {
                                            case 1:
                                                tv_str3.setText("挑战达人铜牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_1_have);
                                                break;
                                            case 50:
                                                tv_str3.setText("挑战达人银牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_50_have);
                                                break;
                                            case 100:
                                                tv_str3.setText("挑战达人金牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_100_have);
                                                break;
                                            case 200:
                                                tv_str3.setText("挑战明星铜牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_200_have);
                                                break;
                                            case 300:
                                                tv_str3.setText("挑战明星银牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_300_have);
                                                break;
                                            case 500:
                                                tv_str3.setText("挑战明星金牌");
                                                img_honor3.setImageResource(R.drawable.img_pk_500_have);
                                                break;
                                        }
                                        ll_honor3.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        }
                        break;
                    default:
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

    private void setShare() {
        dialogShow("加载中");
        Bitmap b1 = getViewBitmap(chart);
        savePic(b1, "/sdcard/scores.png");
        GradesService service = ZillaApi.NormalRestAdapter.create(GradesService.class);
        String token = UserInfoModel.getInstance().getToken();
        UserModel model = UserInfoModel.getInstance().getUser();
        service.getMineScore(token, model.getUserid(), new Callback<ResponseData<ScoreModel>>() {
            @Override
            public void success(ResponseData<ScoreModel> responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        scoreModel = responseData.getData();
                        System.out.println("scoreModel:"+scoreModel);
                        iGetProinfopresenter.upload("/sdcard/scores.png");
                        break;
                    default:
                        dialogDissmiss();
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    //6.0权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PREMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setShare();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }

    }


    @Override
    public void onClick(View v) {

        int flag;
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //进入日排名详情
            case R.id.ll_dayRank:
                Intent intent = new Intent(MyGradesActivity.this, RankingDetailsActivity.class);
                intent.putExtra("flag", 0);
                startActivity(intent);
                break;
            //进入周排名详情
            case R.id.ll_weekRank:
                Intent intent1 = new Intent(MyGradesActivity.this, RankingDetailsActivity.class);
                intent1.putExtra("flag", 1);
                startActivity(intent1);
                break;
            //我的勋章跳转
            case R.id.rl_grade:
                startActivity(new Intent(this, MyXuZhangActivity.class));
                break;
            //分享
            case R.id.fl_right:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    com.github.snowdream.android.util.Log.i("检查权限。。。。");
                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                    if (
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //允许弹出提示
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);

                    } else {
                        //不允许弹出提示
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);
                    }
                } else {
                    setShare();
                }


                break;
            case R.id.bt_left:
                if (state != true) {
                    n = n + 7;
                }
                state = true;
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
                iGradesPresenter.getStepCount(getDateform(nowdate1), getDateform(nowdate7));
                n = n + 7;
                bt_right.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_right:
                if (state != false) {
                    n = n - 14;
                } else {
                    n = n - 7;
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
                iGradesPresenter.getStepCount(getDateform(nowdate1), getDateform(nowdate7));
                state = false;
                if (nowdate7.equals(getPeriodDate(type, 0) + ""))
                    bt_right.setVisibility(View.GONE);
                break;
        }
    }

    /*微信分享,朋友圈分享,微博分享*/
//    String path = AddressManager.get("shareHost");
//    url = path + "ShareSPHonor?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
//    value = "康宝莱体重管理挑战赛，坚持只为改变！";
//    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
//        public void onClick(View v) {
//            menuWindow.dismiss();
//            switch (v.getId()) {
//                case R.id.lin_weixin:
//                    new ShareAction(MyGradesActivity.this)
//                            .setPlatform(SHARE_MEDIA.WEIXIN)
//                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
//                            .withText(value)
//                            .withTargetUrl(url)
//                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
//                            .share();
//                    break;
//                case R.id.lin_circle:
//                    new ShareAction(MyGradesActivity.this)
//                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
//                            .withText(value)
//                            .withTargetUrl(url)
//                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
//                            .share();
//                    break;
//                case R.id.lin_sina:
//                    new ShareAction(MyGradesActivity.this)
//                            .setPlatform(SHARE_MEDIA.SINA)
//                            .withText(value+url)
//                            .withMedia(new UMImage(MyGradesActivity.this, R.drawable.img_share_logo))
//                            .share();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    /**
     * 把View对象转换成bitmap
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("TTTTTTTTActivity", "failed getViewBitmap(" + v + ")",
                    new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    /**
     * 获取阶段日期
     *
     * @param dateType
     * @author Yangtse
     */
    //使用方法 char datetype = '7';
    public static StringBuilder getPeriodDate(char dateType, int n) {
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
                break;
            case '2': // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '3': // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '4': // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case '5': // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '6': // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case '7': // 一个月前
                day = c.get(Calendar.DAY_OF_MONTH) - 30 * n;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
        }
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        return strForwardDate;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // 保存到sdcard
    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
}
