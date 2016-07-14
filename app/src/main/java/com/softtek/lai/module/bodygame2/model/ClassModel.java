package com.softtek.lai.module.bodygame2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClassModel {
    private List<ClassListModel> Classlist;

    @Override
    public String toString() {
        return "ClassModel{" +
                "Classlist=" + Classlist +
                '}';
    }

    public List<ClassListModel> getClasslist() {
        return Classlist;
    }

    public void setClasslist(List<ClassListModel> classlist) {
        Classlist = classlist;
    }
}
