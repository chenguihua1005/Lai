package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
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
public class CustomerDetailActivity extends BaseActivity implements View.OnClickListener {
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

    @InjectView(R.id.addremark_tv)
    TextView addremark_tv;

    List<Fragment> fragments = new ArrayList<>();
    FragmentAdapter adapter;
    private String mobile = "";

    @Override
    protected void initViews() {
        tv_title.setText("客户详情");
        mobile = getIntent().getStringExtra("mobile");

        fragments.add(BasicInfoFragment.getInstance(mobile));
        fragments.add(StatisticsFragment.getInstance(mobile));
        fragments.add(RemarkFragment.getInstance(mobile));

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);


    }

    @Override
    protected void initDatas() {
        addremark_tv.setOnClickListener(this);

    }


    @OnClick({R.id.ll_left, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:

                break;
            case R.id.addremark_tv:
                Intent intent = new Intent(CustomerDetailActivity.this, AddRemarkActivity.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                break;
        }
    }
}
