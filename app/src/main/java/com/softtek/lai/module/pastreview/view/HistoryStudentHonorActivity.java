/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.pastreview.view;


import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.adapter.HistoryHonorFCAdapter;
import com.softtek.lai.module.pastreview.adapter.HistoryHonorJZAdapter;
import com.softtek.lai.module.pastreview.adapter.HistoryHonorStarAdapter;
import com.softtek.lai.module.pastreview.adapter.HistoryHonorYGJAdapter;
import com.softtek.lai.module.pastreview.model.HistoryHonorInfo;
import com.softtek.lai.module.pastreview.presenter.HistoryHonorListManager;
import com.softtek.lai.widgets.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_history_student_honor)
public class HistoryStudentHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener ,HistoryHonorListManager.HistoryHonorCallback{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.historylist_star)
    com.softtek.lai.module.mygrades.view.MyGridView historylist_star;

    @InjectView(R.id.list_ygj)
    com.softtek.lai.module.mygrades.view.MyGridView list_ygj;

    @InjectView(R.id.list_jz)
    com.softtek.lai.module.mygrades.view.MyGridView list_jz;

    @InjectView(R.id.list_fc)
    com.softtek.lai.module.mygrades.view.MyGridView list_fc;

    @InjectView(R.id.view_jz)
    View view_jz;
    @InjectView(R.id.view_fc)
    View view_fc;
    @InjectView(R.id.view_ygj)
    View view_ygj;
    HistoryHonorListManager historyHonorListManager;

    private List<HistoryHonorInfo> jz_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> fc_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> ygj_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> star_list = new ArrayList<HistoryHonorInfo>();


    @Override
    protected void initViews() {
        tv_title.setText("我的荣誉榜");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        Intent intent=getIntent();
        String userId=intent.getLongExtra("userId",0)+"";
        String classId=intent.getLongExtra("classId",0)+"";
        dialogShow("加载中");
        historyHonorListManager=new HistoryHonorListManager(this);
        //需要接收跳转classid参数替换死数据
        historyHonorListManager.getHistoryStudentHonor(userId,classId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
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
    public void getHistoryStudentHonorCallback(String type, List<HistoryHonorInfo> table1) {
        dialogDissmiss();
        try {
            if ("true".equals(type)) {

                System.out.println("table1:" + table1);
                for (int i = 0; i < table1.size(); i++) {
                    HistoryHonorInfo studentHonorInfo = table1.get(i);
                    String honorType = studentHonorInfo.getHonorType().toString();
                    if ("0".equals(honorType)) {
                        jz_list.add(studentHonorInfo);//减重
                    } else if ("1".equals(honorType)) {
                        fc_list.add(studentHonorInfo);//复测
                    } else if ("2".equals(honorType)) {
                        ygj_list.add(studentHonorInfo);//月
                    } else {
                        star_list.add(studentHonorInfo);//全国
                    }
                }

                if (jz_list.size() == 0) {
                    view_jz.setVisibility(View.GONE);
                    list_jz.setVisibility(View.GONE);
                } else {
                    HistoryHonorJZAdapter jz_adapter = new HistoryHonorJZAdapter(this, jz_list);
                    list_jz.setAdapter(jz_adapter);
                }

                if (fc_list.size() == 0) {
                    view_fc.setVisibility(View.GONE);
                    list_fc.setVisibility(View.GONE);
                } else {
                    HistoryHonorFCAdapter fc_adapter = new HistoryHonorFCAdapter(this, fc_list);
                    list_fc.setAdapter(fc_adapter);
                }

                if (ygj_list.size() == 0) {
                    view_ygj.setVisibility(View.GONE);
                    list_ygj.setVisibility(View.GONE);
                } else {
                    HistoryHonorYGJAdapter ygj_adapter = new HistoryHonorYGJAdapter(this, ygj_list);
                    list_ygj.setAdapter(ygj_adapter);
                }
                if (star_list.size() == 0) {
                    historylist_star.setVisibility(View.GONE);
                } else {
                    HistoryHonorStarAdapter star_adapter = new HistoryHonorStarAdapter(this, star_list);
                    historylist_star.setAdapter(star_adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
