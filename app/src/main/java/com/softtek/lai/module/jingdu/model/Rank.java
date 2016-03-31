/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class Rank {
    private long AccountId;
    private long ClassId;
    private int OrderNum;
    private String UserName;
    private String LossAfter;
    private String LossBefor;
    private String LossWeght;

    public Rank(String lossWeght, long accountId, long classId, int orderNum, String userName, String lossAfter, String lossBefor) {
        LossWeght = lossWeght;
        AccountId = accountId;
        ClassId = classId;
        OrderNum = orderNum;
        UserName = userName;
        LossAfter = lossAfter;
        LossBefor = lossBefor;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public long getClassId() {
        return ClassId;
    }

    public void setClassId(long classId) {
        ClassId = classId;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLossAfter() {
        return LossAfter;
    }

    public void setLossAfter(String lossAfter) {
        LossAfter = lossAfter;
    }

    public String getLossBefor() {
        return LossBefor;
    }

    public void setLossBefor(String lossBefor) {
        LossBefor = lossBefor;
    }

    public String getLossWeght() {
        return LossWeght;
    }

    public void setLossWeght(String lossWeght) {
        LossWeght = lossWeght;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "AccountId=" + AccountId +
                ", ClassId=" + ClassId +
                ", OrderNum=" + OrderNum +
                ", UserName='" + UserName + '\'' +
                ", LossAfter='" + LossAfter + '\'' +
                ", LossBefor='" + LossBefor + '\'' +
                ", LossWeght='" + LossWeght + '\'' +
                '}';
    }
}
