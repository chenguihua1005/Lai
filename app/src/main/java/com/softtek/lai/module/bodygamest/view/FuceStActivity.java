/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.os.Bundle;
import android.widget.LinearLayout;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_fuce_st)
public class FuceStActivity extends BaseActivity {

    //保存数据点击
    //初始体重
    @InjectView(R.id.ll_fucest_chu_weight)
    LinearLayout ll_fucest_chu_weight;
    //现在体重
    @InjectView(R.id.ll_fucest_nowweight)
    LinearLayout ll_fucest_nowweight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
