package com.softtek.lai.module.personalPK.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_time)
public class SelectTimeActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.rl_start)
    RelativeLayout rl_start;
    @InjectView(R.id.rl_end)
    RelativeLayout rl_end;
    @InjectView(R.id.tv_start)
    TextView tv_start;
    @InjectView(R.id.tv_end)
    TextView tv_end;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_start.setOnClickListener(this);
        rl_end.setOnClickListener(this);
        tv_title.setText("选择PK挑战时间");
        tv_right.setText("保存");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //保存
                break;
            case R.id.rl_start:

                break;
            case R.id.rl_end:
                break;

        }
    }
}
