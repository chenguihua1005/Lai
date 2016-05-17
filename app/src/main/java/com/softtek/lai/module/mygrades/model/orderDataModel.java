package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/3/2016.
 *  3.3.3	当日前100排名详情
 * 3.3.4	当周排前100名详情
 */
public class OrderDataModel {
    private String AccountId;
    private String Photo;
    private String _order;
    private String mobile;
    private String stepCount;
    private String userName;

    public OrderDataModel(String accountId, String photo, String _order, String mobile, String stepCount, String userName) {
        AccountId = accountId;
        Photo = photo;
        this._order = _order;
        this.mobile = mobile;
        this.stepCount = stepCount;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OrderDataModel{" +
                "AccountId='" + AccountId + '\'' +
                ", Photo='" + Photo + '\'' +
                ", _order='" + _order + '\'' +
                ", mobile='" + mobile + '\'' +
                ", stepCount='" + stepCount + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String get_order() {
        return _order;
    }

    public void set_order(String _order) {
        this._order = _order;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
