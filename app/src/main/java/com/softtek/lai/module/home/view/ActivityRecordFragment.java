/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.LoadMoreRecyclerViewAdapter;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
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
public class ActivityRecordFragment extends BaseFragment {

    @InjectView(R.id.ptrrv)
    RecyclerView ptrrv;

    private List<HomeInfoModel> infos = new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page = 1;

    private LoadMoreRecyclerViewAdapter adapter;
    private static final int LOADCOUNT = 5;
    private int lastVisitableItem;
    private boolean isLoading = false;

    public static ActivityRecordFragment getInstance(ArrayList<HomeInfoModel> records) {
        Bundle data = new Bundle();
        data.putParcelableArrayList("datas", records);
        ActivityRecordFragment fragment = new ActivityRecordFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ptrrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ptrrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = adapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && count > LOADCOUNT && lastVisitableItem + 1 == count) {
                    if (!isLoading) {
                        isLoading = true;
                        //加载更多数据
                        page++;
                        homeInfoPresenter.getContentByPage(page, Constants.ACTIVITY_RECORD);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) ptrrv.getLayoutManager();
                lastVisitableItem = llm.findLastVisibleItemPosition();
            }
        });


    }


    @Override
    protected void initDatas() {
        homeInfoPresenter = new HomeInfoImpl(getContext());
        /*page=1;
        Bundle bundle=getArguments();
        infos=bundle.getParcelableArrayList("datas");*/
        adapter = new LoadMoreRecyclerViewAdapter(getContext(), infos);
        ptrrv.setAdapter(adapter);
        adapter.setOnItemClickListener(new LoadMoreRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ArticalDetailActivity.class);
                intent.putExtra("info", (Parcelable) infos.get(position));
                intent.putExtra("title", "活动推荐");
                startActivity(intent);
            }
        });
//        homeInfoPresenter.getContentByPage(page, Constants.ACTIVITY_RECORD);

    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshView(ActivityEvent activity) {
        isLoading = false;
        adapter.notifyItemRemoved(adapter.getItemCount());
        if (activity.activitys == null || activity.activitys.isEmpty()) {
            page = --page < 1 ? 1 : page;
            return;
        }
        //插入上次数据的末尾
        infos.addAll(activity.activitys);
        adapter.notifyDataSetChanged();
    }

    public void updateInfo(List<HomeInfoModel> records) {
        page = 1;
        if (infos == null) {
            infos = new ArrayList<>();
        } else {
            infos.clear();
        }
        infos.addAll(records);
        if (adapter == null) {
            adapter = new LoadMoreRecyclerViewAdapter(getContext(), infos);
            if (ptrrv != null) {
                ptrrv.setAdapter(adapter);
            }
        }
        adapter.notifyDataSetChanged();

    }

}
