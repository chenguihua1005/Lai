/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.eventModel;

import com.softtek.lai.module.home.model.HomeInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class ProductEvent {

    public List<HomeInfoModel> products;
    //0:刷新 1：加载
    public int flag = 0;

    public ProductEvent(int flag, List<HomeInfoModel> products) {
        this.flag = flag;
        this.products = products;
    }

    public ProductEvent(List<HomeInfoModel> products) {
        this.products = products;
    }
}
