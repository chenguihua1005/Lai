/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.bodygame.view.TipsActivity;
import com.softtek.lai.module.bodygamest.model.HasClass;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.lossweightstory.view.LossWeightStoryActivity;
import com.softtek.lai.module.studentbasedate.view.StudentBaseDateActivity;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_student)
public class StudentActivity extends BaseActivity implements View.OnClickListener {
    private ITiGuanSai tiGuanSai;
    private StudentImpl studentImpl;
    private FuceNumModel fuceNum;
    //标题
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_st_num)
    TextView tv_st_num;
    @InjectView(R.id.iv_st_adv)
    ImageView iv_st_adv;
    @InjectView(R.id.im_refreshst)
    ImageView im_refreshst;
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
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());

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

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        im_refreshst.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        tv_title.setText("体管赛（学员版）");
        tiGuanSai = new TiGuanSaiImpl();
        tiGuanSai.getTiGuanSai();
        tiGuanSai.doGetFuceNum(loginid);
        tiGuanSai.doGetTotal();
        studentImpl=new StudentImpl(this);

    }

    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai) {

        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_st_adv);


    }

    @Subscribe
    public void onEvent1(FuceNumModel fuceNum) {

        if (Integer.parseInt(fuceNum.getCount()) > 10) {
            tv_st_num.setVisibility(View.VISIBLE);
            tv_st_num.setText("10+");
        } else if (Integer.parseInt(fuceNum.getCount()) != 0&&fuceNum.getCount()!=""){
            tv_st_num.setVisibility(View.VISIBLE);
            tv_st_num.setText(fuceNum.getCount());
        }

    }
    @Subscribe
    public void doGetTotol(List<TotolModel> totolModels){
        tv_totalpersonst.setText(totolModels.get(0).getTotal_person());
        tv_total_lossst.setText(totolModels.get(0).getTotal_loss());
    }

    @Override
    public void onClick(View v) {
        final int id=v.getId();
        if(id!=R.id.ll_st_saikuang&&id!=R.id.ll_st_tipst&&id!=R.id.ll_left){
            dialogShow("检查中...");
            studentImpl.hasClass(new RequestCallback<ResponseData<HasClass>>() {
                @Override
                public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                    dialogDissmiss();
                    Log.i(hasClassResponseData.toString());
                    if(hasClassResponseData.getStatus()==200){
                        if("1".equals(hasClassResponseData.getData().getIsHave())){
                            doStartActivity(id);
                        }else{
                            //学员没有班级
                            new AlertDialog.Builder(StudentActivity.this).setTitle("提示")
                                    .setMessage("您目前还没有参加班级").create().show();
                        }
                    }else{
                        Util.toastMsg(hasClassResponseData.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialogDissmiss();
                    super.failure(error);
                }
            });
        }else {
            switch (id) {
                //大赛赛况
                case R.id.ll_st_saikuang:
                    startActivity(new Intent(this, GameActivity.class));
                    break;
                //提示
                case R.id.ll_st_tipst:
                    Intent intent2 = new Intent(this, TipsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.ll_left:
                    finish();
                    break;
            }
        }

    }

    private void doStartActivity(int id){
        switch (id) {

            //点击跳转事件
            case R.id.ll_st_jibenshuju:
                startActivity(new Intent(StudentActivity.this, StudentBaseDateActivity.class));
                break;
            //上传照片
            case R.id.ll_st_shangchuan:
                Intent intent = new Intent(this, UploadPhotoActivity.class);
                startActivity(intent);
                break;
            //复测
            case R.id.ll_st_fuce:
                Intent intent1 = new Intent(this, FuceStActivity.class);
                startActivity(intent1);
                break;
            //减重故事
            case R.id.ll_st_jianzhong:
                startActivity(new Intent(this, LossWeightStoryActivity.class));
                break;
            //成绩单
            case R.id.ll_st_chengjidan:
                startActivity(new Intent(this, StudentScoreActivity.class));
                break;
            //荣誉榜
            case R.id.ll_st_rongyu:
                startActivity(new Intent(this, StudentHonorGridActivity.class));
                break;
        }
    }
}
