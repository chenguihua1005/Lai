package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.MyPublicWewlListManager;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_news)
public class MyNewsActivity extends BaseActivity implements View.OnClickListener {

@InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.re_public_lab)
    RelativeLayout re_public_lab;
    @InjectView(R.id.Re_pk_lab)
    RelativeLayout Re_pk_lab;
    @InjectView(R.id.Re_action_lab)
    RelativeLayout Re_action_lab;
    @InjectView(R.id.Re_systemnews_lab)
    RelativeLayout Re_systemnews_lab;
    @Override
    protected void initViews() {
        tv_title.setText("我的消息");
        ll_left.setOnClickListener(this);
        re_public_lab.setOnClickListener(this);
        Re_pk_lab.setOnClickListener(this);
        Re_action_lab.setOnClickListener(this);
        Re_systemnews_lab.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.re_public_lab:
                startActivity(new Intent(this,MyPublicwelfareActivity.class));
                break;
            case R.id.Re_pk_lab:
                startActivity(new Intent(this,MyPublicwelfareActivity.class));
                break;
            case R.id.Re_action_lab:
                startActivity(new Intent(this,MyActionListActivity.class));
                break;
            case R.id.Re_systemnews_lab:
                startActivity(new Intent(this,MyPublicwelfareActivity.class));
                break;


        }
    }




}
