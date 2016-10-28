/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2.view;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame2.adapter.HonorPCFCAdapter;
import com.softtek.lai.module.bodygame2.adapter.HonorPCJZAdapter;
import com.softtek.lai.module.bodygame2.adapter.HonorPCStarAdapter;
import com.softtek.lai.module.bodygame2.adapter.HonorPCYGJAdapter;
import com.softtek.lai.module.bodygamest.model.HonorModel;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.widgets.CustomGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
@InjectLayout(R.layout.activity_history_student_honor)
public class StudentHonorPCActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.historylist_star)
    CustomGridView historylist_star;

    @InjectView(R.id.list_ygj)
    CustomGridView list_ygj;

    @InjectView(R.id.list_jz)
    CustomGridView list_jz;

    @InjectView(R.id.list_fc)
    CustomGridView list_fc;

    @InjectView(R.id.view_jz)
    View view_jz;
    @InjectView(R.id.view_fc)
    View view_fc;
    @InjectView(R.id.view_ygj)
    View view_ygj;

    private List<StudentHonorInfo> jz_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> fc_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> ygj_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> star_list = new ArrayList<StudentHonorInfo>();

    private IStudentPresenter studentHonorPresenter;

    @Override
    protected void initViews() {
        int type=getIntent().getIntExtra("type",0);
        if (type==1) {
            tv_title.setText("学员荣誉榜");
        }
        else {
            tv_title.setText("荣誉榜");
        }

        ll_left.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {
        dialogShow("加载中");
        studentHonorPresenter = new StudentImpl(this);
        studentHonorPresenter.getStudentHonorPC(getIntent().getLongExtra("accountid", 0) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }


    @Subscribe
    public void onEvent(HonorModel honorModel) {
        List<StudentHonorInfo> table1 = honorModel.getTable1();
        for (int i = 0; i < table1.size(); i++) {
            StudentHonorInfo studentHonorInfo = table1.get(i);
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
            HonorPCJZAdapter jz_adapter = new HonorPCJZAdapter(this, jz_list);
            list_jz.setAdapter(jz_adapter);
        }

        if (fc_list.size() == 0) {
            view_fc.setVisibility(View.GONE);
            list_fc.setVisibility(View.GONE);
        } else {
            List<StudentHonorInfo> fc_list_last = new ArrayList<StudentHonorInfo>();
            String value = fc_list.get(0).getValue();
            for (int i = 0; i < Integer.parseInt(value); i++) {
                StudentHonorInfo studentHonorInfo = new StudentHonorInfo("1", "复测", "", "", i + 1 + "");
                fc_list_last.add(studentHonorInfo);
            }

            HonorPCFCAdapter fc_adapter = new HonorPCFCAdapter(this, fc_list_last);
            list_fc.setAdapter(fc_adapter);
        }

        if (ygj_list.size() == 0) {
            view_ygj.setVisibility(View.GONE);
            list_ygj.setVisibility(View.GONE);
        } else {
            HonorPCYGJAdapter ygj_adapter = new HonorPCYGJAdapter(this, ygj_list);
            list_ygj.setAdapter(ygj_adapter);
        }
        if (star_list.size() == 0) {
            historylist_star.setVisibility(View.GONE);
        } else {
            HonorPCStarAdapter star_adapter = new HonorPCStarAdapter(this, star_list);
            historylist_star.setAdapter(star_adapter);
        }
    }
}
