package com.softtek.lai.module.health.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.health.presenter.HealthyRecordImpl;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_weight)
public class WeightActivity extends BaseActivity implements HealthyRecordImpl.HealthyRecordCallback{

    private HealthyRecordImpl healthyRecord;


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
