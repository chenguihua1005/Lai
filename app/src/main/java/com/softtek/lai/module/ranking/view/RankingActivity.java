package com.softtek.lai.module.ranking.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.ranking.adapter.RankPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_ranking)
public class RankingActivity extends BaseActivity {
    public static final int DAY_RANKING=0;
    public static final int WEEK_RANKING=1;

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
        int rankType=getIntent().getIntExtra("rank_type",DAY_RANKING);
        tv_title.setText(rankType==DAY_RANKING?"日排名":"周排名");
        fragments=new ArrayList<>();
        fragments.add(RunGroupFragment.getInstance(rankType));
        fragments.add(NationalFragment.getInstance(rankType));
        content.setAdapter(new RankPageAdapter(getSupportFragmentManager(),fragments));
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
