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
public class Grade {

    private List<GradeInfo> ClassInfo;

    private List<PhotoInfo> PhotoInfo;

    private List<People> SRInfo;

    private List<People> PCInfo;

    private List<DynamicInfo> DynamicInfo;

    public List<GradeInfo> getClassInfo() {
        return ClassInfo;
    }

    public void setClassInfo(List<GradeInfo> classInfo) {
        ClassInfo = classInfo;
    }

    public List<com.softtek.lai.module.grade.model.PhotoInfo> getPhotoInfo() {
        return PhotoInfo;
    }

    public void setPhotoInfo(List<com.softtek.lai.module.grade.model.PhotoInfo> photoInfo) {
        PhotoInfo = photoInfo;
    }

    public List<People> getSRInfo() {
        return SRInfo;
    }

    public void setSRInfo(List<People> SRInfo) {
        this.SRInfo = SRInfo;
    }

    public List<People> getPCInfo() {
        return PCInfo;
    }

    public void setPCInfo(List<People> PCInfo) {
        this.PCInfo = PCInfo;
    }

    public List<com.softtek.lai.module.grade.model.DynamicInfo> getDynamicInfo() {
        return DynamicInfo;
    }

    public void setDynamicInfo(List<com.softtek.lai.module.grade.model.DynamicInfo> dynamicInfo) {
        DynamicInfo = dynamicInfo;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "ClassInfo=" + ClassInfo +
                ", PhotoInfo=" + PhotoInfo +
                ", SRInfo=" + SRInfo +
                ", PCInfo=" + PCInfo +
                ", DynamicInfo=" + DynamicInfo +
                '}';
    }
}
