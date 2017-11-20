package com.softtek.lai.module.customermanagement.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.love.adapter.LoverAdapter;
import com.softtek.lai.module.customermanagement.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */

@InjectLayout(R.layout.activity_customer_detail)
public class CustomerDetailActivity extends BaseActivity {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    FragmentAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("客户详情");

        fragments.add(BasicInfoFragment.getInstance());
        fragments.add(StatisticsFragment.getInstance());
        fragments.add(RemarkFragment.getInstance());

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);


    }

    @Override
    protected void initDatas() {

    }


    @OnClick({R.id.ll_left, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:

                break;
        }
    }
}