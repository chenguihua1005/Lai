package com.softtek.lai.module.bodygamest.model;

/**
 * Created by lareina.qiao on 4/29/2016.
 */
public class CountWeekModel {
    private String count;

    @Override
    public String toString() {
        return "CountWeekModel{" +
                "count='" + count + '\'' +
                '}';
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
