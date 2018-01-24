package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class RongyuModel {
    private String GroupName;//小组名称
    private String GroupLossPre;//小组减重比
    private String StuPhoto;//优秀学员照片
    private String StuName;//优秀学员姓名
    private String LossPre;//优秀学员减重比
    private String PysPre;//优秀学员减脂比
    private String ClassWeek;//

    private int Target;

    public RongyuModel(String groupName, String groupLossPre, String stuPhoto, String stuName, String lossPre, String pysPre, String classWeek) {
        GroupName = groupName;
        GroupLossPre = groupLossPre;
        StuPhoto = stuPhoto;
        StuName = stuName;
        LossPre = lossPre;
        PysPre = pysPre;
        ClassWeek = classWeek;
    }

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public String getClassWeek() {
        return ClassWeek;
    }

    public void setClassWeek(String classWeek) {
        ClassWeek = classWeek;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupLossPre() {
        return GroupLossPre;
    }

    public void setGroupLossPre(String groupLossPre) {
        GroupLossPre = groupLossPre;
    }

    public String getStuPhoto() {
        return StuPhoto;
    }

    public void setStuPhoto(String stuPhoto) {
        StuPhoto = stuPhoto;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getLossPre() {
        return LossPre;
    }

    public void setLossPre(String lossPre) {
        LossPre = lossPre;
    }

    public String getPysPre() {
        return PysPre;
    }

    public void setPysPre(String pysPre) {
        PysPre = pysPre;
    }

    @Override
    public String toString() {
        return "RongyuModel{" +
                "GroupName='" + GroupName + '\'' +
                ", GroupLossPre='" + GroupLossPre + '\'' +
                ", StuPhoto='" + StuPhoto + '\'' +
                ", StuName='" + StuName + '\'' +
                ", LossPre='" + LossPre + '\'' +
                ", PysPre='" + PysPre + '\'' +
                '}';
    }
}
