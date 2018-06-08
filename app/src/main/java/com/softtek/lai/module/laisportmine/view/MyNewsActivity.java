package com.softtek.lai.module.laisportmine.view;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.present.MyRunTeamManager;
import com.softtek.lai.module.message2.view.ActionActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_news)
public class MyNewsActivity extends BaseActivity implements View.OnClickListener, MyRunTeamManager.MyRunTeamCallback {

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
    @InjectView(R.id.tv_newslis_public)
    TextView tv_newslis_public;
    @InjectView(R.id.tv_newslis_pk)
    TextView tv_newslis_pk;
    @InjectView(R.id.tv_newslis_action)
    TextView tv_newslis_action;
    @InjectView(R.id.tv_newslis_system)
    TextView tv_newslis_system;
    RunTeamModel runTeamModels;
    MyRunTeamManager myRunTeamManager;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    String accountid = "";

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
        accountid = userInfoModel.getUser().getUserid();
        myRunTeamManager = new MyRunTeamManager(this);
        Intent intent = getIntent();
        runTeamModels = (RunTeamModel) intent.getSerializableExtra("runTeamModels");

    }

    @Override
    public void onResume() {
        super.onResume();
        myRunTeamManager.doGetNowRgName(Integer.parseInt(accountid));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.re_public_lab:
                startActivity(new Intent(this, MyPublicwelfareActivity.class));
                break;
            case R.id.Re_pk_lab:
                startActivity(new Intent(this, MyPkListActivity.class));
                break;
            case R.id.Re_action_lab:
                startActivity(new Intent(this, ActionActivity.class));
                break;
            case R.id.Re_systemnews_lab:
                startActivity(new Intent(this, MySystemActivity.class));
                break;


        }
    }


    @Override
    public void getRunTeamName(RunTeamModel runTeamModel) {
        try {
            if (runTeamModel != null) {
                runTeamModels = runTeamModel;
//                if (!runTeamModels.getIsHasAngelMsg().isEmpty()) {
//                    if (Integer.parseInt(runTeamModels.getIsHasAngelMsg()) > 0 && Integer.parseInt(runTeamModels.getIsHasAngelMsg()) <= 10) {
//                        tv_newslis_public.setText(runTeamModels.getIsHasAngelMsg());
//                        tv_newslis_public.setVisibility(View.VISIBLE);
//                    } else if (10 < Integer.parseInt(runTeamModels.getIsHasAngelMsg())) {
//                        tv_newslis_public.setText("10+");
//                        tv_newslis_public.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_newslis_public.setVisibility(View.GONE);
//                    }
//                }
//                if (!runTeamModels.getIsHasChaMsg().isEmpty()) {
//                    if (Integer.parseInt(runTeamModels.getIsHasChaMsg()) > 0 && Integer.parseInt(runTeamModels.getIsHasChaMsg()) <= 10) {
//                        tv_newslis_pk.setText(runTeamModels.getIsHasChaMsg());
//                        tv_newslis_pk.setVisibility(View.VISIBLE);
//                    } else if (10 < Integer.parseInt(runTeamModels.getIsHasChaMsg())) {
//                        tv_newslis_pk.setText("10+");
//                        tv_newslis_pk.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_newslis_pk.setVisibility(View.GONE);
//                    }
//                }
//                if (!runTeamModels.getIsHasActMsg().isEmpty()) {
//                    if (Integer.parseInt(runTeamModels.getIsHasActMsg()) > 0 && Integer.parseInt(runTeamModels.getIsHasActMsg()) <= 10) {
//                        tv_newslis_action.setText(runTeamModels.getIsHasActMsg());
//                        tv_newslis_action.setVisibility(View.VISIBLE);
//                    } else if (10 < Integer.parseInt(runTeamModels.getIsHasActMsg())) {
//                        tv_newslis_action.setText("10+");
//                        tv_newslis_action.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_newslis_action.setVisibility(View.GONE);
//                    }
//                }
//                if (!runTeamModels.getIsHasSysMsg().isEmpty()) {
//                    if (Integer.parseInt(runTeamModels.getIsHasSysMsg()) > 0 && Integer.parseInt(runTeamModels.getIsHasSysMsg()) <= 10) {
//                        tv_newslis_system.setText(runTeamModels.getIsHasSysMsg());
//                        tv_newslis_system.setVisibility(View.VISIBLE);
//                    } else if (10 < Integer.parseInt(runTeamModels.getIsHasSysMsg())) {
//                        tv_newslis_system.setText("10+");
//                        tv_newslis_system.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_newslis_system.setVisibility(View.GONE);
//                    }
//                }
            }
        }catch (Exception e){e.printStackTrace();}

    }
}
