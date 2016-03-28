package com.softtek.lai.module.home.eventModel;

import com.softtek.lai.module.home.model.HomeInfo;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class ProductEvent {

    public List<HomeInfo> products;
    //0:刷新 1：加载
    public int flag=0;

    public ProductEvent(int flag, List<HomeInfo> products) {
        this.flag = flag;
        this.products = products;
    }

    public ProductEvent(List<HomeInfo> products) {
        this.products = products;
    }
}
