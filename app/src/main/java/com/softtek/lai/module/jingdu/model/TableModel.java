package com.softtek.lai.module.jingdu.model;

/**
 * Created by zcy on 2016/4/6.
 */
public class TableModel {
    private String TotalWeight;
    private String TotalMember;
    private String TotalClass;

    @Override
    public String toString() {
        return "TableModel{" +
                "TotalWeight='" + TotalWeight + '\'' +
                ", TotalMember='" + TotalMember + '\'' +
                ", TotalClass='" + TotalClass + '\'' +
                '}';
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
}
