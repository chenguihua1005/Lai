package com.softtek.lai.module.customermanagement.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
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

import static com.softtek.lai.R.string.phoneNum;

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

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    List<Fragment> fragments = new ArrayList<>();
    FragmentAdapter adapter;
    private String mobile = "";
    private boolean isRegistered;//是否已注册

    @Override
    protected void initViews() {
        tv_title.setText("客户详情");
        tv_right.setText("编辑");
        mobile = getIntent().getStringExtra("mobile");
        isRegistered = getIntent().getBooleanExtra("isRegistered", false);

        fragments.add(BasicInfoFragment.getInstance(mobile, isRegistered));
        fragments.add(StatisticsFragment.getInstance(mobile));
        fragments.add(RemarkFragment.getInstance(mobile));

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(DESTROY_SELF));

    }

    @Override
    protected void initDatas() {
        addremark_tv.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                Intent intent1 = new Intent(CustomerDetailActivity.this, EditCustomerInfoActivity.class);
                intent1.putExtra("mobile", mobile);
                intent1.putExtra("needQuery", true);//需要查询基础数据
                startActivity(intent1);
                break;
            case R.id.addremark_tv:
                Intent intent = new Intent(CustomerDetailActivity.this, AddRemarkActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public static final String DESTROY_SELF = "DESTROY_SELF";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equalsIgnoreCase(DESTROY_SELF)) {
                finish();
            }
        }
    };

}
