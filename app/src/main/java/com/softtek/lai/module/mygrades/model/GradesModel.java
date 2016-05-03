package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class GradesModel {
    private String AccountId;
    private String Date;
    private String Orderby;
    private String TotalCnt;

    public GradesModel(String totalCnt, String accountId, String date, String orderby) {
        TotalCnt = totalCnt;
        AccountId = accountId;
        Date = date;
        Orderby = orderby;
    }

    @Override
    public String toString() {
        return "GradesModel{" +
                "AccountId='" + AccountId + '\'' +
                ", Date='" + Date + '\'' +
                ", Orderby='" + Orderby + '\'' +
                ", TotalCnt='" + TotalCnt + '\'' +
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
