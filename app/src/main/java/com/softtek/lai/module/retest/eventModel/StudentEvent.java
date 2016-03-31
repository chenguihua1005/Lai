/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.StudentModel;

import java.util.List;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class StudentEvent {

    private List<StudentModel> studentModels;

    public List<StudentModel> getStudentModels() {
        return studentModels;
    }

    public void setStudentModels(List<StudentModel> studentModels) {
        this.studentModels = studentModels;
    }

    public StudentEvent(List<StudentModel> studentModels) {
        this.studentModels = studentModels;
    }
}
