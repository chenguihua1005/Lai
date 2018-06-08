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
public class SaleEvent {

    public List<HomeInfoModel> sales;
    //0:刷新 1：加载
    public int flag = 0;

    public SaleEvent(int flag, List<HomeInfoModel> sales) {
        this.sales = sales;
        this.flag = flag;
    }

    public SaleEvent(List<HomeInfoModel> sales) {
        this.sales = sales;
    }
}
