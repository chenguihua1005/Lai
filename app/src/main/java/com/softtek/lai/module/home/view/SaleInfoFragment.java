/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.LoadMoreRecyclerViewAdapter;
import com.softtek.lai.module.home.eventModel.SaleEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.base_fragment)
public class SaleInfoFragment extends BaseFragment{


    @InjectView(R.id.ptrrv)
    RecyclerView ptrrv;

    private List<HomeInfoModel> infos = new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page = 1;

    private LoadMoreRecyclerViewAdapter adapter;
    private static final int LOADCOUNT=5;
    private int lastVisitableItem;
    private boolean isLoading=false;

    @Override
    protected void initViews() {
        ptrrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ptrrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count=adapter.getItemCount();
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&count>LOADCOUNT&&lastVisitableItem+1==count){
                    if(!isLoading){
                        isLoading=true;
                        //加载更多数据
                        Log.i("开始加载更多");
                        page++;
                        homeInfoPresenter.getContentByPage( page, Constants.SALE_INFO);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm= (LinearLayoutManager) ptrrv.getLayoutManager();
                lastVisitableItem=llm.findLastVisibleItemPosition();
            }
        });

    }


    @Override
    protected void initDatas() {

        homeInfoPresenter = new HomeInfoImpl(getContext());
        adapter = new LoadMoreRecyclerViewAdapter(getContext(), infos);
        ptrrv.setAdapter(adapter);

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
        isLoading=false;
        adapter.notifyItemRemoved(adapter.getItemCount());
        if(sale.sales==null||sale.sales.isEmpty()){
            page=--page<1?1:page;
            return;
        }
        infos.addAll( sale.sales);
        adapter.notifyDataSetChanged();
    }

    public void updateInfo(List<HomeInfoModel> sales){
        page=1;
        infos.clear();
        infos.addAll(sales);
        adapter.notifyDataSetChanged();
    }

}
