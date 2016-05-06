package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/3/2016.
 * 2.20.1	我的成绩
 */
public class GradesModel {
    private String AccountId;//用户账号
    private String Date;//日期
    private String Orderby;//排名
    private String TotalCnt;//总步数

    public GradesModel(String accountId, String date, String orderby, String totalCnt) {
        AccountId = accountId;
        Date = date;
        Orderby = orderby;
        TotalCnt = totalCnt;
    }

    @Override
    public String toString() {
        return "GradesModel{" +
                "AccountId=" + AccountId +
                ", Date='" + Date + '\'' +
                ", Orderby=" + Orderby +
                ", TotalCnt=" + TotalCnt +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOrderby() {
        return Orderby;
    }

    public void setOrderby(String orderby) {
        Orderby = orderby;
    }

    public String getTotalCnt() {
        return TotalCnt;
    }

    public void setTotalCnt(String totalCnt) {
        TotalCnt = totalCnt;
    }
}
