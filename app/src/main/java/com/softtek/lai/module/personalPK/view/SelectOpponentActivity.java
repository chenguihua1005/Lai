package com.softtek.lai.module.personalPK.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.personalPK.model.PKCreatModel;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_select_opponent)
public class SelectOpponentActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;


    @InjectView(R.id.ll_search)
    LinearLayout ll_search;

    PKCreatModel model;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        tv_title.setText("选择PK挑战对手");
    }

    @Override
    protected void initDatas() {
        model=getIntent().getParcelableExtra("pkmodel");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_search:
                //跳转至搜索界面
                Intent pk=new Intent(this,SearchActivity.class);
                pk.putExtra("pkmodel",getIntent().getParcelableExtra("pkmodel"));
                startActivity(pk);
                break;
        }
    }
}
