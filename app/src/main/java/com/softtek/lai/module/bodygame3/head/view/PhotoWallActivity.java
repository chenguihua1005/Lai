package com.softtek.lai.module.bodygame3.head.view;

import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.community.adapter.HealthyCommunityAdapter;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.presenter.RecommentHealthyManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_photo_wall)
public class PhotoWallActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>,RecommentHealthyManager.RecommentHealthyManagerCallback {
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    @InjectView(R.id.empty)
    FrameLayout empty;

    private RecommentHealthyManager community;
    private HealthyCommunityAdapter adapter;
    private List<HealthyCommunityModel> communityModels=new ArrayList<>();
    int pageIndex=1;
    int totalPage=0;

    @Override
    protected void initViews() {
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void getRecommendDynamic(HealthyRecommendModel model) {

    }
}
