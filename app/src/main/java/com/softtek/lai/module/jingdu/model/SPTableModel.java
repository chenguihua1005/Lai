package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 5/4/2016.
 */
public class SPTableModel {
    private String TotalWeight;//本月累计减重


    @Override
    public String toString() {
        return "TableModel{" +
                "TotalWeight='" + TotalWeight + '\'' +
                '}';
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

}
