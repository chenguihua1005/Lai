package com.softtek.lai.module.healthyreport.model;

/**
 * Created by jerry.guan on 4/14/2017.
 */

public class HealthyItem {


    /**
     * title : 体重
     * value : 45.3
     * unit : kg
     * caption : 偏瘦
     * color : ff6666
     */

    private String title;
    private String value;
    private String unit;
    private String caption;
    private String color;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
