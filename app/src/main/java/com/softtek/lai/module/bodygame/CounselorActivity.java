/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.presenter.ITiGuanSai;
import com.softtek.lai.module.bodygame.presenter.TiGuanSaiImpl;
import com.softtek.lai.module.counselor.view.AssistantActivity;
import com.softtek.lai.module.counselor.view.CounselorClassListActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.counselor.view.SPHonorActivity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.newmemberentry.view.EntryActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.view.RetestActivity;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/17/2016.
 * 体管赛页面
 */
@InjectLayout(R.layout.activity_counselor)
public class CounselorActivity extends BaseActivity implements View.OnClickListener {
    //标题栏
    @InjectView(R.id.tv_right)
    TextView bar_right;
    @InjectView(R.id.tv_title)
    TextView bar_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //banner图片
    @InjectView(R.id.iv_adv)
    ImageView iv_adv;
    //复测按钮
    @InjectView(R.id.ll_counselor_fuce)
    LinearLayout ll_counselor_fuce;
    //体管赛点击
    @InjectView(R.id.ll_tiguansai)
    LinearLayout ll_tiguansai;

    //新学员录入按钮
    @InjectView(R.id.ll_new_student)
    LinearLayout ll_new_student;
    //往期回顾按钮
    @InjectView(R.id.ll_review)
    LinearLayout ll_review;
    //当前进度按钮
    @InjectView(R.id.ll_process)
    LinearLayout ll_process;
    //荣誉榜按钮
    @InjectView(R.id.ll_honor)
    LinearLayout ll_honor;
    //赛况
    @InjectView(R.id.ll_match)
    LinearLayout ll_match;
    //提示页面
    @InjectView(R.id.ll_tip)
    LinearLayout ll_tip;
    //助教管理
    @InjectView(R.id.ll_assistant)
    LinearLayout ll_assistant;
    @InjectView(R.id.tv_fucenum)
    TextView tv_fucenum;
    private ITiGuanSai tiGuanSai;
    private FuceNumModel fuceNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化事件总线，并注册当前类
        EventBus.getDefault().register(this);
//        UserModel user= (UserModel) aCache.getAsObject(Constants.USER_ACACHE_KEY);
//        user.getUserrole();
        //按钮监听
        ll_counselor_fuce.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_tiguansai.setOnClickListener(this);
        ll_new_student.setOnClickListener(this);
        ll_honor.setOnClickListener(this);
        ll_process.setOnClickListener(this);
        ll_review.setOnClickListener(this);
        ll_match.setOnClickListener(this);
        ll_assistant.setOnClickListener(this);
        ll_tip.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(TiGuanSaiModel tiGuanSai) {
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》" + tiGuanSai.getImg_Addr());
        //Picasso.with(this).load().into(iv_adv);
//        Picasso.with(getBaseContext()).load(tiGuanSai.getImg_Addr()).into(iv_adv);
        Picasso.with(this).load(tiGuanSai.getImg_Addr()).placeholder(R.drawable.froyo).error(R.drawable.gingerbread).into(iv_adv);


    }

    @Subscribe
    public void onEvent1(FuceNumModel fuceNum) {
        System.out.println("dsadasdsadasda>>》》》》》》》》》》》》》》" + fuceNum.getCount());
        if (Integer.parseInt(fuceNum.getCount()) > 10) {
            tv_fucenum.setText("10+");
        } else {
            tv_fucenum.setText(fuceNum.getCount());
        }

    }


    @Override
    protected void initViews() {
        bar_title.setText(R.string.CounselorBarL);
    }

    @Override
    protected void initDatas() {
        tiGuanSai = new TiGuanSaiImpl();
        tiGuanSai.getTiGuanSai();
        tiGuanSai.doGetFuceNum(36);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //复测按钮点击跳转事件
            case R.id.ll_counselor_fuce: {
                Intent intent = new Intent(CounselorActivity.this, RetestActivity.class);
                startActivity(intent);


            }
            break;
            //主题栏返回事件
            case R.id.ll_left: {
                finish();

            }
            break;
            //体管赛按钮点击跳转事件
            case R.id.ll_tiguansai: {
                Intent intent = new Intent(this, CounselorClassListActivity.class);
                startActivity(intent);
            }
            break;
            //新学员录入跳转事件
            case R.id.ll_new_student: {
                Intent intent = new Intent(this, EntryActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_honor: {
                Intent intent = new Intent(this, SPHonorActivity.class);
                startActivity(intent);
                Util.toastMsg("荣誉榜页面");
            }
            break;
            //当前进度事件跳转
            case R.id.ll_process: {
                Intent intent = new Intent(this, JingduActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_review: {
                Util.toastMsg("往期回顾");
            }
            break;
            //大赛赛况事件跳转
            case R.id.ll_match: {
                Intent intent = new Intent(CounselorActivity.this, GameActivity.class);
                startActivity(intent);
                Util.toastMsg("大赛赛况页面");
            }
            break;
            //提示事件跳转
            case R.id.ll_tip: {
                Intent intent = new Intent(CounselorActivity.this, WriteActivity.class);
                startActivity(intent);
                Util.toastMsg("提示页面");
            }
            break;
            //助教管理跳转事件
            case R.id.ll_assistant: {
                Intent intent = new Intent(CounselorActivity.this, AssistantActivity.class);
                startActivity(intent);
                Util.toastMsg("助教管理页面");
            }
            break;

        }
    }
}