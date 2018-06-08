package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ActCalendarModel {
    private String MonthDate;//日期
    private int DateType;//类型
    private String Weekth;//第几周复测

    public String getWeekth() {
        return Weekth;
    }

    public void setWeekth(String weekth) {
        Weekth = weekth;
    }

    public String getMonthDate() {
        return MonthDate;
    }

    public void setMonthDate(String monthDate) {
        MonthDate = monthDate;
    }

    public int getDateType() {
        return DateType;
    }

    public void setDateType(int dateType) {
        DateType = dateType;
    }

    @Override
    public String toString() {
        return "ActCalendarModel{" +
                "MonthDate='" + MonthDate + '\'' +
                ", DateType=" + DateType +
                ", Weekth='" + Weekth + '\'' +
                '}';
    }
}
