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
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.customermanagement.adapter.FragmentAdapter;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
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

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_cancle)
    TextView mInviteMatch;

    List<Fragment> fragments = new ArrayList<>();
    FragmentAdapter adapter;
    private String mobile = "";
    private boolean isRegistered;//是否已注册

    @Override
    protected void initViews() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        tv_title.setText("客户详情");

        mobile = getIntent().getStringExtra("mobile");
        isRegistered = getIntent().getBooleanExtra("isRegistered", false);

        if (!isRegistered) {
            tv_right.setText("编辑");
        } else {
            tv_right.setText("详情");
        }

        fragments.add(BasicInfoFragment.getInstance(mobile, isRegistered));
        fragments.add(StatisticsFragment.getInstance(mobile));
        fragments.add(RemarkFragment.getInstance(mobile));

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(DESTROY_SELF));
        //注册订阅者
        EventBus.getDefault().register(this);
        mInviteMatch.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        addremark_tv.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    //    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(BasicInfoModel model) {
        tv_title.setText(model.getBasics().getName());
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
                intent1.putExtra("isRegistered", isRegistered);
                startActivity(intent1);
                break;
            case R.id.addremark_tv:
                Intent intent = new Intent(CustomerDetailActivity.this, AddRemarkActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                break;
            case R.id.tv_cancle:
                Intent matchIntent = new Intent(this, InviteMatchActivity.class);
                matchIntent.putExtra("customName",tv_title.getText().toString().trim());
                matchIntent.putExtra("mobile",mobile);
                startActivity(matchIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        //注销注册
        EventBus.getDefault().unregister(this);
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
