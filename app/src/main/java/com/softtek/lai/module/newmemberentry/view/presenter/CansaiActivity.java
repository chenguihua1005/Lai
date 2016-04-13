package com.softtek.lai.module.newmemberentry.view.presenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;

public class CansaiActivity extends BaseActivity {

    //toolbar
    //标题
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    //跳过按钮
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cansai);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("报名参赛");
    }
}
