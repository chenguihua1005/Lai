/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HonorTableModel implements Serializable {

    private String rowname;     //排名种类
    private String num;     //具体数值
    private String rank_num;     //全国排名

    public String getRowname() {
        return rowname;
    }

    public void setRowname(String rowname) {
        this.rowname = rowname;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRank_num() {
        return rank_num;
    }

    public void setRank_num(String rank_num) {
        this.rank_num = rank_num;
    }

    @Override
    public String toString() {
        return "HonorTableModel{" +
                "rowname='" + rowname + '\'' +
                ", num='" + num + '\'' +
                ", rank_num='" + rank_num + '\'' +
                '}';
    }
}
