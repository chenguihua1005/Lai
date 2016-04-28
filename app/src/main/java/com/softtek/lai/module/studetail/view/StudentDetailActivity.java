/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_detail)
public class StudentDetailActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener {

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.civ_header_image)
    CircleImageView civ_header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_totle_log)
    TextView tv_totle_log;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabContent;
    @InjectView(R.id.ll)
    LinearLayout ll_log;

    private ProgressDialog progressDialog;
    private IMemberInfopresenter memberInfopresenter;
    private List<Fragment> fragmentList=new ArrayList<>();
    private long accountId=0;
    private long classId=0;
    private String review_flag="1";
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_log.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
        accountId=getIntent().getLongExtra("userId",0);
        classId=getIntent().getLongExtra("classId",0);
        review_flag=getIntent().getStringExtra("review");
        review_flag=review_flag==null?"1":review_flag;
        Map<String,String> params=new HashMap<>();
        params.put("userId",accountId+"");
        params.put("classId",classId+"");
        LossWeightChartFragment lwcf= LossWeightChartFragment.newInstance(params);
        DimensionChartFragment dcf=DimensionChartFragment.newInstance(params);
        fragmentList.add(lwcf);
        fragmentList.add(dcf);
        tabContent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(tabContent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void initDatas() {
        EventBus.getDefault().register(this);
        memberInfopresenter = new MemberInfoImpl(this,null);
        tv_title.setText("学员详情");

        progressDialog.show();
        memberInfopresenter.getMemberinfo(String.valueOf(classId),String.valueOf(accountId) , progressDialog);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll:
                Intent intent=new Intent(this,LossWeightLogActivity.class);
                intent.putExtra("accountId",accountId);
                intent.putExtra("review",Integer.parseInt(review_flag));
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetData(MemberModel memberModel) {

        tv_name.setText(memberModel.getUserName());
        tv_phone.setText(memberModel.getMobile());
        tv_totle_log.setText(memberModel.getLogCount() + "篇");
        Log.i("学员详情="+memberModel.toString());
        tv_totle_lw.setText(StringUtils.isEmpty(memberModel.getLossWeight())?"0":memberModel.getLossWeight() + "斤");
        tv_loss_before.setText(StringUtils.isEmpty(memberModel.getLossBefor())?"0":memberModel.getLossBefor() + "斤");
        tv_loss_after.setText(StringUtils.isEmpty(memberModel.getLossAfter())?"0":memberModel.getLossAfter() + "斤");
        if(StringUtils.isEmpty(memberModel.getPhoto())){
            Picasso.with(this).load(memberModel.getPhoto()).placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ_header_image);
        }
        if(StringUtils.isEmpty(memberModel.getBeforImg())){
            Picasso.with(this).load(memberModel.getBeforImg()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_before);
        }
        if(StringUtils.isEmpty(memberModel.getAfterImg())){
            Picasso.with(this).load(memberModel.getAfterImg()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_after);
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
