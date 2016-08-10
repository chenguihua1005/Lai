/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.view.HealthyRecordActivity;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.softtek.lai.module.historydate.view.HistoryDataActivity;
import com.softtek.lai.module.home.presenter.HealthyRecordManager;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy_record)
public class HealthyRecordFragment extends LazyBaseFragment implements View.OnClickListener, PullToRefreshScrollView.OnRefreshListener<ScrollView>
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

    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.but_login)
    Button but_login;
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
    protected void lazyLoad() {
        if (StringUtils.isEmpty(UserInfoModel.getInstance().getToken())) {
            lin_is_vr.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
            fl_right.setVisibility(View.INVISIBLE);

        } else {
            lin_is_vr.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            fl_right.setVisibility(View.VISIBLE);
            fl_right.setOnClickListener(this);
            //获取健康记录
            mobile = UserInfoModel.getInstance().getUser().getMobile();
            retestPre = new HealthyRecordManager(this);
            retestPre.GetUserMeasuredInfo(mobile);

        }
    }

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
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
        but_login.setOnClickListener(this);
        iv_email.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.healthedit));
        healthy_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        healthy_refresh.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录");


    }


    private static final int EDIT_HEALTHY_RECORD = 2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_right:
                startActivityForResult(new Intent(getContext(), HealthEntryActivity.class), 2);
                break;
            case R.id.tv_weight:
                Intent intent = new Intent(getContext(), HealthyRecordActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.tv_body_fat:
                Intent intent1 = new Intent(getContext(), HealthyRecordActivity.class);
                intent1.putExtra("id", 1);
                intent1.putExtra("flag", 1);
                startActivity(intent1);
                break;
            case R.id.tv_fat:
                Intent intent2 = new Intent(getContext(), HealthyRecordActivity.class);
                intent2.putExtra("id", 2);
                intent2.putExtra("flag", 1);
                startActivity(intent2);
                break;
            case R.id.tv_bust:
                Intent intent3 = new Intent(getContext(), HealthyRecordActivity.class);
                intent3.putExtra("id", 3);
                intent3.putExtra("flag", 1);
                startActivity(intent3);
                break;
            case R.id.tv_waistline:
                Intent intent4 = new Intent(getContext(), HealthyRecordActivity.class);
                intent4.putExtra("id", 4);
                intent4.putExtra("flag", 1);
                startActivity(intent4);
                break;
            case R.id.tv_hipline:
                Intent intent5 = new Intent(getContext(), HealthyRecordActivity.class);
                intent5.putExtra("id", 5);
                intent5.putExtra("flag", 1);
                startActivity(intent5);
                break;
            case R.id.tv_uphipline:
                Intent intent6 = new Intent(getContext(), HealthyRecordActivity.class);
                intent6.putExtra("id", 6);
                intent6.putExtra("flag", 1);
                startActivity(intent6);
                break;
            case R.id.tv_leg:
                Intent intent7 = new Intent(getContext(), HealthyRecordActivity.class);
                intent7.putExtra("id", 7);
                intent7.putExtra("flag", 1);
                startActivity(intent7);
                break;
            case R.id.tv_shin:
                Intent intent8 = new Intent(getContext(), HealthyRecordActivity.class);
                intent8.putExtra("id", 8);
                intent8.putExtra("flag", 1);
                startActivity(intent8);
                break;
            case R.id.tv_healthhistoty:
                startActivityForResult(new Intent(getContext(), HistoryDataActivity.class), 2);
                break;
            case R.id.but_login:
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
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
        Log.i("健康记录开始请求");
        retestPre.GetUserMeasuredInfo(mobile);
    }

    @Override
    public void getModel(LaichModel laichModel) {
        healthy_refresh.onRefreshComplete();
        try {
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
