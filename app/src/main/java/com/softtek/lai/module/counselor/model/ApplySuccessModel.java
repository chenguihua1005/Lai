/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ApplySuccessModel implements Serializable {

    private String applyerId;
    private String classManagerId;
    private String classId;
    private String comments;

    public String getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(String applyerId) {
        this.applyerId = applyerId;
    }

    public String getClassManagerId() {
        return classManagerId;
    }

    public void setClassManagerId(String classManagerId) {
        this.classManagerId = classManagerId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ApplySuccessModel{" +
                "applyerId='" + applyerId + '\'' +
                ", classManagerId='" + classManagerId + '\'' +
                ", classId='" + classId + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
