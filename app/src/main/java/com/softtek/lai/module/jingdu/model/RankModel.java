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
    private List<Table2Model> Table2;

    @Override
    public String toString() {
        return "RankModel{" +
                "Table=" + Table +
                ", Table1=" + Table1 +
                ", Table2=" + Table2 +
                '}';
    }

    public RankModel(List<TableModel> table, List<Table1Model> table1, List<Table2Model> table2) {
        Table = table;
        Table1 = table1;
        Table2 = table2;
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

    public List<Table2Model> getTable2() {
        return Table2;
    }

    public void setTable2(List<Table2Model> table2) {
        Table2 = table2;
    }
}
