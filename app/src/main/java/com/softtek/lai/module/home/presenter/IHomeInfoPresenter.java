package com.softtek.lai.module.home.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.widgets.SuperSwipeRefreshLayout;

import java.util.List;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface IHomeInfoPresenter {

    void loadCacheData();

    //获取主页信息
    void getHomeInfoData(SwipeRefreshLayout pull);

    //分页查询活动等信息flag=0表示更新1表示加载
    void getContentByPage(int flag,int page, int img_type);

    /*
    3个活动缓存
     */
    List<HomeInfo> loadActivityCacheDate(String key);
}
