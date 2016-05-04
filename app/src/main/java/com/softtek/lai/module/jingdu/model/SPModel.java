package com.softtek.lai.module.jingdu.model;

import java.util.List;

/**
 * Created by julie.zhu on 5/4/2016.
 */
public class SPModel {
    private List<SPTableModel> spTable;
    private List<Table1Model> Table1;
    private List<Table2Model> Table2;
    private List<PaimingModel> paiming;

    public SPModel(List<SPTableModel> spTable, List<Table1Model> table1, List<Table2Model> table2, List<PaimingModel> paiming) {
        this.spTable = spTable;
        Table1 = table1;
        Table2 = table2;
        this.paiming = paiming;
    }

    @Override
    public String toString() {
        return "SPModel{" +
                "spTable=" + spTable +
                ", Table1=" + Table1 +
                ", Table2=" + Table2 +
                ", paiming=" + paiming +
                '}';
    }

    public List<PaimingModel> getPaiming() {
        return paiming;
    }

    public void setPaiming(List<PaimingModel> paiming) {
        this.paiming = paiming;
    }

    public List<SPTableModel> getSpTable() {
        return spTable;
    }

    public void setSpTable(List<SPTableModel> spTable) {
        this.spTable = spTable;
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
