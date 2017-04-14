package com.softtek.lai.module.healthyreport;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HealthyRecordFragmentAdapter;
import com.softtek.lai.module.healthyreport.model.FragmentModel;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_chart)
public class HealthyChartActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    List<FragmentModel> fragmentList=new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("历史数据");
    }

    @Override
    protected void initDatas() {
        for (int i=0;i<10;i++){
            fragmentList.add(new FragmentModel("体重",HealthyChartFragment.newInstance()));
        }
        tab_content.setAdapter(new HealthyRecordFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tab_content.addOnPageChangeListener(this);
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(0);

        tab_content.setCurrentItem(0);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
