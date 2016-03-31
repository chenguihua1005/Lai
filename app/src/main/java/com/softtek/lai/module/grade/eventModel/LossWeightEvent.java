/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.eventModel;

import com.softtek.lai.module.grade.model.StudentModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class LossWeightEvent {

    private List<StudentModel> students;

    public LossWeightEvent(List<StudentModel> studentModels) {
        this.students = studentModels;
    }

    public List<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentModel> studentModels) {
        this.students = studentModels;
    }
}
