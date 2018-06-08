package com.softtek.lai.module.sportchart.model;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
public class StepListModel {
    String AccountId;
    String Date;
    String Orderby;
    String TotalCnt;

    @Override
    public String toString() {
        return "StepListModel{" +
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
