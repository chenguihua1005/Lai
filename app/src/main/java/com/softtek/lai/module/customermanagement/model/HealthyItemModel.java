package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class HealthyItemModel {
    private int pid;//指标编号
    private String pidName;//指标代码
    private String title;//指标名称
    private String value;//指标值
    private String unit;//指标单位
    private String caption;//指标档位
    private String color;//指标档位颜色

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
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

    @Override
    public String toString() {
        return "HealthyItemModel{" +
                "pid=" + pid +
                ", pidName='" + pidName + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", caption='" + caption + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
