package com.softtek.lai.module.home.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

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
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.utils.ACache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.base_fragment)
public class ActivityRecordFragment extends BaseFragment implements PullToRefreshRecyclerView.PagingableListener{

    @InjectView(R.id.ptrrv)
    PullToRefreshRecyclerView ptrrv;

    private List<HomeInfo> infos=new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page=0;
    //下次加载插入的列表位置
    private int index=0;

    private ACache aCache;

    @Override
    protected void initViews() {
        ptrrv.setSwipeEnable(false);
        DemoLoadMoreView loadMoreView=new DemoLoadMoreView(getContext(),ptrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("正在加载...");
        loadMoreView.setLoadMorePadding(100);
        ptrrv.setLoadMoreFooter(loadMoreView);
        ptrrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ptrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        ptrrv.setPagingableListener(this);

    }



    @Override
    protected void initDatas() {
        aCache=ACache.get(getContext(),Constants.HOME_CACHE_DATA_DIR);
        page=0;
        homeInfoPresenter=new HomeInfoImpl(getContext());
        //获取缓存数据
        List<HomeInfo> caches=homeInfoPresenter.loadActivityCacheDate(Constants.HOEM_ACTIVITY_KEY);
        infos.clear();
        if(caches==null){
            index=0;//下次加载从第0条插入
            for(int i=0;i<10;i++){
                infos.add(new HomeInfo());
            }
        }else if(caches.size()<10){
            index=caches.size();//下次加载插入的位置
            for(int i=0;i<10-infos.size();i++){
                caches.add(new HomeInfo());
            }
            infos.addAll(caches);
        }
        ptrrv.setAdapter(new RecyclerViewAdapter(getContext(),infos));
        ptrrv.onFinishLoading(true, false);
        homeInfoPresenter.getContentByPage(0, ++page, 1);
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
    public void onRefreshView(ActivityEvent activity){
        if(activity.flag==0){
            infos.clear();
            infos.addAll(activity.activitys);
        }else {
            //插入上次数据的末尾
            infos.addAll(index,activity.activitys);
        }
        index=index+activity.activitys.size();
        if(infos.size()<10){
            for(int i=0;i<10-infos.size();i++){
                infos.add(new HomeInfo());
            }
        }

        ptrrv.getRecyclerView().getAdapter().notifyDataSetChanged();
        aCache.put(Constants.HOEM_ACTIVITY_KEY,new Gson().toJson(new HomeInfoCache(infos)));
    }

    @Override
    public void onLoadMoreItems() {
        System.out.println("加载啦....");
        homeInfoPresenter.getContentByPage(1, page, 1);
    }

    @Subscribe
    public void onRefreshEnd(RefreshEvent event){
        System.out.print("加载结束了");
        if(event.result){
            page++;
        }
        ptrrv.onFinishLoading(true,true);
    }


}
