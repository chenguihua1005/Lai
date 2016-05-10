/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.sport.adapter.GroupAdapter;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.model.PraiseChallengeModel;
import com.softtek.lai.module.sport.model.SportMainModel;
import com.softtek.lai.module.sport.presenter.SportGroupManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 跑团首页
 */
@InjectLayout(R.layout.activity_group_main)
public class GroupMainActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetSportIndexCallBack {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_step)
    TextView text_step;

    @InjectView(R.id.text_rl)
    TextView text_rl;

    @InjectView(R.id.text_pm)
    TextView text_pm;

    @InjectView(R.id.text_xzs)
    TextView text_xzs;

    @InjectView(R.id.text_gxz)
    TextView text_gxz;

    @InjectView(R.id.text1)
    TextView text1;

    @InjectView(R.id.text2)
    TextView text2;

    @InjectView(R.id.lin_have_pk)
    LinearLayout lin_have_pk;

    @InjectView(R.id.lin_no_pk)
    LinearLayout lin_no_pk;

    @InjectView(R.id.text_pk_left_count)
    TextView text_pk_left_count;

    @InjectView(R.id.text_pk_right_count)
    TextView text_pk_right_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        SportGroupManager sportGroupManager = new SportGroupManager(this);
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        userId = "13";
        sportGroupManager.getSportIndex(userId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                startActivity(new Intent(this, HomeActviity.class));
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, HomeActviity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getSportIndex(String type, SportMainModel sportMainModel) {
        String TodayStepCnt = sportMainModel.getTodayStepCnt();
        if ("0".equals(TodayStepCnt)) {
            text_step.setText("-");
        } else {
            text_step.setText(sportMainModel.getTodayStepCnt());
        }
        String todayKaluliCnt = sportMainModel.getTodayKaluliCnt();
        if ("0".equals(todayKaluliCnt)) {
            text_rl.setText("-");
        } else {
            text_rl.setText(sportMainModel.getTodayKaluliCnt());
        }
        String todayStepOdr = sportMainModel.getTodayStepOdr();
        if ("0".equals(todayStepOdr)) {
            text_pm.setText("-");
        } else {
            text_pm.setText(sportMainModel.getTodayStepOdr());
        }
        String medalCnt = sportMainModel.getMedalCnt();
        if ("0".equals(medalCnt)) {
            text_xzs.setText("-");
        } else {
            text_xzs.setText(sportMainModel.getMedalCnt());
        }
        String donatenNum = sportMainModel.getDonatenNum();
        if ("0".equals(donatenNum)) {
            text2.setText("赶紧来贡献吧");
            text_gxz.setVisibility(View.GONE);
            text1.setVisibility(View.GONE);
        } else {
            text_gxz.setText(sportMainModel.getDonatenNum());
        }

        tv_title.setText(sportMainModel.getRGName());

        PraiseChallengeModel praiseChallengeModel = sportMainModel.getPraiseChallenge();
        if (praiseChallengeModel == null) {
            lin_no_pk.setVisibility(View.VISIBLE);
            lin_have_pk.setVisibility(View.GONE);
        }else {
            lin_no_pk.setVisibility(View.GONE);
            lin_have_pk.setVisibility(View.VISIBLE);

            text_pk_left_count.setText(praiseChallengeModel.getPCnt());
            text_pk_right_count.setText(praiseChallengeModel.getBPCnt());
        }
    }
}
