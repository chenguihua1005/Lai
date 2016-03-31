/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.Student;

import java.util.List;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class StudentEvent {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public StudentEvent(List<Student> students) {
        this.students = students;
    }
}
