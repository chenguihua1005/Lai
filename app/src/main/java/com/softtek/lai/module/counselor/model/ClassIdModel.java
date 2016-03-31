/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassIdModel implements Serializable {

    private String ClassId;     //班级id

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    @Override
    public String toString() {
        return "ClassIdModel{" +
                "ClassIdModel='" + ClassId + '\'' +
                '}';
    }
}
