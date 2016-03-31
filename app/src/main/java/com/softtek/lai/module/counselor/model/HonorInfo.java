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
public class HonorInfo implements Serializable {

    private List<HonorTable> Table;
    private List<HonorTable1> Table1;

    public List<HonorTable> getTable() {
        return Table;
    }

    public void setTable(List<HonorTable> table) {
        Table = table;
    }

    public List<HonorTable1> getTable1() {
        return Table1;
    }

    public void setTable1(List<HonorTable1> table1) {
        Table1 = table1;
    }

    @Override
    public String toString() {
        return "HonorInfo{" +
                "Table=" + Table +
                ", Table1=" + Table1 +
                '}';
    }
}
