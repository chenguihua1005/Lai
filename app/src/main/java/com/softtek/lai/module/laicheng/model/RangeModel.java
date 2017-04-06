package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/6/2017.
 */

public class RangeModel {
    private int value;
    private String color;
    private String valuetip;

    public RangeModel(int value, String color, String valuetip) {
        this.value = value;
        this.color = color;
        this.valuetip = valuetip;
    }

    public String getValuetip() {
        return valuetip;
    }

    public void setValuetip(String valuetip) {
        this.valuetip = valuetip;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
