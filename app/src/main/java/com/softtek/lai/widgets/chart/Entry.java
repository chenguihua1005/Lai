package com.softtek.lai.widgets.chart;

/**
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

}
