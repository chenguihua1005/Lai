package com.softtek.lai.module.healthchart.model;

import java.util.List;

/**
 * Created by John on 2016/4/13.
 */
public class HealthDateModel {
    private List<WeekDateModel> WeekDate;
    private List<MonthDateModel> MonthDate;
    private List<SeasonDateModel> SeasonDate;
    private List<SeasonDateModel> YearDate;

    @Override
    public String toString() {
        return "HealthDateModel{" +
                "WeekDate=" + WeekDate +
                ", MonthDate=" + MonthDate +
                ", SeasonDate=" + SeasonDate +
                ", YearDate=" + YearDate +
                '}';
    }

    public List<WeekDateModel> getWeekDate() {
        return WeekDate;
    }

    public void setWeekDate(List<WeekDateModel> weekDate) {
        WeekDate = weekDate;
    }

    public List<MonthDateModel> getMonthDate() {
        return MonthDate;
    }

    public void setMonthDate(List<MonthDateModel> monthDate) {
        MonthDate = monthDate;
    }

    public List<SeasonDateModel> getSeasonDate() {
        return SeasonDate;
    }

    public void setSeasonDate(List<SeasonDateModel> seasonDate) {
        SeasonDate = seasonDate;
    }

    public List<SeasonDateModel> getYearDate() {
        return YearDate;
    }

    public void setYearDate(List<SeasonDateModel> yearDate) {
        YearDate = yearDate;
    }
}
