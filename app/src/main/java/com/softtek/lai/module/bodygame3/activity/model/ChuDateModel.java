package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by lareina.qiao on 2016/11/22.
 */
//获取初始数据界面基本信息
public class ChuDateModel {
    private String StuPhoto;//学员头像
    private String StuName ;//学员姓名
    private String StuPhone ;//学员手机号
    private String ClassName ;//班级名称
    private String Week  ;//班级所在周
    private String StaData  ;//开班日期
    private String EndData   ;//结束日期

    @Override
    public String toString() {
        return "ChuDateModel{" +
                "StuPhoto='" + StuPhoto + '\'' +
                ", StuName='" + StuName + '\'' +
                ", StuPhone='" + StuPhone + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", Week='" + Week + '\'' +
                ", StaData='" + StaData + '\'' +
                ", EndData='" + EndData + '\'' +
                '}';
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

    public String getStuPhone() {
        return StuPhone;
    }

    public void setStuPhone(String stuPhone) {
        StuPhone = stuPhone;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }

    public String getStaData() {
        return StaData;
    }

    public void setStaData(String staData) {
        StaData = staData;
    }

    public String getEndData() {
        return EndData;
    }

    public void setEndData(String endData) {
        EndData = endData;
    }
}
