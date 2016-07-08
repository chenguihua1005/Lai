/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2.view;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassSelectModel implements Serializable {

    private String ClassId;
    private String ClassName;
    private String ClassMonth;

    @Override
    public String toString() {
        return "ClassSelectModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassMonth='" + ClassMonth + '\'' +
                '}';
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getClassMonth() {
        return ClassMonth;
    }

    public void setClassMonth(String classMonth) {
        ClassMonth = classMonth;
    }
}
