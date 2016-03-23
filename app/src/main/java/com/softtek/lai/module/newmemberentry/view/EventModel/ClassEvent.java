package com.softtek.lai.module.newmemberentry.view.EventModel;

import com.softtek.lai.module.newmemberentry.view.model.ClassList;

import java.util.List;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class ClassEvent {
    private List<ClassList> classLists;


    public List<ClassList> getClassLists() {
        return classLists;
    }

    public void setClassLists(List<ClassList> classLists) {
        this.classLists = classLists;
    }

    public ClassEvent(List<ClassList> classLists) {
        this.classLists = classLists;
    }
}
