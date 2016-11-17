package com.softtek.lai.module.bodygame3.activity;

import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//创建活动
@InjectLayout(R.layout.activity_create)
public class CreateActivity extends BaseActivity {
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void initViews() {
        tv_title.setText("新建活动");
        tv_right.setText("确定");
    }

    @Override
    protected void initDatas() {

    }
}
