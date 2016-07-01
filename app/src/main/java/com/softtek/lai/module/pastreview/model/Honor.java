package com.softtek.lai.module.pastreview.model;

/**
 * Created by jerry.guan on 6/30/2016.
 * 荣誉
 */
public class Honor {

    private String HonorType;
    private String HonorName;
    private String HonorStatus;
    private String Value;
    private String CreateDate;

    public String getHonorType() {
        return HonorType;
    }

    public void setHonorType(String honorType) {
        HonorType = honorType;
    }

    public String getHonorName() {
        return HonorName;
    }

    public void setHonorName(String honorName) {
        HonorName = honorName;
    }

    public String getHonorStatus() {
        return HonorStatus;
    }

    public void setHonorStatus(String honorStatus) {
        HonorStatus = honorStatus;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
