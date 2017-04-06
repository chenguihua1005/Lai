package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/6/2017.
 */

public class WeightModel {
    private String caption;//
    private String color;//

    public WeightModel(String caption, String color) {
        this.caption = caption;
        this.color = color;
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
