package com.softtek.lai.module.mygrades.model;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 * 3.3.3	当日前100排名详情
 * 3.3.4	当周排前100名详情
 */
public class DayRankModel {
    private String orderInfo;
    private List<OrderDataModel> orderData;

    public DayRankModel(List<OrderDataModel> orderData, String orderInfo) {
        this.orderData = orderData;
        this.orderInfo = orderInfo;
    }

    @Override
    public String toString() {
        return "DayRankModel{" +
                "orderInfo='" + orderInfo + '\'' +
                ", orderData=" + orderData +
                '}';
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderDataModel> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDataModel> orderData) {
        this.orderData = orderData;
    }
}
