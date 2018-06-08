package com.softtek.lai.module.customermanagement.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.customermanagement.adapter.RemarkAdapter;
import com.softtek.lai.module.customermanagement.model.RemarkItemModel;
import com.softtek.lai.module.customermanagement.model.RemarkModel;
import com.softtek.lai.module.customermanagement.presenter.RemarkListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/17/2017.  PullToRefreshBase.OnRefreshListener2<ListView>
 */


@InjectLayout(R.layout.fragment_remark)
public class RemarkFragment extends LazyBaseFragment<RemarkListPresenter> implements PullToRefreshBase.OnRefreshListener2, RemarkListPresenter.RemarkListCallback {
    @InjectView(R.id.plv_history)
    PullToRefreshListView plv_history;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mRefresh;

    private static String mobile = "";


    private int pageindex = 1;
    private List<RemarkItemModel> remarkModels = new ArrayList<>();
    private RemarkAdapter adapter;

    public static Fragment getInstance(String mobileNum) {
        Fragment fragment = new RemarkFragment();
        mobile = mobileNum;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                plv_history.setRefreshing();
//            }
//        }, 300);

//        plv_history.onRefreshComplete();

    }

    @Override
    public void initViews() {

        plv_history.setMode(PullToRefreshBase.Mode.DISABLED);
        plv_history.setOnRefreshListener(this);
        plv_history.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_history.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_history.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示

        setPresenter(new RemarkListPresenter(this));
        adapter = new RemarkAdapter(remarkModels, getContext());
        plv_history.setAdapter(adapter);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(UPDATE_REMARKLIST));

        dialogShow(getString(R.string.loading));
        getPresenter().getRemarkList("", mobile, 1, 100);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getRemarkList("", mobile, 1, 100);
            }
        });


    }

    @Override
    protected void initDatas() {

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void getRemarkList(RemarkModel model) {
        if (mRefresh.isRefreshing()){
            mRefresh.setRefreshing(false);
        }
        remarkModels.clear();
        if (model != null) {
            remarkModels.addAll(model.getItems());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void dialogDissmiss() {
        super.dialogDissmiss();
        if (mRefresh.isRefreshing()){
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    public static final String UPDATE_REMARKLIST = "UPDATE_REMARKLIST";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equalsIgnoreCase(UPDATE_REMARKLIST)) {
                getPresenter().getRemarkList("", mobile, 1, 100);
            }
        }
    };
}
