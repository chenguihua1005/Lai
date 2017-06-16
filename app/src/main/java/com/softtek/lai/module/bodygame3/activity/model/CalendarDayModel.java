package com.softtek.lai.module.bodygame3.activity.model;

import com.softtek.lai.widgets.materialcalendarview.CalendarDay;

/**
 * Created by jerry.guan on 6/16/2017.
 */

public class CalendarDayModel {

    private CalendarDay day;
    private String weekTh;

    public CalendarDayModel(CalendarDay day, String weekTh) {
        this.day = day;
        this.weekTh = weekTh;
    }

    public CalendarDayModel() {
    }

    public CalendarDay getDay() {
        return day;
    }

    public void setDay(CalendarDay day) {
        this.day = day;
    }

    public String getWeekTh() {
        return weekTh;
    }

    public void setWeekTh(String weekTh) {
        this.weekTh = weekTh;
    }
}
