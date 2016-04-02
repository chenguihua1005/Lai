/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorJZAdapter;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.present.IStudentHonorPresenter;
import com.softtek.lai.module.bodygamest.present.StudentHonorImpl;
import com.softtek.lai.module.counselor.adapter.HonorStudentAdapter;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.model.HonorTable1Model;
import com.softtek.lai.module.counselor.model.HonorTableModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.HorizontalListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_student_honor)
public class StudentHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_star)
    HorizontalListView list_star;

    @InjectView(R.id.img_fc_1)
    ImageView img_fc_1;

    @InjectView(R.id.img_fc_2)
    ImageView img_fc_2;

    @InjectView(R.id.img_fc_3)
    ImageView img_fc_3;

    @InjectView(R.id.lin_fc)
    LinearLayout lin_fc;

    @InjectView(R.id.list_ygj)
    HorizontalListView list_ygj;

    @InjectView(R.id.list_jz)
    HorizontalListView list_jz;


    private IStudentHonorPresenter studentHonorPresenter;
    private ACache aCache;
    int width;

    private List<StudentHonorInfo> jz_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> fc_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> ygj_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> star_list = new ArrayList<StudentHonorInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(List<StudentHonorInfo> studentHonorList) {
        System.out.println("studentHonorList:" + studentHonorList);
        for (int i = 0; i < studentHonorList.size(); i++) {
            StudentHonorInfo studentHonorInfo = studentHonorList.get(i);
            String honorType = studentHonorInfo.getHonorType().toString();
            if ("0".equals(honorType)) {
                jz_list.add(studentHonorInfo);
            } else if ("1".equals(honorType)) {
                fc_list.add(studentHonorInfo);
            } else if ("2".equals(honorType)) {
                ygj_list.add(studentHonorInfo);
            } else if ("3".equals(honorType)) {
                star_list.add(studentHonorInfo);
            }
        }
        if (jz_list.size() < 3) {
            addData();
        }
        StudentHonorJZAdapter adapter = new StudentHonorJZAdapter(this, jz_list, width);
        list_jz.setAdapter(adapter);
        list_star.setAdapter(adapter);
    }

    //增加未点亮的减重奖章
    private void addData() {
        StudentHonorInfo studentHonorInfo;
        for (int i = 0; i < 20; i++) {
            int lastValue = 0;
            if (jz_list.size() != 0) {
                lastValue = Integer.parseInt(jz_list.get(jz_list.size() - 1).getValue().toString());
            }

            if (lastValue >= 50) {
                studentHonorInfo = new StudentHonorInfo("0", "减重奖章", "False", (lastValue + 10) + "");
            } else {
                studentHonorInfo = new StudentHonorInfo("0", "减重奖章", "False", (lastValue + 5) + "");
            }
            jz_list.add(studentHonorInfo);
        }
    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("荣誉榜");


        img_fc_1.post(new Runnable() {
            @Override
            public void run() {
                width = img_fc_1.getWidth();
                ViewGroup.LayoutParams para = list_jz.getLayoutParams();
                para.height = width;
                list_jz.setLayoutParams(para);

                list_ygj.setLayoutParams(para);

                list_star.setLayoutParams(para);

                //lin_fc.setLayoutParams(para);
                lin_fc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, width));
            }
        });
    }

    @Override
    protected void initDatas() {
        studentHonorPresenter = new StudentHonorImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        studentHonorPresenter.getStudentHonor();
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;

            case R.id.tv_right:
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

}
