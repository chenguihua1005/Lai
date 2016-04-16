/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/21/2016.
 * 班级主页信息
 */
public class GradeModel {

    private List<GradeInfoModel> ClassInfo;

    //private List<PhotoInfoModel> PhotoInfo;

    private List<PeopleModel> SRInfo;

    private List<PeopleModel> PCInfo;

    public List<GradeInfoModel> getClassInfo() {
        return ClassInfo;
    }

    public void setClassInfo(List<GradeInfoModel> classInfo) {
        ClassInfo = classInfo;
    }

    public List<PeopleModel> getSRInfo() {
        return SRInfo;
    }

    public void setSRInfo(List<PeopleModel> SRInfo) {
        this.SRInfo = SRInfo;
    }

    public List<PeopleModel> getPCInfo() {
        return PCInfo;
    }

    public void setPCInfo(List<PeopleModel> PCInfo) {
        this.PCInfo = PCInfo;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "ClassInfo=" + ClassInfo +
                ", SRInfo=" + SRInfo +
                ", PCInfo=" + PCInfo +
                '}';
    }
}
