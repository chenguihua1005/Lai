package com.softtek.lai.module.bodygame2pc.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class PCClmDetailModel {
    private String ClassId;
    private String ClassName;
    private String ClassBanner;
    private String EndDate;
    private String Gender;
    private String IsStar;
    private String IsTest;
    private String Photo;
    private String StartDate;
    private String SupName;
    private String UserName;
    private String firstweight;
    private String honorcnt;
    private String lastweight;
    private String loss;

    public String getClassBanner() {
        return ClassBanner;
    }

    public void setClassBanner(String classBanner) {
        ClassBanner = classBanner;
    }

    @Override
    public String toString() {
        return "PCClmDetailModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassBanner='" + ClassBanner + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", Gender='" + Gender + '\'' +
                ", IsStar='" + IsStar + '\'' +
                ", IsTest='" + IsTest + '\'' +
                ", Photo='" + Photo + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", SupName='" + SupName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", firstweight='" + firstweight + '\'' +
                ", honorcnt='" + honorcnt + '\'' +
                ", lastweight='" + lastweight + '\'' +
                ", loss='" + loss + '\'' +
                '}';
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIsStar() {
        return IsStar;
    }

    public void setIsStar(String isStar) {
        IsStar = isStar;
    }

    public String getIsTest() {
        return IsTest;
    }

    public void setIsTest(String isTest) {
        IsTest = isTest;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getSupName() {
        return SupName;
    }

    public void setSupName(String supName) {
        SupName = supName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstweight() {
        return firstweight;
    }

    public void setFirstweight(String firstweight) {
        this.firstweight = firstweight;
    }

    public String getHonorcnt() {
        return honorcnt;
    }

    public void setHonorcnt(String honorcnt) {
        this.honorcnt = honorcnt;
    }

    public String getLastweight() {
        return lastweight;
    }

    public void setLastweight(String lastweight) {
        this.lastweight = lastweight;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }
}
