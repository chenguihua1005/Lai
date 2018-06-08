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
import com.softtek.lai.module.laiClassroom.presenter.HistoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//历史页面
@InjectLayout(R.layout.fragment_history)
public class HistoryFragment extends LazyBaseFragment<HistoryPresenter> implements HistoryPresenter.getHistorydata, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.plv_history)
    PullToRefreshListView plv_history;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    private List<CollectlistModel> collectlistModels = new ArrayList<>();
    private int pageindex = 1;
    CollectAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override

            public void run() {

                plv_history.setRefreshing();

            }

        }, 300);
    }

    @Override
    protected void initViews() {
        plv_history.setMode(PullToRefreshBase.Mode.BOTH);
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
    }

    @Override
    protected void initDatas() {
        setPresenter(new HistoryPresenter(this));
        adapter = new CollectAdapter(getContext(), collectlistModels);
        plv_history.setAdapter(adapter);
    }

    @Override
    public void gethistorydata(CollectModel collectModel,int from) {
        plv_history.onRefreshComplete();
        if (collectModel != null) {
            im_nomessage.setVisibility(View.GONE);
            if (!collectModel.getArticleList().isEmpty()) {
                if(from==0){
                    collectlistModels.clear();
                }
                collectlistModels.addAll(collectModel.getArticleList());
               adapter.notifyDataSetChanged();
            } else {
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

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

        pageindex = 1;
        getPresenter().getVisitHistory(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), pageindex, 10,0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex++;
        getPresenter().getVisitHistory(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), pageindex, 10,1);

    }

    @Override
    public void dialogDissmiss() {
        plv_history.onRefreshComplete();
    }
}
