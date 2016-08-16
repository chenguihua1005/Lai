package com.softtek.lai.module.bodygame2pc.model;

/**
 * Created by lareina.qiao on 7/14/2016.
 */
public class StuHonorListModel {
    private String CreateDate;
    private String HonorName;
    private String HonorType;
    private String Value;
    private String HonorStatus;

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

    @Override
    public String toString() {
        return "StuHonorListModel{" +
                "CreateDate='" + CreateDate + '\'' +
                ", HonorName='" + HonorName + '\'' +
                ", HonorType='" + HonorType + '\'' +
                ", Value='" + Value + '\'' +
                ", HonorStatus='" + HonorStatus + '\'' +
                '}';
    }
}
