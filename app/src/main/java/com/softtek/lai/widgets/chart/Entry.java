package com.softtek.lai.widgets.chart;

/**
 * 每一个Entry对应一个X轴上的数据
 * Created by jerry.guan on 11/8/2016.
 */

public class Entry {

    private int index;
    private float val;

    public Entry(int index, float val) {
        this.index = index;
        this.val = val;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public int getIndex() {
        return index;
    }

    public float getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "index=" + index +
                ", val=" + val +
                '}';
    }
}
