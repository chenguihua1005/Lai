package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.HealthyReportCustomerAdapter;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;
import com.softtek.lai.module.customermanagement.model.BasicModel;
import com.softtek.lai.module.customermanagement.model.HealthyItemModel;
import com.softtek.lai.module.customermanagement.model.LatestRecordModel;
import com.softtek.lai.module.customermanagement.presenter.BasicInfoPresenter;
import com.softtek.lai.widgets.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_basicinfo)
public class BasicInfoFragment extends LazyBaseFragment<BasicInfoPresenter> implements BasicInfoPresenter.BasicInfoCallBack, View.OnClickListener {
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_other)
    TextView tv_other;
    @InjectView(R.id.tv_birth)
    TextView tv_birth;
    @InjectView(R.id.tv_zhicheng)
    TextView tv_zhicheng;
    @InjectView(R.id.tv_mobile)
    TextView tv_mobile;
    @InjectView(R.id.tv_cn)
    TextView tv_cn;
    @InjectView(R.id.tv_angle)
    TextView tv_angle;

    @InjectView(R.id.tv_mesuretime)
    TextView tv_mesuretime;

    @InjectView(R.id.list)
    RecyclerView list;


    @InjectView(R.id.ll_health)
    RelativeLayout ll_health;

    @InjectView(R.id.ll_more)
    LinearLayout ll_more;

    @InjectView(R.id.btn_register)
    Button btn_register;

    ArrayList<HealthyItemModel> items = new ArrayList<>();
    HealthyReportCustomerAdapter adapter;


    private static String mobile = "";
    private static boolean isRegistered;//是否已注册

    public static Fragment getInstance(String mobileNum, boolean isRegister) {
        Fragment fragment = new BasicInfoFragment();
        mobile = mobileNum;
        isRegistered = isRegister;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        ll_health.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        if (!isRegistered) {
            ll_health.setEnabled(false);
            ll_more.setVisibility(View.GONE);
            tv_mesuretime.setText("该客户还没有注册账户，无法看到健康记录");
            list.setVisibility(View.GONE);
            btn_register.setVisibility(View.VISIBLE);
        } else {
            btn_register.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void initDatas() {
        Log.i("BasicInfoFragment", "mobile = " + mobile);

        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setHasFixedSize(true);
        list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new HealthyReportCustomerAdapter(items, getContext(), false);
//        adapter.setListener(this);
        list.setAdapter(adapter);

        setPresenter(new BasicInfoPresenter(this));
        getPresenter().getCustomerBasicInfo(mobile);

    }

    @Override
    public void getBasicInfo(BasicInfoModel model) {
        if (model != null) {
            BasicModel basicModel = model.getBasics();
            tv_name.setText(basicModel.getName());
            tv_other.setText(basicModel.getGender().equals("0") ? "男" : " 女" + "|" + basicModel.getHeight());
            tv_birth.setText(basicModel.getBirthDay());
            tv_zhicheng.setText(basicModel.getUserRole());
            tv_mobile.setText(basicModel.getMobile());
            tv_cn.setText(basicModel.getCertification());
            tv_angle.setText(basicModel.getAngel());


            items.clear();

            LatestRecordModel latestModel = model.getLatest();

            if (latestModel != null) {
                String measureTime = latestModel.getMeasureTime();
                if (!TextUtils.isEmpty(measureTime)) {
                    tv_mesuretime.setText("最新健康记录（" + measureTime + ")");
                }

                if (latestModel.getItemList() != null) {
                    items.addAll(latestModel.getItemList());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_health:

                break;
            case R.id.btn_register:
                Intent intent = new Intent(getContext(), RegistForCustomerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
