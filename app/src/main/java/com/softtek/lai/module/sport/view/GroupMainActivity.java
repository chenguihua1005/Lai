/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.view;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.act.view.ActListActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.laisportmine.present.MyRunTeamManager;
import com.softtek.lai.module.laisportmine.view.MyInformationActivity;
import com.softtek.lai.module.mygrades.view.MyGradesActivity;
import com.softtek.lai.module.personalPK.view.CreatePKActivity;
import com.softtek.lai.module.personalPK.view.PKListActivity;
import com.softtek.lai.module.sport.adapter.GroupAdapter;
import com.softtek.lai.module.sport.adapter.GroupMainActiuvityAdapter;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.model.PraiseChallengeModel;
import com.softtek.lai.module.sport.model.RecentlyActiviteModel;
import com.softtek.lai.module.sport.model.SportMainModel;
import com.softtek.lai.module.sport.presenter.SportGroupManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 跑团首页
 */
@InjectLayout(R.layout.activity_group_main)
public class GroupMainActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetSportIndexCallBack, MyRunTeamManager.MyRunTeamCallback {

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

    @InjectView(R.id.text3)
    TextView text3;

    @InjectView(R.id.lin_have_pk)
    LinearLayout lin_have_pk;

    @InjectView(R.id.lin_no_pk)
    LinearLayout lin_no_pk;

    @InjectView(R.id.text_pk_left_count)
    TextView text_pk_left_count;

    @InjectView(R.id.text_pk_right_count)
    TextView text_pk_right_count;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.rel_my_score)
    RelativeLayout rel_my_score;

    MyRunTeamManager myRunTeamManager;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    long accountid = Long.parseLong(userInfoModel.getUser().getUserid());

    PraiseChallengeModel praiseChallengeModel;

    List<RecentlyActiviteModel> recentlyActivite;

    @InjectView(R.id.text_pk_left_name)
    TextView text_pk_left_name;

    @InjectView(R.id.text_pk_right_name)
    TextView text_pk_right_name;

    @InjectView(R.id.text_pk_time)
    TextView text_pk_time;

    @InjectView(R.id.img_pk_type)
    ImageView img_pk_type;

    @InjectView(R.id.img_right)
    ImageView img_right;

    @InjectView(R.id.img_left)
    ImageView img_left;

    @InjectView(R.id.list_activity)
    ListView list_activity;

    @InjectView(R.id.lin_no_activity)
    TextView lin_no_activity;

    @InjectView(R.id.lin_start_sport)
    LinearLayout lin_start_sport;

    @InjectView(R.id.lin_start_sports)
    LinearLayout lin_start_sports;

    @InjectView(R.id.rel_my_activity)
    RelativeLayout rel_my_activity;

    @InjectView(R.id.rel_my_pk)
    RelativeLayout rel_my_pk;

    @InjectView(R.id.lin_pk)
    LinearLayout lin_pk;

    @InjectView(R.id.text_start_pk)
    TextView text_start_pk;

    @InjectView(R.id.text_start_pks)
    TextView text_start_pks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rel_my_score.setOnClickListener(this);
        lin_start_sports.setOnClickListener(this);
        lin_start_sport.setOnClickListener(this);
        rel_my_activity.setOnClickListener(this);
        rel_my_pk.setOnClickListener(this);
        lin_pk.setOnClickListener(this);
        text_start_pk.setOnClickListener(this);
        text_start_pks.setOnClickListener(this);
        lin_no_pk.setOnClickListener(this);
        lin_no_activity.setOnClickListener(this);
        list_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentlyActiviteModel recentlyActiviteModel = recentlyActivite.get(position);
                String actId=recentlyActiviteModel.getActId();//活动Id
            }
        });
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
        //判断是否有跑团
        myRunTeamManager = new MyRunTeamManager(this);
        myRunTeamManager.doGetNowRgName(accountid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                startActivity(new Intent(this, HomeActviity.class));
                break;
            case R.id.fl_right:
            case R.id.iv_email:
                startActivity(new Intent(this, MyInformationActivity.class));
                break;
            case R.id.rel_my_score://我的成绩
                startActivity(new Intent(this, MyGradesActivity.class));
                break;
            case R.id.lin_start_sport://开始运动
            case R.id.lin_start_sports://开始运动

                break;
            case R.id.lin_no_activity://活动
            case R.id.rel_my_activity://活动
                startActivity(new Intent(this, ActListActivity.class));
                break;

            case R.id.rel_my_pk://PK挑战列表
                startActivity(new Intent(this, PKListActivity.class));
                break;

            case R.id.lin_pk://pk详情
                String id = praiseChallengeModel.getPKId();//PKId
                break;

            case R.id.text_start_pk://开始PK
            case R.id.text_start_pks://开始PK
            case R.id.lin_no_pk://开始PK
                startActivity(new Intent(this, CreatePKActivity.class));
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
            text_step.setText("--");
        } else {
            text_step.setText(sportMainModel.getTodayStepCnt());
        }
        String todayKaluliCnt = sportMainModel.getTodayKaluliCnt();
        if ("0".equals(todayKaluliCnt)) {
            text_rl.setText("--");
            text3.setVisibility(View.GONE);
        } else {
            text3.setVisibility(View.VISIBLE);
            text_rl.setText(sportMainModel.getTodayKaluliCnt());
        }
        String todayStepOdr = sportMainModel.getTodayStepOdr();
        if ("0".equals(todayStepOdr)) {
            text_pm.setText("--");
        } else {
            text_pm.setText(sportMainModel.getTodayStepOdr());
        }
        String medalCnt = sportMainModel.getMedalCnt();
        if ("0".equals(medalCnt)) {
            text_xzs.setText("--");
        } else {
            text_xzs.setText(sportMainModel.getMedalCnt());
        }

        text_gxz.setText(sportMainModel.getDonatenNum());

        tv_title.setText(sportMainModel.getRGName());

        praiseChallengeModel = sportMainModel.getPraiseChallenge();

        if (praiseChallengeModel.getPKId() == null) {
            lin_no_pk.setVisibility(View.VISIBLE);
            lin_have_pk.setVisibility(View.GONE);
        } else {
            lin_no_pk.setVisibility(View.GONE);
            lin_have_pk.setVisibility(View.VISIBLE);

            text_pk_left_count.setText(praiseChallengeModel.getPCnt());
            text_pk_right_count.setText(praiseChallengeModel.getBPCnt());
            text_pk_left_name.setText(praiseChallengeModel.getUserName());
            text_pk_right_name.setText(praiseChallengeModel.getBUserName());
            String chipType = praiseChallengeModel.getChipType();
            if ("0".equals(chipType)) {
                img_pk_type.setImageResource(R.drawable.img_group_main_1);
            } else if ("1".equals(chipType)) {
                img_pk_type.setImageResource(R.drawable.img_group_main_2);
            } else if ("2".equals(chipType)) {
                img_pk_type.setImageResource(R.drawable.img_group_main_3);
            }
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
            if ("".equals(praiseChallengeModel.getUserPhoto()) || "null".equals(praiseChallengeModel.getUserPhoto()) || praiseChallengeModel.getUserPhoto() == null) {
                Picasso.with(this).load("111").fit().error(R.drawable.img_default).into(img_left);
            } else {
                Picasso.with(this).load(path + praiseChallengeModel.getUserPhoto()).fit().error(R.drawable.img_default).into(img_left);
            }
            if ("".equals(praiseChallengeModel.getBPhoto()) || "null".equals(praiseChallengeModel.getBPhoto()) || praiseChallengeModel.getBPhoto() == null) {
                Picasso.with(this).load("111").fit().error(R.drawable.img_default).into(img_right);
            } else {
                Picasso.with(this).load(path + praiseChallengeModel.getBPhoto()).fit().error(R.drawable.img_default).into(img_right);
            }
            String start = praiseChallengeModel.getStart();
            String end = praiseChallengeModel.getEnd();

            String start_time = "";
            String end_time = "";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号");
            try {
                Date start_date = sdf.parse(start);
                Date end_date = sdf.parse(end);
                start_time = format.format(start_date);
                end_time = format.format(end_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            text_pk_time.setText(start_time + " - " + end_time);
        }
        recentlyActivite = sportMainModel.getRecentlyActivite();
        if (recentlyActivite.size() == 0) {
            lin_no_activity.setVisibility(View.VISIBLE);
            list_activity.setVisibility(View.GONE);
        } else {
            lin_no_activity.setVisibility(View.GONE);
            list_activity.setVisibility(View.VISIBLE);
            GroupMainActiuvityAdapter adapter = new GroupMainActiuvityAdapter(this, recentlyActivite);
            list_activity.setAdapter(adapter);
        }
    }

    @Override
    public void getRunTeamName(String data, String flag) {
        if (!data.equals("")) {
            iv_email.setVisibility(View.VISIBLE);
            iv_email.setImageResource(R.drawable.img_group_main_my);
        }

    }
}
