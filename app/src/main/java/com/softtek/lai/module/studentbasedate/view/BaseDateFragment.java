package com.softtek.lai.module.studentbasedate.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/13/2016.
 */
@InjectLayout(R.layout.fragment_basedate)
public class BaseDateFragment extends BaseFragment{


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
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;

    List<Fragment> fragments=new ArrayList<>();

    public static BaseDateFragment getInstance(Serializable obj){
        BaseDateFragment fragment=new BaseDateFragment();
        if(obj!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("obj",obj);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initViews() {


    }

    long accountId=0;
    long classId=0;

    @Override
    protected void initDatas() {
        //获取传递过来的参数
        Bundle bundle=getArguments();
        if(bundle!=null){
            StudentBaseInfoModel model= (StudentBaseInfoModel) bundle.getSerializable("obj");
            accountId=model.getAccountId();
            classId=model.getClassId();
            tv_totle_lw.setText(model.getLossTotal()+"");
            tv_loss_before.setText(model.getLossBefore());
            tv_loss_after.setText(model.getLossAfter());
            try {
                Picasso.with(getContext()).load(model.getLossBeforePhoto()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_before);
                Picasso.with(getContext()).load(model.getLossAfterPhoto()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_after);
            }catch (Exception e){

            }
        }
        LossWeightChartFragmentPC lwcf= LossWeightChartFragmentPC.newInstance();
        DimensionChartFragmentPC dcf=DimensionChartFragmentPC.newInstance();
        fragments.add(lwcf);
        fragments.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getFragmentManager(), fragments));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    public void updateData(StudentBaseInfoModel model){
        accountId=model.getAccountId();
        classId=model.getClassId();
        tv_totle_lw.setText(Float.parseFloat(model.getLossAfter())==0?"0斤":model.getLossTotal()+"斤");
        tv_loss_before.setText(StringUtil.getFloatValue(model.getLossBefore())+"斤");
        tv_loss_after.setText(Float.parseFloat(model.getLossAfter())==0?"尚未复测":Float.parseFloat(model.getLossAfter())+"斤");
        if(StringUtils.isNotEmpty(model.getLossBeforePhoto())){
            Picasso.with(getContext()).load(model.getLossBeforePhoto()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_before);
        }
        if(StringUtils.isNotEmpty(model.getLossAfterPhoto())){
            Picasso.with(getContext()).load(model.getLossAfterPhoto()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(iv_loss_after);
        }
    }
}
