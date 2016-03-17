package com.softtek.lai.module.StudentDetail.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_detail)
public class StudentDetailActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("学员详情");
        tv_left.setBackgroundResource(R.drawable.back_h);
        tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15), DisplayUtil.dip2px(this,20)));
    }

    @Override
    public void onClick(View v) {

    }
}
