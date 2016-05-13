/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.view;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorJZAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorStarAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorYGJAdapter;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.HorizontalListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
@InjectLayout(R.layout.activity_student_honor)
public class StudentHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

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

    @InjectView(R.id.lin_ygj_left)
    LinearLayout lin_ygj_left;

    @InjectView(R.id.lin_ygj_right)
    LinearLayout lin_ygj_right;


    private IStudentPresenter studentHonorPresenter;
    private ACache aCache;
    int widthPosition;

    private List<StudentHonorInfo> jz_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> fc_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> ygj_list = new ArrayList<StudentHonorInfo>();
    private List<StudentHonorInfo> star_list = new ArrayList<StudentHonorInfo>();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_ygj_left.setOnClickListener(this);
        lin_ygj_right.setOnClickListener(this);
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
        StudentHonorInfo studentHonorInfo = new StudentHonorInfo("1", "43", "33", "5");
        StudentHonorInfo studentHonorInfo1 = new StudentHonorInfo("1", "43", "33", "10");
        StudentHonorInfo studentHonorInfo2 = new StudentHonorInfo("1", "43", "33", "15");
        StudentHonorInfo studentHonorInfo3 = new StudentHonorInfo("1", "43", "33", "20");
        jz_list.add(studentHonorInfo);
        jz_list.add(studentHonorInfo1);
        jz_list.add(studentHonorInfo2);
        jz_list.add(studentHonorInfo3);
        System.out.println("jz_list:" + jz_list);
        StudentHonorJZAdapter adapter = new StudentHonorJZAdapter(this, jz_list);
        list_jz.setAdapter(adapter);

        if (fc_list.size() == 0) {

        } else if (fc_list.size() == 1) {
            img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
        } else if (fc_list.size() == 2) {
            img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
            img_fc_2.setImageResource(R.drawable.img_student_honor_yin);
        } else if (fc_list.size() == 3) {
            img_fc_1.setImageResource(R.drawable.img_student_honor_tong);
            img_fc_2.setImageResource(R.drawable.img_student_honor_yin);
            img_fc_3.setImageResource(R.drawable.img_student_honor_jin);
        }

        addData();
        StudentHonorYGJAdapter ygj_adapter = new StudentHonorYGJAdapter(this, ygj_list);
        list_ygj.setAdapter(ygj_adapter);
        addData1();

        StudentHonorStarAdapter star_adapter = new StudentHonorStarAdapter(this, star_list);
        list_star.setAdapter(star_adapter);
    }

    private void addData(){
        for (int i = 0; i <37 ; i++) {
            StudentHonorInfo studentHonorInfo = new StudentHonorInfo("1", "43", "33", "2015年12月");
            ygj_list.add(studentHonorInfo);
        }
    }

    private void addData1(){
        for (int i = 0; i <27 ; i++) {
            StudentHonorInfo studentHonorInfo = new StudentHonorInfo("1", "43", "33", i+"");
            star_list.add(studentHonorInfo);
        }
    }
    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.CounselorF);


//        img_fc_1.post(new Runnable() {
//            @Override
//            public void run() {
//                width = img_fc_1.getWidth();
//                ViewGroup.LayoutParams para = list_jz.getLayoutParams();
//                para.height = width;
//                list_jz.setLayoutParams(para);
//
//                list_ygj.setLayoutParams(para);
//
//                list_star.setLayoutParams(para);
//
//                //lin_fc.setLayoutParams(para);
//                lin_fc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, width));
//            }
//        });
    }

    @Override
    protected void initDatas() {
        studentHonorPresenter = new StudentImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        studentHonorPresenter.getStudentHonor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.lin_ygj_left:

                break;

            case R.id.lin_ygj_right:
                list_ygj.scrollTo(list_ygj.getScrollX()+100);
                break;
        }
    }

    public int getScrollY() {
        View c = list_ygj.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = list_ygj.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
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
