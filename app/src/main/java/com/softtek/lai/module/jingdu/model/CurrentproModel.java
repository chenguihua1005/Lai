/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 3/29/2016.
 */
public class CurrentproModel {
    private String TotalWeight;
    private String TotalMember;
    private String TotalClass;

    public CurrentproModel(String totalWeight, String totalMember, String totalClass) {
        TotalWeight = totalWeight;
        TotalMember = totalMember;
        TotalClass = totalClass;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getTotalMember() {
        return TotalMember;
    }

    public void setTotalMember(String totalMember) {
        TotalMember = totalMember;
    }

    public String getTotalClass() {
        return TotalClass;
    }

    public void setTotalClass(String totalClass) {
        TotalClass = totalClass;
    }

    @Override
    public String toString() {
        return "CurrentproModel{" +
                "TotalWeight='" + TotalWeight + '\'' +
                ", TotalMember='" + TotalMember + '\'' +
                ", TotalClass='" + TotalClass + '\'' +
                '}';
    }
}
