/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.historydate.view;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorJZAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorStarAdapter;
import com.softtek.lai.module.bodygamest.Adapter.StudentHonorYGJAdapter;
import com.softtek.lai.module.bodygamest.model.HnumsModel;
import com.softtek.lai.module.bodygamest.model.HonorModel;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.model.StudentHonorTypeInfo;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.historydate.adapter.HistoryHonorFCAdapter;
import com.softtek.lai.module.historydate.adapter.HistoryHonorJZAdapter;
import com.softtek.lai.module.historydate.adapter.HistoryHonorStarAdapter;
import com.softtek.lai.module.historydate.adapter.HistoryHonorYGJAdapter;
import com.softtek.lai.module.historydate.model.HistoryHonorInfo;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_history_student_honor)
public class HistoryStudentHonorActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_star)
    GridView list_star;

    @InjectView(R.id.list_ygj)
    GridView list_ygj;

    @InjectView(R.id.list_jz)
    GridView list_jz;

    @InjectView(R.id.list_fc)
    GridView list_fc;

    @InjectView(R.id.view_jz)
    View view_jz;
    @InjectView(R.id.view_fc)
    View view_fc;
    @InjectView(R.id.view_ygj)
    View view_ygj;


    private List<HistoryHonorInfo> jz_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> fc_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> ygj_list = new ArrayList<HistoryHonorInfo>();
    private List<HistoryHonorInfo> star_list = new ArrayList<HistoryHonorInfo>();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(List<HistoryHonorInfo> table1) {
        System.out.println("list:" + table1);
        for (int i = 0; i < table1.size(); i++) {
            HistoryHonorInfo studentHonorInfo = table1.get(i);
            String honorType = studentHonorInfo.getHonorType().toString();
            if ("0".equals(honorType)) {
                jz_list.add(studentHonorInfo);
            } else if ("1".equals(honorType)) {
                fc_list.add(studentHonorInfo);
            } else if ("2".equals(honorType)) {
                ygj_list.add(studentHonorInfo);
            } else {
                star_list.add(studentHonorInfo);
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
            list_star.setVisibility(View.GONE);
        } else {
            HistoryHonorStarAdapter star_adapter = new HistoryHonorStarAdapter(this, star_list);
            list_star.setAdapter(star_adapter);
        }
    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("我的荣誉榜");


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

}
