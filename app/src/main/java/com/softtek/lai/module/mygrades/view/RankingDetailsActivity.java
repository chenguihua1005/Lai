package com.softtek.lai.module.mygrades.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.mygrades.adapter.TabContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by julie.zhu on 5/16/2016.
 * 我的成绩-排名详情
 */
@InjectLayout(R.layout.activity_ranking_details)
public class RankingDetailsActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_rungroupname)
    TextView tv_rungroupname;

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("排名详情");

        DayRankFragment dayRankFragment=new DayRankFragment();
        WeekRankFragment weekRankFragment=new WeekRankFragment();
        fragments.add(dayRankFragment);
        fragments.add(weekRankFragment);
        tab_content.setAdapter(new TabContentAdapter(getSupportFragmentManager(),fragments));
        tab.setupWithViewPager(tab_content);
        Intent intent=getIntent();
        int flag=intent.getIntExtra("flag",0);
        tab_content.setCurrentItem(flag);
    }

    @Override
    protected void initDatas() {

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
