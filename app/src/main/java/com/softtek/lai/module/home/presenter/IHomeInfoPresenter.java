package com.softtek.lai.module.home.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.softtek.lai.widgets.SuperSwipeRefreshLayout;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface IHomeInfoPresenter {

    void loadCacheData();

    //获取主页信息
    void getHomeInfoData(SwipeRefreshLayout pull);

    //分页查询活动等信息
    void getContentByPage(int page, int img_type, SuperSwipeRefreshLayout pull, ProgressBar footerProgressBar, ImageView footerImageView);
}
