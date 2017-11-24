package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerModel {
    private long AccountId;//用户编号/莱聚+账号 (市场人员使用)
    private String Mobile;//手机号码
    private String Name;//姓名
    private String Tag;//标签
    private String Photo;//头像
    private String Creator;//添加人姓名
    private String CreatedTime;//添加时间

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "AccountId=" + AccountId +
                ", Mobile='" + Mobile + '\'' +
                ", Name='" + Name + '\'' +
                ", Tag='" + Tag + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Creator='" + Creator + '\'' +
                ", CreatedTime='" + CreatedTime + '\'' +
                '}';
    }
}
