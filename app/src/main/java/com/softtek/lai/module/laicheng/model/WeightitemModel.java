package com.softtek.lai.module.laicheng.model;

import java.util.List;

/**
 * Created by shelly.xu on 4/6/2017.
 */

public class WeightitemModel {
    private int value;
    private String unit;
    private String color;
    private String arrowcolor;
    private String indexdescription;
    private List<RangeModel> range;

    public WeightitemModel(int value, String unit, String color, String arrowcolor, String indexdescription, List<RangeModel> range) {
        this.value = value;
        this.unit = unit;
        this.color = color;
        this.arrowcolor = arrowcolor;
        this.indexdescription = indexdescription;
        this.range = range;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getArrowcolor() {
        return arrowcolor;
    }

    public void setArrowcolor(String arrowcolor) {
        this.arrowcolor = arrowcolor;
    }

    public String getIndexdescription() {
        return indexdescription;
    }

    public void setIndexdescription(String indexdescription) {
        this.indexdescription = indexdescription;
    }

    public List<RangeModel> getRange() {
        return range;
    }

    public void setRange(List<RangeModel> range) {
        this.range = range;
    }
}
