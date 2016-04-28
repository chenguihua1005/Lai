/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class UserHonorModel implements Serializable {

    private String SumLoss;
    private String Num;
    private String Days;

    @Override
    public String toString() {
        return "UserHonorModel{" +
                "SumLoss='" + SumLoss + '\'' +
                ", Num='" + Num + '\'' +
                ", Days='" + Days + '\'' +
                '}';
    }

    public String getSumLoss() {
        return SumLoss;
    }

    public void setSumLoss(String sumLoss) {
        SumLoss = sumLoss;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }
}
