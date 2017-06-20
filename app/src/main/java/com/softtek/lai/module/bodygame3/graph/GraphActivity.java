package com.softtek.lai.module.bodygame3.graph;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.graph.adaper.GraphAdapter;
import com.softtek.lai.module.healthyreport.HistoryDataActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_graph)
public class GraphActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    GraphAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("曲线图");
        tv_right.setText("健康记录");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final long accountId = getIntent().getLongExtra("accountId", 0);
        String classId = getIntent().getStringExtra("classId");
        boolean isShow=getIntent().getBooleanExtra("isShow",false);
        if(isShow){
            fl_right.setVisibility(View.VISIBLE);
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(GraphActivity.this, HistoryDataActivity.class);
                    intent.putExtra("accountId",accountId);
                    startActivity(intent);
                }
            });
        }else {
            fl_right.setVisibility(View.GONE);
        }
        fragments.add(LossWeightFragment.getInstance(accountId, classId));
        fragments.add(DimemsionFragment.getInstance(accountId, classId));
        adapter = new GraphAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);
    }

    @Override
    protected void initDatas() {

    }
}
