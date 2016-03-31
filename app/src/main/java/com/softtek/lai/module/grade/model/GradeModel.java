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

    private List<PhotoInfoModel> PhotoInfo;

    private List<PeopleModel> SRInfo;

    private List<PeopleModel> PCInfo;

    private List<DynamicInfoModel> DynamicInfo;

    public List<GradeInfoModel> getClassInfo() {
        return ClassInfo;
    }

    public void setClassInfo(List<GradeInfoModel> classInfo) {
        ClassInfo = classInfo;
    }

    public List<PhotoInfoModel> getPhotoInfo() {
        return PhotoInfo;
    }

    public void setPhotoInfo(List<PhotoInfoModel> photoInfo) {
        PhotoInfo = photoInfo;
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

    public List<DynamicInfoModel> getDynamicInfo() {
        return DynamicInfo;
    }

    public void setDynamicInfo(List<DynamicInfoModel> dynamicInfo) {
        DynamicInfo = dynamicInfo;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "ClassInfoModel=" + ClassInfo +
                ", PhotoInfoModel=" + PhotoInfo +
                ", SRInfoModel=" + SRInfo +
                ", PCInfo=" + PCInfo +
                ", DynamicInfoModel=" + DynamicInfo +
                '}';
    }
}
