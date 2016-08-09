/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.view;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.forlong401.log.transaction.log.manager.LogManager;
import com.forlong401.log.transaction.utils.LogUtils;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.act.view.ActListActivity;
import com.softtek.lai.module.group.adapter.GroupMainActiuvityAdapter;
import com.softtek.lai.module.group.model.MineResultModel;
import com.softtek.lai.module.group.model.PraiseChallengeModel;
import com.softtek.lai.module.group.model.RecentlyActiviteModel;
import com.softtek.lai.module.group.model.SportMainModel;
import com.softtek.lai.module.group.presenter.SportGroupManager;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.laisportmine.view.MyInformationActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.mygrades.view.MyGradesActivity;
import com.softtek.lai.module.personalPK.view.CreatePKActivity;
import com.softtek.lai.module.personalPK.view.PKDetailActivity;
import com.softtek.lai.module.personalPK.view.PKListActivity;
import com.softtek.lai.module.sport.view.StartSportActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 跑团首页
 */
@InjectLayout(R.layout.activity_group_main)
public class GroupMainActivity extends BaseActivity implements View.OnClickListener, SportGroupManager.GetSportIndexCallBack
        , PullToRefreshBase.OnRefreshListener<ScrollView> ,Handler.Callback{

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

    @InjectView(R.id.text_left_1)
    TextView text_left_1;

    @InjectView(R.id.text_right_2)
    TextView text_right_2;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.rel_my_score)
    RelativeLayout rel_my_score;

    @InjectView(R.id.lin_reflash)
    LinearLayout lin_reflash;

    @InjectView(R.id.pull_sroll)
    PullToRefreshScrollView pull_sroll;

    UserModel model;
    SportGroupManager sportGroupManager;
    String userId;

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

    @InjectView(R.id.lin1)
    LinearLayout lin1;
    @InjectView(R.id.lin2)
    LinearLayout lin2;
    @InjectView(R.id.lin3)
    LinearLayout lin3;
    @InjectView(R.id.lin4)
    LinearLayout lin4;


    private static int currentStep;
    private static int serverStep;
    private static final int REQUEST_DELAY=3;
    private Handler delayHandler=new Handler(this);
    private static int deviation=0;
    private Messenger clientMessenger;
    //用来接收服务端发送过来的信息使用
    private Messenger getReplyMessage=new Messenger(delayHandler);
    @Override
    public boolean handleMessage(Message msg) {
        //在这里获取服务端发来的信息
        switch (msg.what){
            case StepService.MSG_FROM_SERVER:
                //获取数据
                Bundle data=msg.getData();
                currentStep=data.getInt("todayStep",0);
                serverStep=data.getInt("serverStep",0);
                LogManager.getManager(getApplicationContext())
                        .log("GroupMainActivity","currentStep="+currentStep+";serverStep="+serverStep, LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                //更新显示
                try {
                    if (currentStep == 0) {
                        text_step.setText("--");
                        text_rl.setText("--");
                        text3.setVisibility(View.GONE);
                    } else {
                        text_step.setText(currentStep + "");
                        text3.setVisibility(View.VISIBLE);
                        int kaluli = currentStep / 35;
                        text_rl.setText(kaluli + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //延迟在一次向服务端请求
                delayHandler.sendEmptyMessageDelayed(REQUEST_DELAY,400);
                break;
            case REQUEST_DELAY:
                //继续向服务端发送请求获取数据
                Message message = Message.obtain(null, StepService.MSG_FROM_CLIENT);
                //携带服务器上的步数
                LogManager.getManager(getApplicationContext())
                        .log("GroupMainActivity","request Step, now deviation="+deviation, LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                if (deviation>0){
                    int deviationTemp=deviation;
                    Bundle surplusStep = new Bundle();
                    surplusStep.putInt("surplusStep",deviationTemp);
                    message.setData(surplusStep);
                    LogManager.getManager(getApplicationContext())
                            .log("GroupMainActivity","has deviation value,now deviation="+deviation, LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                }
                deviation=0;
                message.replyTo = getReplyMessage;
                try {
                    clientMessenger.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    LogManager.getManager(getApplicationContext())
                            .log("GroupMainActivity","bindService successfully.......", LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                }

                break;
        }
        return false;
    }

    //用于绑定服务使用
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当连接开始时向服务端发起一个信使，然后服务端会拿到这个信使并返回一个数据
            clientMessenger=new Messenger(service);
            Message message=Message.obtain(null,StepService.MSG_FROM_CLIENT);
            message.replyTo=getReplyMessage;
            try {
                clientMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            LogManager.getManager(getApplicationContext())
                    .log("GroupMainActivity","bindService successfully.......", LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogManager.getManager(getApplicationContext())
                    .log("GroupMainActivity","unbindService successfully.......", LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        }
    };

    @Override
    protected void initViews() {
        iv_email.setImageResource(R.drawable.img_group_main_my);
        pull_sroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pull_sroll.setOnRefreshListener(this);
        ll_left.setOnClickListener(this);
        lin1.setOnClickListener(this);
        lin2.setOnClickListener(this);
        lin3.setOnClickListener(this);
        lin4.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rel_my_score.setOnClickListener(this);
        lin_reflash.setOnClickListener(this);
        lin_start_sports.setOnClickListener(this);
        lin_start_sport.setOnClickListener(this);
        rel_my_activity.setOnClickListener(this);
        rel_my_pk.setOnClickListener(this);
        lin_pk.setOnClickListener(this);
        text_start_pk.setOnClickListener(this);
        text_start_pks.setOnClickListener(this);
        lin_no_pk.setOnClickListener(this);
        lin_no_activity.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //绑定服务
        bindService(new Intent(this,StepService.class),connection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解绑服务
        deviation=0;
        delayHandler.removeMessages(REQUEST_DELAY);
        unbindService(connection);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        userId = UserInfoModel.getInstance().getUser().getUserid();
        String str = DateUtil.getInstance().getCurrentDate() + "," + (currentStep==0? SharedPreferenceService.getInstance().get("currentStep",0):currentStep);
        sportGroupManager.getSportIndex(userId, str);
        sportGroupManager.getNewMsgRemind(userId);
    }

    @Override
    protected void initDatas() {
        sportGroupManager = new SportGroupManager(this);
        list_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentlyActiviteModel recentlyActiviteModel = recentlyActivite.get(position);
                String actId = recentlyActiviteModel.getActId();//活动Id
                Intent intent = new Intent(GroupMainActivity.this, ActActivity.class);
                intent.putExtra("id", actId);
                startActivity(intent);

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pull_sroll.setRefreshing();
            }
        }, 600);
    }

    @Override
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        if (model != null) {
            sportGroupManager.getNewMsgRemind(model.getUserid());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_reflash:
                dialogShow("加载中");
                String str = DateUtil.getInstance().getCurrentDate() + "," + currentStep;
                sportGroupManager.getMineResult(userId, str);
                break;
            case R.id.ll_left:
                finish();
                Intent inten=new Intent(this, HomeActviity.class);
                startActivity(inten);
                break;

            case R.id.fl_right:
            case R.id.iv_email:
                startActivity(new Intent(this, MyInformationActivity.class));
                break;
            case R.id.lin1://我的成绩
            case R.id.lin2://我的成绩
            case R.id.lin3://我的成绩
            case R.id.lin4://我的成绩
            case R.id.rel_my_score://我的成绩
                startActivity(new Intent(this, MyGradesActivity.class));
                break;
            case R.id.lin_start_sport://开始运动
            case R.id.lin_start_sports://开始运动
                startActivity(new Intent(GroupMainActivity.this, StartSportActivity.class));
                break;
            case R.id.lin_no_activity://活动
            case R.id.rel_my_activity://活动
                startActivity(new Intent(this, ActListActivity.class));
                break;

            case R.id.rel_my_pk://PK挑战列表
                startActivity(new Intent(this, PKListActivity.class));
                break;

            case R.id.lin_pk://pk详情
                if (praiseChallengeModel != null) {
                    String id = praiseChallengeModel.getPKId();//PKId
                    Intent intent = new Intent(this, PKDetailActivity.class);
                    intent.putExtra("pkId", Long.parseLong(id));
                    intent.putExtra("pkType", Constants.GROUPMAIN_PK);
                    startActivityForResult(intent, 100);
                }
                break;

            case R.id.text_start_pk://开始PK
            case R.id.text_start_pks://开始PK
            case R.id.lin_no_pk://开始PK
                startActivity(new Intent(this, CreatePKActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            String chp = data.getStringExtra("ChP");
            String bchp = data.getStringExtra("BChP");
            text_pk_left_count.setText(StringUtils.isEmpty(chp) ? "0" : chp);
            text_pk_right_count.setText(StringUtils.isEmpty(bchp) ? "0" : bchp);

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            Intent inten = new Intent(this, HomeActviity.class);
            startActivity(inten);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getSportIndex(String type, SportMainModel sportMainModel) {
        try {
            deviation=0;
            pull_sroll.onRefreshComplete();
            if ("success".equals(type)) {
                String TodayStepCnt = sportMainModel.getTodayStepCnt();
                if ("0".equals(TodayStepCnt)) {
                    text_step.setText("--");
                    text_rl.setText("--");
                    text3.setVisibility(View.GONE);
                    text_pm.setText("--");
                } else {
                    //如果本次同步发现服务器上的步数比本地还多则计算误差并显示
                    int currentTemp=currentStep;
                    int tempStep=Integer.parseInt(TodayStepCnt);
                    if(tempStep-currentTemp>0){
                        //用服务器上的步数减去本地第一次同步的服务器上的步数获取误差值
                        deviation=tempStep-serverStep;
                    }
                    LogManager.getManager(getApplicationContext())
                            .log("GroupMainActivity","pull down request,\n" +
                                    " currentTemp="+currentTemp+"\n" +
                                    " serverStep="+serverStep+"\n, now deviation="+deviation, LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                    text_step.setText(TodayStepCnt);
                    text3.setVisibility(View.VISIBLE);
                    int kaluli = Integer.parseInt(sportMainModel.getTodayStepCnt()) / 35;
                    text_rl.setText(kaluli + "");
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
                if (praiseChallengeModel != null && praiseChallengeModel.getPKId() == null) {
                    lin_no_pk.setVisibility(View.VISIBLE);
                    lin_have_pk.setVisibility(View.GONE);
                } else {
                    lin_no_pk.setVisibility(View.GONE);
                    lin_have_pk.setVisibility(View.VISIBLE);
                    text_pk_left_count.setText(praiseChallengeModel.getPCnt());
                    text_pk_right_count.setText(praiseChallengeModel.getBPCnt());
                    text_left_1.setText(praiseChallengeModel.getPCnt());
                    text_right_2.setText(praiseChallengeModel.getBPCnt());
                    text_pk_left_name.setText(StringUtil.showName(praiseChallengeModel.getUserName(), praiseChallengeModel.getMobile()));
                    text_pk_right_name.setText(StringUtil.showName(praiseChallengeModel.getBUserName(), praiseChallengeModel.getBMobile()));
                    String chipType = praiseChallengeModel.getChipType();
                    if ("0".equals(chipType)) {
                        img_pk_type.setImageResource(R.drawable.img_group_main_1);
                    } else if ("1".equals(chipType)) {
                        img_pk_type.setImageResource(R.drawable.img_group_main_2);
                    } else if ("2".equals(chipType)) {
                        img_pk_type.setImageResource(R.drawable.img_group_main_3);
                    }
                    String path = AddressManager.get("photoHost");
                    if ("".equals(praiseChallengeModel.getUserPhoto()) || "null".equals(praiseChallengeModel.getUserPhoto()) || praiseChallengeModel.getUserPhoto() == null) {
                        Picasso.with(this).load(R.drawable.img_default).into(img_left);
                    } else {
                        Picasso.with(this).load(path + praiseChallengeModel.getUserPhoto()).fit().error(R.drawable.img_default).into(img_left);
                    }
                    if ("".equals(praiseChallengeModel.getBPhoto()) || "null".equals(praiseChallengeModel.getBPhoto()) || praiseChallengeModel.getBPhoto() == null) {
                        Picasso.with(this).load(R.drawable.img_default).into(img_right);
                    } else {
                        Picasso.with(this).load(path + praiseChallengeModel.getBPhoto()).fit().error(R.drawable.img_default).into(img_right);
                    }
                    String start = praiseChallengeModel.getStart();
                    String end = praiseChallengeModel.getEnd();

                    String start_time = "";
                    String end_time = "";

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMineResult(String type, MineResultModel model) {
        dialogDissmiss();
        try {
            if ("success".equals(type)) {
                String TodayStepCnt = model.getTodayStepCnt();
                if ("0".equals(TodayStepCnt)) {
                    text3.setVisibility(View.GONE);
                    text_step.setText("--");
                    text_rl.setText("--");
                    text_pm.setText("--");
                } else {
                    //如果本次同步发现服务器上的步数比本地还多则计算误差并显示
                    int currentTemp=currentStep;
                    int tempStep=Integer.parseInt(TodayStepCnt);
                    if(tempStep-currentTemp>0){
                        //用服务器上的步数减去本地第一次同步的服务器上的步数获取误差值
                        deviation=tempStep-serverStep;
                    }
                    text3.setVisibility(View.VISIBLE);
                    text_step.setText(model.getTodayStepCnt());
                    int kaluli = Integer.parseInt(model.getTodayStepCnt()) / 35;
                    text_rl.setText(kaluli + "");
                    text_pm.setText(model.getTodayStepOdr());
                }
                String medalCnt = model.getMedalCnt();
                if ("0".equals(medalCnt)) {
                    text_xzs.setText("--");
                } else {
                    text_xzs.setText(model.getMedalCnt());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getNewMsgRemind(String type) {
        try {
            if ("success".equals(type)) {
                iv_email.setImageResource(R.drawable.img_message_have);
            } else {
                iv_email.setImageResource(R.drawable.img_message_none);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
