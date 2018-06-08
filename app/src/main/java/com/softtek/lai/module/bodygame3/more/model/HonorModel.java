package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class HonorModel {

    private List<StudentHonorInfo> Table1;
    private List<StudentHonorTypeInfo> Table2;

    @Override
    public String toString() {
        return "HonorModel{" +
                "Table1=" + Table1 +
                ", Table2=" + Table2 +
                '}';
    }

    public List<StudentHonorInfo> getTable1() {
        return Table1;
    }

    public void setTable1(List<StudentHonorInfo> table1) {
        Table1 = table1;
    }

    public List<StudentHonorTypeInfo> getTable2() {
        return Table2;
    }

    public void setTable2(List<StudentHonorTypeInfo> table2) {
        Table2 = table2;
    }
}
