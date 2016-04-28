/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import butterknife.InjectView;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.DemoLoadMoreView;
import com.softtek.lai.module.home.adapter.DividerItemDecoration;
import com.softtek.lai.module.home.adapter.RecyclerViewAdapter;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
import com.softtek.lai.module.home.eventModel.RefreshEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.utils.ACache;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.base_fragment)
public class ActivityRecordFragment extends BaseFragment implements PullToRefreshRecyclerView.PagingableListener {

    @InjectView(R.id.ptrrv)
    PullToRefreshRecyclerView ptrrv;

    private List<HomeInfoModel> infos = new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page = 0;
    //下次加载插入的列表位置
    private int index = 0;

    private ACache aCache;
    private RecyclerViewAdapter adapter;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ptrrv.setSwipeEnable(false);
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(getContext(), ptrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("正在加载...");
        loadMoreView.setLoadMorePadding(100);
        ptrrv.setLoadMoreFooter(loadMoreView);
        ptrrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ptrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        ptrrv.setPagingableListener(this);

    }


    @Override
    protected void initDatas() {
        aCache = ACache.get(getContext(), Constants.HOME_CACHE_DATA_DIR);
        page = 0;
        homeInfoPresenter = new HomeInfoImpl(getContext());
        adapter = new RecyclerViewAdapter(getContext(), infos);
        ptrrv.setAdapter(adapter);
        ptrrv.onFinishLoading(true, true);

    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshView(ActivityEvent activity) {
        if (activity.flag == 0) {
            infos.clear();
            infos.addAll(activity.activitys);
        } else {
            //插入上次数据的末尾
            infos.addAll(index, activity.activitys);
        }
        adapter.notifyDataSetChanged();
        aCache.put(Constants.HOEM_ACTIVITY_KEY, new Gson().toJson(new HomeInfoCache(infos)));
    }

    @Override
    public void onLoadMoreItems() {
        System.out.println("加载啦....");
        homeInfoPresenter.getContentByPage(1, page, Constants.ACTIVITY_RECORD);

    }


    @Subscribe
    public void onRefreshEnd(RefreshEvent event) {
        System.out.print("加载结束了");
        if (event.flag == Constants.ACTIVITY_RECORD) {
            if (event.result) {
                page++;
            }
            ptrrv.onFinishLoading(true, true);
        }
    }

    public void updateInfo(List<HomeInfoModel> records){
        infos.clear();
        infos.addAll(records);
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

}
