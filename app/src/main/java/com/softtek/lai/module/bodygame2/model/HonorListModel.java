package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class HonorListModel {
    private String CreateDate;
    private String HonorName;
    private String HonorType;
    private String Value;
    private String HonorStatus;

    public HonorListModel(String createDate, String honorName, String honorType, String value, String honorStatus) {
        CreateDate = createDate;
        HonorName = honorName;
        HonorType = honorType;
        Value = value;
        HonorStatus = honorStatus;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getHonorName() {
        return HonorName;
    }

    public void setHonorName(String honorName) {
        HonorName = honorName;
    }

    public String getHonorType() {
        return HonorType;
    }

    public void setHonorType(String honorType) {
        HonorType = honorType;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getHonorStatus() {
        return HonorStatus;
    }

    public void setHonorStatus(String honorStatus) {
        HonorStatus = honorStatus;
    }
}
