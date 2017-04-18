package com.softtek.lai.module.healthyreport;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.healthyreport.adapter.HealthyRecordFragmentAdapter;
import com.softtek.lai.module.healthyreport.model.FragmentModel;
import com.softtek.lai.module.healthyreport.model.HealthyItem;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_healthy_chart)
public class HealthyChartActivity extends BaseActivity implements View.OnClickListener{

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
        Bundle bundle1=getIntent().getBundleExtra("base");
        int type=getIntent().getIntExtra("pid",0);
        ArrayList<HealthyItem> items=getIntent().getParcelableArrayListExtra("items");
        int selector=0;
        for (int i=0;i<items.size();i++){
            HealthyItem item=items.get(i);
            if(item.getPid()==type){
                selector=i;
            }
            Bundle bundle=new Bundle();
            bundle.putAll(bundle1);
            bundle.putString("chartTitle",item.getTitle());
            bundle.putInt("pid",item.getPid());
            fragmentList.add(new FragmentModel(item.getTitle(),HealthyChartFragment.newInstance(bundle)));
        }
        tab_content.setAdapter(new HealthyRecordFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_content.setOffscreenPageLimit(4);

        tab_content.setCurrentItem(selector,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
