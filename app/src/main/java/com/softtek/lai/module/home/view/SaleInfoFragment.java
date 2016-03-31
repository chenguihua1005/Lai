/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.InjectView;
import com.google.gson.Gson;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.DemoLoadMoreView;
import com.softtek.lai.module.home.adapter.DividerItemDecoration;
import com.softtek.lai.module.home.adapter.RecyclerViewAdapter;
import com.softtek.lai.module.home.cache.HomeInfoCache;
import com.softtek.lai.module.home.eventModel.RefreshEvent;
import com.softtek.lai.module.home.eventModel.SaleEvent;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.utils.ACache;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.base_fragment)
public class SaleInfoFragment extends BaseFragment implements PullToRefreshRecyclerView.PagingableListener {


    @InjectView(R.id.ptrrv)
    PullToRefreshRecyclerView ptrrv;

    private List<HomeInfo> infos = new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page = 0;
    //下次加载插入的列表位置
    private int index = 0;
    private ACache aCache;
    private RecyclerViewAdapter adapter;

    @Override
    protected void initViews() {
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
        //获取缓存数据
        List<HomeInfo> caches = homeInfoPresenter.loadActivityCacheDate(Constants.HOEM_SALE_KEY);
        infos.clear();
        if (caches == null) {
            index = 0;//下次加载从第0条插入
            for (int i = 0; i < 10; i++) {
                infos.add(new HomeInfo());
            }
        } else if (caches.size() < 10) {
            index = caches.size();//下次加载插入的位置
            for (int i = 0; i < 10 - infos.size(); i++) {
                caches.add(new HomeInfo());
            }
            infos.addAll(caches);
        }
        adapter = new RecyclerViewAdapter(getContext(), infos);
        ptrrv.setAdapter(adapter);
        ptrrv.onFinishLoading(true, true);
        homeInfoPresenter.getContentByPage(0, ++page, Constants.SALE_INFO);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshView(SaleEvent sale) {
        if (sale.flag == 0) {
            infos.clear();
            infos.addAll(sale.sales);
            System.out.println("这次是更新，目前数据有" + infos.size() + "条");
        } else {
            //插入上次数据的末尾
            infos.addAll(index, sale.sales);
            System.out.println("这次是添加插入，目前数据有" + infos.size() + "条");
        }
        index = index + sale.sales.size();
        if (infos.size() < 10) {
            int size = 10 - infos.size();
            System.out.println("数据小于10条需要添加" + size + "条");
            HomeInfo info = new HomeInfo();
            for (int i = 0; i < size; i++) {
                infos.add(info);
                System.out.println("添加了第" + (i + 1) + "条");
            }
        }
        System.out.println("当前数据大小....." + infos.size());
        adapter.notifyDataSetChanged();
        aCache.put(Constants.HOEM_SALE_KEY, new Gson().toJson(new HomeInfoCache(infos)));
    }

    @Override
    public void onLoadMoreItems() {
        System.out.println("加载啦....");
        homeInfoPresenter.getContentByPage(1, page, Constants.SALE_INFO);
    }

    @Subscribe
    public void onRefreshEnd(RefreshEvent event) {
        System.out.print("加载结束了");
        if (event.flag == Constants.SALE_INFO) {
            if (event.result) {
                page++;
            }
            ptrrv.onFinishLoading(true, true);
        }
    }


}
