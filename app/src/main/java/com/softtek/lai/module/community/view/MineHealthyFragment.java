package com.softtek.lai.module.community.view;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.community.presenter.CommunityImpl;
import com.softtek.lai.module.community.presenter.ICommunity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
@InjectLayout(R.layout.fragment_recommend_healthy)
public class MineHealthyFragment extends BaseFragment{

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private ICommunity community;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        community=new CommunityImpl();
        //获取健康推荐动态
        community.getRecommendDynamic();

    }
}
