package com.softtek.lai.module.grade.eventModel;

import com.softtek.lai.module.grade.model.Student;

import java.util.List;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class LossWeightEvent {

    private List<Student> students;

    public LossWeightEvent(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}