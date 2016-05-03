package com.softtek.lai.module.mygrades.model;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class DayRankModel {
    private String orderInfo;
    private List<orderDataModel> orderData;

    public DayRankModel(List<orderDataModel> orderData, String orderInfo) {
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

    public List<orderDataModel> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<orderDataModel> orderData) {
        this.orderData = orderData;
    }
}
