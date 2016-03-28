package com.softtek.lai.module.grade.view;

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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.adapter.TabContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_students)
public class StudentsActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener,View.OnClickListener{

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
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void initViews() {
        iv_email.setVisibility(View.GONE);
        LossWeightFragment lwf1=new LossWeightFragment();
        lwf1.setFlagType(Integer.parseInt(Constants.LOSS_WEIGHT));
        LossWeightFragment lwf2=new LossWeightFragment();
        lwf2.setFlagType(Integer.parseInt(Constants.WAISTLINE));
        LossWeightFragment lwf3=new LossWeightFragment();
        lwf3.setFlagType(Integer.parseInt(Constants.PHYSIQUE));
        LossWeightFragment lwf4=new LossWeightFragment();
        lwf4.setFlagType(Integer.parseInt(Constants.LOSS_WEIGHT_PER));
        fragments.add(lwf1);
        fragments.add(lwf2);
        fragments.add(lwf3);
        fragments.add(lwf4);
        tabcontent.setOffscreenPageLimit(4);
        tabcontent.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
        tabLayout.setupWithViewPager(tabcontent);

        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("学员列表");
        tv_right.setText("邀请学员");

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
