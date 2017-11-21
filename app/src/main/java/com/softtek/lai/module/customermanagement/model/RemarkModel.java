package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

public class RemarkModel {
    private String name;
    private String remark;
    private String time;

    public RemarkModel(String name, String remark, String time) {
        this.name = name;
        this.remark = remark;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
