/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.softtek.lai.module.historydate.view.HistoryDataActivity;
import com.softtek.lai.module.home.model.LaichModel;
import com.softtek.lai.module.home.presenter.HealthyRecordManager;
import com.softtek.lai.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy_record)
public class HealthyRecordActivity extends BaseActivity implements View.OnClickListener, PullToRefreshScrollView.OnRefreshListener<ScrollView>
        , HealthyRecordManager.HealthyRecordCallback {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.tv_weight)
    TextView tv_weight;
    @InjectView(R.id.tv_body_fat)
    TextView tv_body_fat;
    @InjectView(R.id.tv_fat)
    TextView tv_fat;
    @InjectView(R.id.tv_bust)
    TextView tv_bust;
    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;
    @InjectView(R.id.tv_hipline)
    TextView tv_hipline;
    @InjectView(R.id.tv_uphipline)
    TextView tv_uphipline;
    @InjectView(R.id.tv_leg)
    TextView tv_leg;
    @InjectView(R.id.tv_shin)
    TextView tv_shin;
    @InjectView(R.id.tv_healthhistoty)
    TextView tv_healthhistoty;
    @InjectView(R.id.tv_healthdate)
    TextView tv_healthdate;



    @InjectView(R.id.tv_health_weight)
    TextView tv_health_weight;
    @InjectView(R.id.tv_health_Pysical)
    TextView tv_health_Pysical;
    @InjectView(R.id.tv_health_fat)
    TextView tv_health_fat;
    @InjectView(R.id.tv_health_circum)
    TextView tv_health_circum;
    @InjectView(R.id.tv_health_waistline)
    TextView tv_health_waistline;
    @InjectView(R.id.tv_health_hiplie)
    TextView tv_health_hiplie;
    @InjectView(R.id.tv_health_upArmGirth)
    TextView tv_health_upArmGirth;
    @InjectView(R.id.tv_health_upLegGirth)
    TextView tv_health_upLegGirth;
    @InjectView(R.id.tv_health_doLegGirth)
    TextView tv_health_doLegGirth;
    @InjectView(R.id.tv_healthtime)
    TextView tv_healthtime;
    @InjectView(R.id.healthy_refresh)
    PullToRefreshScrollView healthy_refresh;
    HealthyRecordManager retestPre;
    String mobile;

    @Override
    protected void initViews() {
        tv_weight.setOnClickListener(this);
        tv_body_fat.setOnClickListener(this);
        tv_fat.setOnClickListener(this);
        tv_bust.setOnClickListener(this);
        tv_waistline.setOnClickListener(this);
        tv_hipline.setOnClickListener(this);
        tv_uphipline.setOnClickListener(this);
        tv_leg.setOnClickListener(this);
        tv_shin.setOnClickListener(this);
        tv_healthhistoty.setOnClickListener(this);

        iv_email.setBackground(ContextCompat.getDrawable(this,R.drawable.healthedit));
        healthy_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        healthy_refresh.setOnRefreshListener(this);
        ILoadingLayout startLabelse = healthy_refresh.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录");
        fl_right.setVisibility(View.VISIBLE);
        fl_right.setOnClickListener(this);
        //获取健康记录
        mobile = UserInfoModel.getInstance().getUser().getMobile();
        retestPre = new HealthyRecordManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(healthy_refresh!=null){
                    healthy_refresh.setRefreshing();
                }
            }
        }, 300);


    }


    private static final int EDIT_HEALTHY_RECORD = 2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                startActivityForResult(new Intent(this, HealthEntryActivity.class), 2);
                break;
            case R.id.tv_weight:
                Intent intent = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.tv_body_fat:
                Intent intent1 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent1.putExtra("id", 1);
                intent1.putExtra("flag", 1);
                startActivity(intent1);
                break;
            case R.id.tv_fat:
                Intent intent2 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent2.putExtra("id", 2);
                intent2.putExtra("flag", 1);
                startActivity(intent2);
                break;
            case R.id.tv_bust:
                Intent intent3 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent3.putExtra("id", 3);
                intent3.putExtra("flag", 1);
                startActivity(intent3);
                break;
            case R.id.tv_waistline:
                Intent intent4 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent4.putExtra("id", 4);
                intent4.putExtra("flag", 1);
                startActivity(intent4);
                break;
            case R.id.tv_hipline:
                Intent intent5 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent5.putExtra("id", 5);
                intent5.putExtra("flag", 1);
                startActivity(intent5);
                break;
            case R.id.tv_uphipline:
                Intent intent6 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent6.putExtra("id", 6);
                intent6.putExtra("flag", 1);
                startActivity(intent6);
                break;
            case R.id.tv_leg:
                Intent intent7 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent7.putExtra("id", 7);
                intent7.putExtra("flag", 1);
                startActivity(intent7);
                break;
            case R.id.tv_shin:
                Intent intent8 = new Intent(this, com.softtek.lai.module.health.view.HealthyRecordActivity.class);
                intent8.putExtra("id", 8);
                intent8.putExtra("flag", 1);
                startActivity(intent8);
                break;
            case R.id.tv_healthhistoty:
                startActivityForResult(new Intent(this, HistoryDataActivity.class), 2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == EDIT_HEALTHY_RECORD) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        healthy_refresh.setRefreshing();
                    }
                }, 300);
            }
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        retestPre.GetUserMeasuredInfo(mobile);
    }

    @Override
    public void getModel(LaichModel laichModel) {
        try {
            healthy_refresh.onRefreshComplete();
            if (laichModel == null) {
                laichModel = new LaichModel();
            }
            tv_health_weight.setText(StringUtils.isEmpty(laichModel.getWeight()) ? "" : Float.parseFloat(laichModel.getWeight()) + "斤");
            tv_health_Pysical.setText(StringUtils.isEmpty(laichModel.getPysical()) ? "" : Float.parseFloat(laichModel.getPysical()) + "%");
            tv_health_fat.setText(StringUtils.isEmpty(laichModel.getFat()) ? "" : Float.parseFloat(laichModel.getFat()) + "");
            tv_health_circum.setText(StringUtils.isEmpty(laichModel.getCircum()) ? "" : Float.parseFloat(laichModel.getCircum()) + "cm");
            tv_health_waistline.setText(StringUtils.isEmpty(laichModel.getWaistline()) ? "" : Float.parseFloat(laichModel.getWaistline()) + "cm");
            tv_health_hiplie.setText(StringUtils.isEmpty(laichModel.getHiplie()) ? "" : Float.parseFloat(laichModel.getHiplie()) + "cm");
            tv_health_upArmGirth.setText(StringUtils.isEmpty(laichModel.getUpArmGirth()) ? "" : Float.parseFloat(laichModel.getUpArmGirth()) + "cm");
            tv_health_upLegGirth.setText(StringUtils.isEmpty(laichModel.getUpLegGirth()) ? "" : Float.parseFloat(laichModel.getUpLegGirth()) + "cm");
            tv_health_doLegGirth.setText(StringUtils.isEmpty(laichModel.getDoLegGirth()) ? "" : Float.parseFloat(laichModel.getDoLegGirth()) + "cm");
            String date = laichModel.getCreateDate();
            if (StringUtils.isEmpty(date)) {
                tv_healthdate.setText("");
                tv_healthtime.setText("");
                return;
            }
            DateUtil util = DateUtil.getInstance();
            int month = util.getMonth(date);
            int day = util.getDay(date);
            int hour = util.getHour(date);
            int minutes = util.getMinute(date);
            tv_healthdate.setText(month + "月" + day + "日");
            tv_healthtime.setText(hour + ":" + (minutes < 10 ? "0" + minutes : minutes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}