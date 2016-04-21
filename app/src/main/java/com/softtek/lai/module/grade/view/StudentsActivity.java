/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;
import com.softtek.lai.module.grade.adapter.TabContentAdapter;
import com.softtek.lai.module.login.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_students)
public class StudentsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener, View.OnClickListener {

    @InjectView(R.id.tab)
    TabLayout tabLayout;

    @InjectView(R.id.page)
    ViewPager tabcontent;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    private List<Fragment> fragments = new ArrayList<>();
    private long classId=0;
    private int review_flag=0;

    @Override
    protected void initViews() {
        classId=getIntent().getLongExtra("classId",0);
        review_flag=getIntent().getIntExtra("review",0);
        Map<String,String> params=new HashMap<>();
        params.put("classId",classId+"");
        params.put("review",review_flag+"");
        LossWeightFragment lossWeightFragment = LossWeightFragment.newInstance(params);
        WaistFragment waist=WaistFragment.newInstance(params);
        FatFragment fat=FatFragment.newInstance(params);
        LossWeightPerFragment per=LossWeightPerFragment.newInstance(params);
        fragments.add(lossWeightFragment);
        fragments.add(waist);
        fragments.add(fat);
        fragments.add(per);
        tabcontent.setOffscreenPageLimit(4);
        tabcontent.setAdapter(new TabContentAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(tabcontent);
        View tab1 = getLayoutInflater().inflate(R.layout.tab1, null);
        View tab2 = getLayoutInflater().inflate(R.layout.tab2, null);
        View tab3 = getLayoutInflater().inflate(R.layout.tab3, null);
        View tab4 = getLayoutInflater().inflate(R.layout.tab4, null);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab1), 0, true);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab2), 1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab3), 2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab4), 3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        if(review_flag==0){
            tv_right.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initDatas() {
        tv_title.setText("学员列表");
        UserModel user= UserInfoModel.getInstance().getUser();
        tv_right.setText("邀请学员");
        if(String.valueOf(Constants.SR).equals(user.getUserrole())){
            tv_right.setVisibility(View.GONE);

        }
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intents = new Intent(this, InviteStudentActivity.class);
                intents.putExtra("classId", classId);
                startActivity(intents);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
