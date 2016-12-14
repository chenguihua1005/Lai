package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by 87356 on 2016/12/3.
 */
public class ListdateModel {
    private String DateName;//小组id
    private String DateValue;//小组名

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
