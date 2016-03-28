package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassId implements Serializable {

    private String ClassId;     //班级id

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    @Override
    public String toString() {
        return "ClassId{" +
                "ClassId='" + ClassId + '\'' +
                '}';
    }
}
