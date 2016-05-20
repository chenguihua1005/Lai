package com.softtek.lai.module.mygrades.model;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 * 3.3.3	当日前100排名详情
 * 3.3.4	当周排前100名详情
 */
public class DayRankModel {

    private String orderInfo;//当前用户排名
    private String orderPhoto;//--当前用户头像
    private String orderName;//	--当前用户姓名
    private String orderMobile;//--当前用户手机号
    private String orderSteps;//--当前用户步数
    private String orderRGName;//当前用户所参加的跑团
    private List<OrderDataModel> orderData;

    public DayRankModel(String orderInfo, String orderPhoto, String orderName, String orderMobile, String orderSteps, String orderRGName, List<OrderDataModel> orderData) {
        this.orderInfo = orderInfo;
        this.orderPhoto = orderPhoto;
        this.orderName = orderName;
        this.orderMobile = orderMobile;
        this.orderSteps = orderSteps;
        this.orderRGName = orderRGName;
        this.orderData = orderData;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderPhoto() {
        return orderPhoto;
    }

    public void setOrderPhoto(String orderPhoto) {
        this.orderPhoto = orderPhoto;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderMobile() {
        return orderMobile;
    }

    public void setOrderMobile(String orderMobile) {
        this.orderMobile = orderMobile;
    }

    public String getOrderSteps() {
        return orderSteps;
    }

    public void setOrderSteps(String orderSteps) {
        this.orderSteps = orderSteps;
    }

    public String getOrderRGName() {
        return orderRGName;
    }

    public void setOrderRGName(String orderRGName) {
        this.orderRGName = orderRGName;
    }

    public List<OrderDataModel> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDataModel> orderData) {
        this.orderData = orderData;
    }

    @Override
    public String toString() {
        return "DayRankModel{" +
                "orderInfo='" + orderInfo + '\'' +
                ", orderPhoto='" + orderPhoto + '\'' +
                ", orderName='" + orderName + '\'' +
                ", orderMobile='" + orderMobile + '\'' +
                ", orderSteps='" + orderSteps + '\'' +
                ", orderRGName='" + orderRGName + '\'' +
                ", orderData=" + orderData +
                '}';
    }
}
