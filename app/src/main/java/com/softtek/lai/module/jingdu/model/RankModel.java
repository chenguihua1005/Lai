/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.model;

import java.util.List;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankModel {
    private List<TableModel> Table;
    private List<Table1Model> Table1;

    @Override
    public String toString() {
        return "RankModel{" +
                "Table=" + Table +
                ", Table1=" + Table1 +
                '}';
    }

    public List<TableModel> getTable() {
        return Table;
    }

    public void setTable(List<TableModel> table) {
        Table = table;
    }

    public List<Table1Model> getTable1() {
        return Table1;
    }

    public void setTable1(List<Table1Model> table1) {
        Table1 = table1;
    }
}
