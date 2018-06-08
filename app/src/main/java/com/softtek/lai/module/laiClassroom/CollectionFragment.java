package com.softtek.lai.module.laiClassroom;


import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laiClassroom.adapter.CollectAdapter;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.model.CollectlistModel;
import com.softtek.lai.module.laiClassroom.presenter.CollectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//收藏页面
@InjectLayout(R.layout.fragment_collection)
public class CollectionFragment extends LazyBaseFragment<CollectPresenter> implements CollectPresenter.getcollect, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.plv_collect)
    PullToRefreshListView plv_collect;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;

    private List<CollectlistModel> collectlistModels = new ArrayList<>();
    private int pageindex = 1;
    CollectAdapter adapter;

    //18629094909
    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                plv_collect.setRefreshing();

            }

        }, 300);
    }

    @Override
    protected void initViews() {
//        plv_audit.setOnItemClickListener(this);
        plv_collect.setMode(PullToRefreshBase.Mode.BOTH);
        plv_collect.setOnRefreshListener(this);
        plv_collect.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_collect.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_collect.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        setPresenter(new CollectPresenter(this));
        adapter = new CollectAdapter(getContext(), collectlistModels);
        plv_collect.setAdapter(adapter);
    }

    @Override
    public void getcollects(CollectModel collectModel,int from) {
        plv_collect.onRefreshComplete();
        if (collectModel != null) {
            im_nomessage.setVisibility(View.GONE);
            if(!collectModel.getArticleList().isEmpty()){
                if(from==0){
                    collectlistModels.clear();
                }
                collectlistModels.addAll(collectModel.getArticleList());
                adapter.notifyDataSetChanged();
            }else {
                if (pageindex == 1) {
                    im_nomessage.setVisibility(View.VISIBLE);
                } else {
                    im_nomessage.setVisibility(View.GONE);
                    pageindex--;
                }
            }
        } else {
            im_nomessage.setVisibility(View.VISIBLE);
            pageindex--;
        }
    }

    //下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex = 1;
        getPresenter().getcollectarticle(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), pageindex, 10,0);
    }

    //上拉加载
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex++;
        getPresenter().getcollectarticle(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), pageindex, 10,1);


    }

    @Override
    public void dialogDissmiss() {
        plv_collect.onRefreshComplete();
    }
}
