package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class ClassinfoModel {
    private List<ClassModel> ClassInfoList;
    private RongyuModel Honor;
    private List<TuijianModel> ListRec;
    private ZhaopianModel PhotoWall;
    private List<PartnersModel> PartnersList;


    public List<ClassModel> getClassInfoList() {
        return ClassInfoList;
    }

    public void setClassInfoList(List<ClassModel> classInfoList) {
        ClassInfoList = classInfoList;
    }

    public RongyuModel getHonor() {
        return Honor;
    }

    public void setHonor(RongyuModel honor) {
        Honor = honor;
    }

    public List<TuijianModel> getListRec() {
        return ListRec;
    }

    public void setListRec(List<TuijianModel> listRec) {
        ListRec = listRec;
    }

    public ZhaopianModel getPhotoWall() {
        return PhotoWall;
    }

    public void setPhotoWall(ZhaopianModel photoWall) {
        PhotoWall = photoWall;
    }

    public List<PartnersModel> getPartnersList() {
        return PartnersList;
    }

    public void setPartnersList(List<PartnersModel> partnersList) {
        PartnersList = partnersList;
    }

    @Override
    public String toString() {
        return "ClassinfoModel{" +
                ", Honor=" + Honor +
                ", ListRec=" + ListRec +
                ", PhotoWall=" + PhotoWall +
                ", PartnersList=" + PartnersList +
                '}';
    }
}
