package com.softtek.lai.module.bodygame3.head.model;

import java.io.Serializable;

/**
 * Created by 87356 on 2016/12/3.
 */
public class ListdateModel implements Serializable{
    private String DateName;//日期id
    private String DateValue;//日期

    public String getDateValue() {
        return DateValue;
    }

    public void setDateValue(String dateValue) {
        DateValue = dateValue;
    }

    public String getDateName() {
        return DateName;
    }

    public void setDateName(String dateName) {
        DateName = dateName;
    }

    public String toString() {
        return "ListdateModel{" +
                "DateName='" + DateName + '\'' +
                ", DateValue='" + DateValue + '\'' +
                '}';
    }
}
