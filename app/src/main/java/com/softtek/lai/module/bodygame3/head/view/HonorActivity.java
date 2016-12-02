package com.softtek.lai.module.bodygame3.head.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.head.adapter.HonorRankAdapter;
import com.softtek.lai.module.ranking.adapter.RankPageAdapter;
import com.softtek.lai.module.ranking.view.NationalFragment;
import com.softtek.lai.module.ranking.view.RunGroupFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_honorranking)
public class HonorActivity extends BaseActivity {
    public static final int WEEK_RANKING=0;//周排名
    public static final int MONTH_RANKING=1;//月排名
    public static final int TOAL_RANKING=2;//总排名

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;

    @Override
    protected void initViews() {
        tv_title.setText("荣誉榜");
        fragments=new ArrayList<>();
        fragments.add(WeekHonorFragment.getInstance());
//        fragments.add(MonthHonorFragment.getInstance());
//        fragments.add(TotalHonorFragment.getInstance());
        content.setAdapter(new HonorRankAdapter(getSupportFragmentManager(),fragments));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initDatas() {


    }
}
