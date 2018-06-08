package com.softtek.lai.module.sport2.model;

/**
 * Created by julie.zhu on 5/11/2016.
 * 最近3个荣誉榜
 */
public class LaiHonorModel {
    private String CreateDate;
    private int HonorType;// --1,天数，2步数，3天使听见爱，4 PK挑战
    private int HonorVlue;//	--HonorType为1、2、4时有值，3时没值

    public LaiHonorModel(String createDate, int honorType, int honorVlue) {
        CreateDate = createDate;
        HonorType = honorType;
        HonorVlue = honorVlue;
    }

    @Override
    public String toString() {
        return "LaiHonorModel{" +
                "CreateDate='" + CreateDate + '\'' +
                ", HonorType=" + HonorType +
                ", HonorVlue=" + HonorVlue +
                '}';
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getHonorType() {
        return HonorType;
    }

    public void setHonorType(int honorType) {
        HonorType = honorType;
    }

    public int getHonorVlue() {
        return HonorVlue;
    }

    public void setHonorVlue(int honorVlue) {
        HonorVlue = honorVlue;
    }
}
