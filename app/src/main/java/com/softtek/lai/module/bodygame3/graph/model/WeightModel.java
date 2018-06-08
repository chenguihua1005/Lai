package com.softtek.lai.module.bodygame3.graph.model;

/**
 * @author jerry.Guan
 *         created by 2016/12/3
 */

public class WeightModel {


    private long AccountId;
    private String ClassId;
    private String weight;
    private String pysical;
    private String fat;
    private String typeDate;
    private int WeekDay;
    private String ClassStart;
    private String ClassEnd;
    private String CurrentDate;
    private int CurrentWeekDay;

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long AccountId) {
        this.AccountId = AccountId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPysical() {
        return pysical;
    }

    public void setPysical(String pysical) {
        this.pysical = pysical;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(String typeDate) {
        this.typeDate = typeDate;
    }

    public int getWeekDay() {
        return WeekDay;
    }

    public void setWeekDay(int WeekDay) {
        this.WeekDay = WeekDay;
    }

    public String getClassStart() {
        return ClassStart;
    }

    public void setClassStart(String ClassStart) {
        this.ClassStart = ClassStart;
    }

    public String getClassEnd() {
        return ClassEnd;
    }

    public void setClassEnd(String ClassEnd) {
        this.ClassEnd = ClassEnd;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String CurrentDate) {
        this.CurrentDate = CurrentDate;
    }

    public int getCurrentWeekDay() {
        return CurrentWeekDay;
    }

    public void setCurrentWeekDay(int CurrentWeekDay) {
        this.CurrentWeekDay = CurrentWeekDay;
    }

    @Override
    public String toString() {
        return "WeightModel{" +
                "AccountId=" + AccountId +
                ", ClassId='" + ClassId + '\'' +
                ", weight='" + weight + '\'' +
                ", pysical='" + pysical + '\'' +
                ", fat='" + fat + '\'' +
                ", typeDate='" + typeDate + '\'' +
                ", WeekDay=" + WeekDay +
                ", ClassStart='" + ClassStart + '\'' +
                ", ClassEnd='" + ClassEnd + '\'' +
                ", CurrentDate='" + CurrentDate + '\'' +
                ", CurrentWeekDay=" + CurrentWeekDay +
                '}';
    }
}
