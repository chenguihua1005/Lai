/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy_record)
public class HealthyRecordFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

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

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.GONE);
        tv_weight.setOnClickListener(this);
        tv_body_fat.setOnClickListener(this);
        tv_fat.setOnClickListener(this);
        tv_bust.setOnClickListener(this);
        tv_waistline.setOnClickListener(this);
        tv_hipline.setOnClickListener(this);
        tv_uphipline.setOnClickListener(this);
        tv_leg.setOnClickListener(this);
        tv_shin.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_weight:
                break;
            case R.id.tv_body_fat:
                break;
            case R.id.tv_fat:
                break;
            case R.id.tv_bust:
                break;
            case R.id.tv_waistline:
                break;
            case R.id.tv_hipline:
                break;
            case R.id.tv_uphipline:
                break;
            case R.id.tv_leg:
                break;
            case R.id.tv_shin:
                break;
        }
    }
}
