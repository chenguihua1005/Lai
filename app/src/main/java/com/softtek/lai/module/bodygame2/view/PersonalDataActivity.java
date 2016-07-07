package com.softtek.lai.module.bodygame2.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studentbasedate.adapter.BaseDataFragmentAdapter;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;
import com.softtek.lai.module.studentbasedate.view.BaseDateFragment;
import com.softtek.lai.module.studentbasedate.view.ClassDynamicFragment;
import com.softtek.lai.module.studentbasedate.view.DimensionChartFragmentPC;
import com.softtek.lai.module.studentbasedate.view.LossWeightChartFragmentPC;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.module.studetail.view.DimensionChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightLogActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_personal_data)
public class PersonalDataActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_after_tip)
    TextView tv_loss_after_tip;
    @InjectView(R.id.tv_total_loss_tip)
    TextView tv_total_loss_tip;
    //奖章一
    @InjectView(R.id.ll_honorn1)
    LinearLayout ll_honorn1;
    //奖章二
    @InjectView(R.id.ll_honorn2)
    LinearLayout ll_honorn2;
    //奖章三
    @InjectView(R.id.ll_honorn3)
    LinearLayout ll_honorn3;
    private long accountId=0;
    private long classId=0;
    private String review_flag="1";

    private IMemberInfopresenter memberInfopresenter;
    private List<Fragment> fragmentList=new ArrayList<>();
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        accountId=getIntent().getLongExtra("userId",72);
        classId=getIntent().getLongExtra("classId",15);
        review_flag=getIntent().getStringExtra("review");
        review_flag=review_flag==null?"1":review_flag;
        Map<String,String> params=new HashMap<>();
        params.put("userId",accountId+"");
        params.put("classId",classId+"");
        LossWeightChartFragment lwcf= LossWeightChartFragment.newInstance(params);
        DimensionChartFragment dcf=DimensionChartFragment.newInstance(params);
        fragmentList.add(lwcf);
        fragmentList.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initDatas() {
        LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item,ll_honorn1);
        TextView tv_jz_value= (TextView) findViewById(R.id.tv_jz_value);
//        tv_jz_value.setText("100");
//        LayoutInflater.from(this).inflate(R.layout.person_honor_jz_item,ll_honorn1);
//        TextView tv_jz_value= (TextView) findViewById(R.id.tv_jz_value);
//        tv_jz_value.setText("100");
        LayoutInflater.from(this).inflate(R.layout.person_honor_star_item,ll_honorn2);
        TextView tv_jzstar_value= (TextView) findViewById(R.id.tv_jzstar_value);
        tv_jzstar_value.setText("20");
        LayoutInflater.from(this).inflate(R.layout.person_honor_ygj_item,ll_honorn3);
        TextView tv_yuegj_value= (TextView) findViewById(R.id.tv_yuegj_value);
        tv_yuegj_value.setText("2016年7月");
        EventBus.getDefault().register(this);
        memberInfopresenter = new MemberInfoImpl(this,null);
        tv_title.setText("学员详情");
        dialogShow("正在读取学员数据...");
        memberInfopresenter.getMemberinfo(String.valueOf(classId),String.valueOf(accountId) , progressDialog);

//        studentBaseDate = new StudentBaseDateImpl(this);
//        BaseDateFragment baseDateFragment = BaseDateFragment.getInstance(null);
//        ClassDynamicFragment classDynamicFragment = ClassDynamicFragment.getInstance();
//        fragments.add(baseDateFragment);
//        fragments.add(classDynamicFragment);
//        tab_content.setAdapter(new BaseDataFragmentAdapter(getSupportFragmentManager(), fragments));
//        tab.setupWithViewPager(tab_content);
//        View tab1 = getLayoutInflater().inflate(R.layout.tab1_basedata, null);
//        View tab2 = getLayoutInflater().inflate(R.layout.tab2_basedata, null);
//        tab.removeAllTabs();
//        tab.addTab(tab.newTab().setCustomView(tab1), 0, true);
//        tab.addTab(tab.newTab().setCustomView(tab2), 1);
//        tab.setTabMode(TabLayout.MODE_FIXED);
//        dialogShow("正在加载...");
//        studentBaseDate.getClassMemberInfoPC();
//        LossWeightChartFragmentPC lwcf= LossWeightChartFragmentPC.newInstance();
//        DimensionChartFragmentPC dcf=DimensionChartFragmentPC.newInstance();
//        fragments.add(lwcf);
//        fragments.add(dcf);
//        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragments));
//        tabLayout.setupWithViewPager(tabcontent);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


    public void onFragmentInteraction(Uri uri) {

    }


//    public void getClassMemberInfo(StudentBaseInfoModel studentBaseInfoModel) {
//        dialogDissmiss();
//        if (studentBaseInfoModel != null) {
//            Log.i("基础数据" + studentBaseInfoModel);
//            tv_title.setText(studentBaseInfoModel.getClassName());
//            StringBuffer strDate = new StringBuffer();
//            StringBuffer endDate = new StringBuffer();
//            String start = studentBaseInfoModel.getStartDate();
//            String end = studentBaseInfoModel.getEndDate();
//            if (StringUtils.isNotEmpty(start)) {
//                strDate.append(DateUtil.getInstance().getYear(start));
//                strDate.append("年");
//                strDate.append(DateUtil.getInstance().getMonth(start));
//                strDate.append("月");
//            }
//            if (StringUtils.isNotEmpty(end)) {
//                endDate.append(DateUtil.getInstance().getYear(end));
//                endDate.append("年");
//                endDate.append(DateUtil.getInstance().getMonth(end));
//                endDate.append("月");
//            }
////            tv_title_date.setText(strDate.toString()+"-"+endDate.toString());
//
//            ((BaseDateFragment) fragments.get(0)).updateData(studentBaseInfoModel);
//            ((ClassDynamicFragment) fragments.get(1)).loadDynamic(studentBaseInfoModel.getClassId());
//        }
//    }

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

        if(StringUtils.isNotEmpty(memberModel.getLossWeight())&&Float.parseFloat(memberModel.getLossWeight())<0){
            tv_total_loss_tip.setText("共增重");
        }else{
            tv_total_loss_tip.setText("共减重");
        }
        tv_totle_lw.setText(Float.parseFloat(StringUtils.isEmpty(memberModel.getLossAfter())?"0":memberModel.getLossAfter())==0?"0斤":Math.abs(Float.parseFloat(memberModel.getLossWeight()))+"斤");
        tv_loss_before.setText(StringUtil.getFloatValue(memberModel.getLossBefore())+"斤");
        float lossAfter=StringUtil.getFloat(memberModel.getLossAfter());
        tv_loss_after.setText(lossAfter==0?"尚未复测":StringUtil.getFloat(memberModel.getLossAfter())+"斤");
        if(lossAfter==0){
            tv_loss_after_tip.setVisibility(View.GONE);
        }else{
            tv_loss_after_tip.setVisibility(View.VISIBLE);
        }

        if(!StringUtils.isEmpty(memberModel.getBeforeImg())){
            Picasso.with(this).load(memberModel.getBeforeImg()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_before);
        }
        if(!StringUtils.isEmpty(memberModel.getAfterImg())){
            Picasso.with(this).load(memberModel.getAfterImg()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_after);
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
