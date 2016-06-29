package com.softtek.lai.module.pastreview.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.pastreview.model.PastBaseData;
import com.softtek.lai.module.pastreview.presenter.PastReviewManager;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pc_past_base_data)
public class PcPastBaseDataActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;//共减重
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;//减重前
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;//减重后
    @InjectView(R.id.tv_total_loss_tip)
    TextView tv_total_loss_tip;
    @InjectView(R.id.tv_loss_after_tip)
    TextView tv_loss_after_tip;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;

    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;

    List<Fragment> fragments=new ArrayList<>();
    private PastReviewManager manager;
    long userId;
    long classId;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        //需要传入班级Id和用户id
        UserModel model=UserInfoModel.getInstance().getUser();
        userId=Long.parseLong(model.getUserid());
        classId=Long.parseLong(getIntent().getStringExtra("classId"));
        LossWeightChartFragmentPC lwcf= LossWeightChartFragmentPC.newInstance(model.getUserid(),String.valueOf(classId));
        DimensionChartFragmentPC dcf= DimensionChartFragmentPC.newInstance(model.getUserid(),classId+"");
        fragments.add(lwcf);
        fragments.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void initDatas() {
        tv_title.setText("基础数据");
        manager=new PastReviewManager();
        //请求学员基本数据
        dialogShow("正在读取学员数据...");
        manager.getBaseDataInfo(this,userId,classId);
    }

    public void onGetData(PastBaseData memberModel) {
        dialogDissmiss();
        if(memberModel==null){
            return;
        }
        if(StringUtils.isNotEmpty(memberModel.getTotalLoss())&&Float.parseFloat(memberModel.getTotalLoss())<0){
            tv_total_loss_tip.setText("共增重");
        }else{
            tv_total_loss_tip.setText("共减重");
        }
        tv_totle_lw.setText(Float.parseFloat(StringUtils.isEmpty(memberModel.getAfterWeight())?"0":memberModel.getAfterWeight())==0?"0斤":Math.abs(Float.parseFloat(memberModel.getTotalLoss()))+"斤");
        tv_loss_before.setText(StringUtil.getFloatValue(memberModel.getBeforeWeight())+"斤");
        float lossAfter=StringUtil.getFloat(memberModel.getAfterWeight());
        tv_loss_after.setText(lossAfter==0?"尚未复测":StringUtil.getFloat(memberModel.getAfterWeight())+"斤");
        if(lossAfter==0){
            tv_loss_after_tip.setVisibility(View.GONE);
        }else{
            tv_loss_after_tip.setVisibility(View.VISIBLE);
        }
        if(!StringUtils.isEmpty(memberModel.getBeforeImage())){
            Picasso.with(this).load(memberModel.getBeforeImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_before);
        }
        if(!StringUtils.isEmpty(memberModel.getAfterImage())){
            Picasso.with(this).load(memberModel.getAfterImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_after);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
