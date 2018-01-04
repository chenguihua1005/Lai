package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;

/**
 * Created by jia.lu on 12/29/2017.
 */

public class InviteMatchActivity extends MakiBaseActivity implements View.OnClickListener {

    private LinearLayout mBack;
    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_match);
        initData();
        initView();
    }

    private void initData(){

    }

    private void initView(){
        mBack = findViewById(R.id.ll_left);
        mTitle = findViewById(R.id.tv_title);
        mTitle.setText("参赛邀请");
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
