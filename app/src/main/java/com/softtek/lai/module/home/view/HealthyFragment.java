/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.community.adapter.CommunityAdapter;
import com.softtek.lai.module.community.view.MineHealthyFragment;
import com.softtek.lai.module.community.view.RecommendHealthyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy)
public class HealthyFragment extends BaseFragment {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.GONE);
        tv_title.setText("健康圈");
        iv_email.setVisibility(View.GONE);
        RecommendHealthyFragment recommendHealthyFragment=new RecommendHealthyFragment();
        MineHealthyFragment mineHealthyFragment=new MineHealthyFragment();
        fragments.add(recommendHealthyFragment);
        fragments.add(mineHealthyFragment);
        tab_content.setAdapter(new CommunityAdapter(getFragmentManager(),fragments));
        tab.setupWithViewPager(tab_content);
    }

    @Override
    protected void initDatas() {

    }
}
