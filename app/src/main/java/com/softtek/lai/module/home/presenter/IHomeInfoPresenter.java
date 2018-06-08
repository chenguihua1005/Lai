/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import com.softtek.lai.module.home.model.HomeInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface IHomeInfoPresenter {

    //获取主页信息
    void getHomeInfoData(SwipeRefreshLayout pull);

    //分页查询活动等信息flag=0表示更新1表示加载
    void getContentByPage( int page, int img_type);
}
