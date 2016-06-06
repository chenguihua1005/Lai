/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ShareSRHonorModel implements Serializable {

    private String SumLoss;
    private String Num;

    @Override
    public String toString() {
        return "ShareSRHonorModel{" +
                "SumLoss='" + SumLoss + '\'' +
                ", Num='" + Num + '\'' +
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
}
