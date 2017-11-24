package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.CustomerAdapter;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.module.customermanagement.model.CustomerModel;
import com.softtek.lai.module.customermanagement.presenter.IntendCustomerPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

@InjectLayout(R.layout.fragment_customer)
public class IntendCustomerFragment extends LazyBaseFragment<IntendCustomerPresenter> implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, IntendCustomerPresenter.IntendCustomerCallback {
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;


    private CustomerAdapter customerAdapter;
    private List<CustomerModel> modelList = new ArrayList<CustomerModel>();
    private int pageindex = 1;
    private int pageSize = 10;

    public static Fragment getInstance() {
        Fragment fragment = new IntendCustomerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                plv_audit.setRefreshing();
            }

        }, 300);
    }

    @Override
    protected void initViews() {
        plv_audit.setOnItemClickListener(this);
        plv_audit.setMode(PullToRefreshBase.Mode.BOTH);
        plv_audit.setOnRefreshListener(this);
        plv_audit.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_audit.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_audit.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示


    }

    @Override
    protected void initDatas() {
        setPresenter(new IntendCustomerPresenter(this));

        customerAdapter = new CustomerAdapter(getContext(), modelList);
        plv_audit.setAdapter(customerAdapter);
        getPresenter().getIntentCustomerList(pageindex, pageSize);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), CustomerDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex = 1;
        modelList.clear();
        getPresenter().getIntentCustomerList(pageindex, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex++;
        getPresenter().getIntentCustomerList(pageindex, pageSize);
    }

    @Override
    public void getIntentCustomerList(CustomerListModel model) {
        if (model.getItems()!=null) {
            modelList.addAll(model.getItems());
        }
        customerAdapter.notifyDataSetChanged();
    }

    @Override
    public void hidenLoading() {
        plv_audit.onRefreshComplete();
    }
}
