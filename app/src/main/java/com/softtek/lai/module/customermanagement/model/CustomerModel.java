package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerModel {
    private String Name;
    private String remark;
    private String Tag;//标签
    private String UserRole;//角色
    private String Photo;//头像
    private String Creator;//添加人姓名
    private String Mobile;//手机号码

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "Name='" + Name + '\'' +
                ", remark='" + remark + '\'' +
                ", Tag='" + Tag + '\'' +
                ", UserRole='" + UserRole + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Creator='" + Creator + '\'' +
                ", Mobile='" + Mobile + '\'' +
                '}';
    }
}
