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
import com.softtek.lai.module.customermanagement.model.LatestRecordModel;
import com.softtek.lai.module.customermanagement.presenter.BasicInfoPresenter;
import com.softtek.lai.module.healthyreport.HealthyChartActivity;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.healthyreport.model.HealthyItem;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.
 */


@InjectLayout(R.layout.fragment_basicinfo)
public class BasicInfoFragment extends LazyBaseFragment<BasicInfoPresenter> implements BasicInfoPresenter.BasicInfoCallBack, View.OnClickListener, HealthyReportCustomerAdapter.OnItemClickListener {
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

    @InjectView(R.id.civ_head)
    CircleImageView civ_head;

    ArrayList<HealthyItem> items = new ArrayList<>();
    HealthyReportCustomerAdapter adapter;

    private BasicModel basicModel;
    private BasicInfoModel basicInfoModel;


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
            ll_more.setOnClickListener(this);
        }


    }

    @Override
    protected void initDatas() {
        Log.i("BasicInfoFragment", "mobile = " + mobile);

        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setHasFixedSize(true);
        list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new HealthyReportCustomerAdapter(items, getContext(), false);
        adapter.setListener(this);
        list.setAdapter(adapter);

        setPresenter(new BasicInfoPresenter(this));
        getPresenter().getCustomerBasicInfo(mobile);

    }

    @Override
    public void getBasicInfo(BasicInfoModel model) {
        if (model != null) {
            basicInfoModel = model;
            basicModel = model.getBasics();

            if (!TextUtils.isEmpty(basicModel.getPhoto())) {
                Picasso.with(getContext()).load(AddressManager.get("photoHost") + basicModel.getPhoto()).fit().into(civ_head);
            } else {
                Picasso.with(getContext()).load(R.drawable.img_default).fit().into(civ_head);
            }


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
                } else {
                    tv_mesuretime.setText("最新健康记录（暂无)");
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
            case R.id.ll_more:
                Intent intent1 = new Intent(getContext(), HealthListActivity.class);
                intent1.putExtra("accountId", basicModel.getAccountId());
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        //跳转到曲线图
        HealthyItem item = items.get(position);
        Intent intent = new Intent(getContext(), HealthyChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("isVisitor", HealthyReportActivity.NON_VISITOR);
        bundle.putString("accountId", basicModel.getAccountId() + "");
        bundle.putString("recordId", basicInfoModel.getRecord().getAcInfoId());
        intent.putExtra("base", bundle);
        intent.putExtra("pid", item.getPid());
        intent.putParcelableArrayListExtra("items", items);
        startActivity(intent);
    }
}
