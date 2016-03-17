package com.softtek.lai.module.home.presenter;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface IHomeInfoPresenter {

    //获取主页信息
    void getHomeInfoData(PullToRefreshScrollView pull);
}
