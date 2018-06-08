package com.softtek.lai.module.ranking.model;

import java.util.List;

/**
 * Created by jerry.guan on 10/18/2016.
 */
public class RankModel {

    private String PageCount;//--总页数
    private String orderInfo;//--当前用户排名
    private String AcStepGuid;//--今日步数guid
    private String orderPhoto;//--当前用户头像
    private String orderName;//--当前用户姓名
    private String orderMobile;//--当前用户手机号
    private String orderSteps;//--当前用户步数
    private String IsPrasie;//--当前用户是否点赞		0：未点赞
    private String PrasieNum;//--点赞数
    private String orderRGName;//--当前用户所参加的跑团

    private List<OrderData> orderData;

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getAcStepGuid() {
        return AcStepGuid;
    }

    public void setAcStepGuid(String acStepGuid) {
        AcStepGuid = acStepGuid;
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

    public String getIsPrasie() {
        return IsPrasie;
    }

    public void setIsPrasie(String isPrasie) {
        IsPrasie = isPrasie;
    }

    public String getPrasieNum() {
        return PrasieNum;
    }

    public void setPrasieNum(String prasieNum) {
        PrasieNum = prasieNum;
    }

    public String getOrderRGName() {
        return orderRGName;
    }

    public void setOrderRGName(String orderRGName) {
        this.orderRGName = orderRGName;
    }

    public List<OrderData> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderData> orderData) {
        this.orderData = orderData;
    }
}
