/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.bodygamest.model.CountWeekModel;
import com.softtek.lai.module.bodygamest.model.HasClass;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.lossweightstory.view.LossWeightStoryActivity;
import com.softtek.lai.module.message.presenter.IMessagePresenter;
import com.softtek.lai.module.message.presenter.MessageImpl;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.module.studentbasedate.view.StudentBaseDateActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_student)
public class BodyGamePCActivity extends BaseActivity implements View.OnClickListener {
    private ITiGuanSai tiGuanSai;
    private StudentImpl studentImpl;

    //标题
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_st_num)
    TextView tv_st_num;
    @InjectView(R.id.iv_st_adv)
    ImageView iv_st_adv;
    @InjectView(R.id.tv_totalpersonst)
    TextView tv_totalpersonst;
    @InjectView(R.id.tv_total_lossst)
    TextView tv_total_lossst;
    //点击事件
    //基本数据
    @InjectView(R.id.ll_st_jibenshuju)
    LinearLayout ll_st_jibenshuju;
    //上传照片
    @InjectView(R.id.ll_st_shangchuan)
    LinearLayout ll_st_shangchuan;
    //复测
    @InjectView(R.id.ll_st_fuce)
    LinearLayout ll_st_fuce;
    //减重故事
    @InjectView(R.id.ll_st_jianzhong)
    LinearLayout ll_st_jianzhong;
    //成绩单
    @InjectView(R.id.ll_st_chengjidan)
    LinearLayout ll_st_chengjidan;
    //荣誉榜
    @InjectView(R.id.ll_st_rongyu)
    LinearLayout ll_st_rongyu;
    //大赛赛况
    @InjectView(R.id.ll_st_saikuang)
    LinearLayout ll_st_saikuang;
    //提示
    @InjectView(R.id.ll_st_tipst)
    LinearLayout ll_st_tipst;
    //刷新
    @InjectView(R.id.im_refreshst)
    ImageView im_refreshst;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.ll_st_review)
    LinearLayout ll_st_review;
    private IMessagePresenter messagePresenter;
    private MessageReceiver mMessageReceiver;

    RetestPre retestPre;
    IStudentPresenter iStudentPresenter;
    UserInfoModel userInfoModel = UserInfoModel.getInstance();
    long loginid = Long.parseLong(userInfoModel.getUser().getUserid());
    boolean flag = true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //点击事件监听
        ll_st_jibenshuju.setOnClickListener(this);
        ll_st_shangchuan.setOnClickListener(this);
        ll_st_fuce.setOnClickListener(this);
        ll_st_jianzhong.setOnClickListener(this);
        ll_st_chengjidan.setOnClickListener(this);
        ll_st_rongyu.setOnClickListener(this);
        ll_st_saikuang.setOnClickListener(this);
        ll_st_tipst.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_st_review.setOnClickListener(this);
        tintManager.setStatusBarAlpha(0);
        messagePresenter = new MessageImpl(this);
        registerMessageReceiver();
        iv_email.setBackgroundResource(R.drawable.email);
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);

    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                img_red.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        im_refreshst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("数据刷新中...");
                progressDialog.show();
                tiGuanSai.doGetTotal(progressDialog);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model==null){
            return;
        }
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {

        } else {
            messagePresenter.getMessageRead(img_red);
        }

        studentImpl.hasClass(new RequestCallback<ResponseData<HasClass>>() {
            @Override
            public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                dialogDissmiss();
                Log.i(hasClassResponseData.toString());
                if (hasClassResponseData.getStatus() == 200) {
                    if ("1".equals(hasClassResponseData.getData().getIsHave())) {
                        retestPre.doGetAudit(loginid, 0, "");
                        iStudentPresenter.GetNotMeasuredRecordByPC(loginid);
                    }
                }

            }
        });
    }


    public static final int Student_reteset = 1;

    @Override
    protected void initDatas() {
        iStudentPresenter = new StudentImpl(this);
        tiGuanSai = new TiGuanSaiImpl();
        tiGuanSai.getTiGuanSai();
        tiGuanSai.doGetTotal(progressDialog);
        studentImpl = new StudentImpl(this);
        retestPre = new RetestclassImp();


        studentImpl.hasClass(new RequestCallback<ResponseData<HasClass>>() {
            @Override
            public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                dialogDissmiss();
                Log.i(hasClassResponseData.toString());
                if (hasClassResponseData.getStatus() == 200) {
                    if ("1".equals(hasClassResponseData.getData().getIsHave())) {
                        retestPre.doGetAudit(loginid, 0, "");
                        iStudentPresenter.GetNotMeasuredRecordByPC(loginid);
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });


    }

    @Subscribe
    public void doGetDates(RetestAuditModelEvent retestAuditModelEvent) {
        if (retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus() != null) {
            if (retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus().equals("0")) {
                flag = false;

            }
        }

    }

    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai) {

        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.default_icon_rect).fit().error(R.drawable.default_icon_rect).into(iv_st_adv);


    }

    @Subscribe
    public void onEvent1(CountWeekModel countWeekModel) {
        if (countWeekModel != null && !countWeekModel.toString().isEmpty()) {
            if (Integer.parseInt(countWeekModel.getCount()) > 10) {
                tv_st_num.setVisibility(View.VISIBLE);
                tv_st_num.setText("10+");
            } else if (Integer.parseInt(countWeekModel.getCount()) <= 10 && 0 < Integer.parseInt(countWeekModel.getCount())) {
                tv_st_num.setVisibility(View.VISIBLE);
                tv_st_num.setText(countWeekModel.getCount());
            } else {
                tv_st_num.setVisibility(View.GONE);
            }
        }


    }

    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels) {
        tv_totalpersonst.setText(totolModels.get(0).getTotal_person());
        tv_total_lossst.setText(totolModels.get(0).getTotal_loss());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id != R.id.ll_st_saikuang && id != R.id.ll_st_tipst && id != R.id.ll_left
                && id != R.id.fl_right && id != R.id.iv_email && id != R.id.ll_st_rongyu
                &&id!=R.id.ll_st_review) {
            dialogShow("检查中...");
            studentImpl.hasClass(new RequestCallback<ResponseData<HasClass>>() {
                @Override
                public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                    dialogDissmiss();
                    Log.i(hasClassResponseData.toString());
                    if (hasClassResponseData.getStatus() == 200) {
                        if ("1".equals(hasClassResponseData.getData().getIsHave())) {
                            doStartActivity(id);

                        } else if (Integer.parseInt(hasClassResponseData.getData().getIsHave()) == 0) {
                            //学员没有班级
                            new AlertDialog.Builder(BodyGamePCActivity.this).setTitle("温馨提示")
                                    .setMessage("您目前还没有参加班级").create().show();
                        } else if (Integer.parseInt(hasClassResponseData.getData().getIsHave()) == 2) {
                            //学员没有班级
                            new AlertDialog.Builder(BodyGamePCActivity.this).setTitle("温馨提示")
                                    .setMessage("班级尚未开始").create().show();
                        }
                    } else {
                        Util.toastMsg(hasClassResponseData.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialogDissmiss();
                    super.failure(error);
                }
            });
        } else {
            switch (id) {
                //大赛赛况
                case R.id.ll_st_saikuang:
                    startActivity(new Intent(this, GameActivity.class));
                    break;
                //提示
                case R.id.ll_st_tipst:
                    startActivity(new Intent(this, TipsActivity.class));
                    break;
                //往期回顾
                case R.id.ll_st_review:
                    new AlertDialog.Builder(this).setMessage("功能开发中敬请期待").create().show();
                    //startActivity(new Intent(this, ClassListActivity.class));
                    break;
                case R.id.ll_left:
                    String type = getIntent().getStringExtra("type");
                    if ("0".equals(type)) {
                        Intent inten=new Intent(this, HomeActviity.class);
                        startActivity(inten);
                    } else {
                        finish();
                    }
                    break;
                case R.id.ll_st_rongyu:
                    //荣誉榜
                    dialogShow("检查中...");
                    studentImpl.hasClass2(new RequestCallback<ResponseData<HasClass>>() {
                        @Override
                        public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                            dialogDissmiss();
                            if (hasClassResponseData.getStatus() == 200) {
                                int ishave=Integer.parseInt(hasClassResponseData.getData().getIsHave());
                                if(ishave==0){
                                    //学员没有班级
                                    new AlertDialog.Builder(BodyGamePCActivity.this).setTitle("温馨提示")
                                            .setMessage("您目前还没有参加过班级").create().show();
                                }else{
                                    startActivity(new Intent(BodyGamePCActivity.this, StudentHonorGridActivity.class));
                                }
                            } else {
                                Util.toastMsg(hasClassResponseData.getMsg());
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            dialogDissmiss();
                            super.failure(error);
                        }
                    });
                    break;
                case R.id.fl_right:
                case R.id.iv_email:
                    startActivity(new Intent(BodyGamePCActivity.this, MessageActivity.class));
                    break;

            }
        }

    }

    private void doStartActivity(int id) {
        switch (id) {
            //基本数据
            case R.id.ll_st_jibenshuju:
                startActivity(new Intent(this, StudentBaseDateActivity.class));
                break;
            //上传照片
            case R.id.ll_st_shangchuan:
                Intent intent = new Intent(this, UploadPhotoActivity.class);
                startActivity(intent);
                break;
            //复测
            case R.id.ll_st_fuce:
                if (flag) {
                    startActivityForResult(new Intent(this, FuceStActivity.class), Student_reteset);
                } else {
                    new AlertDialog.Builder(BodyGamePCActivity.this).setTitle("提示")
                            .setMessage("复测审核中……").create().show();

                }
                break;
            //减重故事
            case R.id.ll_st_jianzhong:
                startActivity(new Intent(this, LossWeightStoryActivity.class));
                break;
            //成绩单
            case R.id.ll_st_chengjidan:
                startActivity(new Intent(this, StudentScoreActivity.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //学员复测传递
        if (requestCode == Student_reteset && resultCode == RESULT_OK) {
            flag = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            String type = getIntent().getStringExtra("type");
            if ("0".equals(type)) {
                Intent inten=new Intent(this, HomeActviity.class);
                inten.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inten);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
