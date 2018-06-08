package com.softtek.lai.widgets.chart;

import java.util.List;

/**
 * Created by jerry.guan on 11/9/2016.
 */

public class DataLine {

    private List<String> xAxis;
    private List<Entry> entries;
    private float maxYAxis;

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public float getMaxYAxis() {
        return maxYAxis;
    }

    public void setMaxYAxis(float maxYAxis) {
        this.maxYAxis = maxYAxis;
    }
}
