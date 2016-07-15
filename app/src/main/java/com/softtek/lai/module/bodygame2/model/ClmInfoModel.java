package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class ClmInfoModel {
    private String AssistantId;
    private String ClassName;
    private String EndDate;
    private String HXAccountId;
    private String Mobile;
    private String Photo;
    private String StartDate;
    private String SuperName;
    private String SuperType;
    private String UserName;
    private String Gender;
    private String afterImage;
    private String afterWeight;
    private String beforeImage;
    private String beforeWeight;
    private String totalLoss;
    private String Istest;
    private String typedate;
    private String IsRetire;

    public ClmInfoModel(String assistantId, String className, String endDate, String HXAccountId, String mobile, String photo, String startDate, String superName, String superType, String userName, String gender, String afterImage, String afterWeight, String beforeImage, String beforeWeight, String totalLoss, String istest, String typedate, String isRetire) {
        AssistantId = assistantId;
        ClassName = className;
        EndDate = endDate;
        this.HXAccountId = HXAccountId;
        Mobile = mobile;
        Photo = photo;
        StartDate = startDate;
        SuperName = superName;
        SuperType = superType;
        UserName = userName;
        Gender = gender;
        this.afterImage = afterImage;
        this.afterWeight = afterWeight;
        this.beforeImage = beforeImage;
        this.beforeWeight = beforeWeight;
        this.totalLoss = totalLoss;
        Istest = istest;
        this.typedate = typedate;
        IsRetire = isRetire;
    }

    public String getIsRetire() {
        return IsRetire;
    }

    public void setIsRetire(String isRetire) {
        IsRetire = isRetire;
    }

    public String getAssistantId() {
        return AssistantId;
    }

    public void setAssistantId(String assistantId) {
        AssistantId = assistantId;
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

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
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

    public String getSuperName() {
        return SuperName;
    }

    public void setSuperName(String superName) {
        SuperName = superName;
    }

    public String getSuperType() {
        return SuperType;
    }

    public void setSuperType(String superType) {
        SuperType = superType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }

    public String getAfterWeight() {
        return afterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        this.afterWeight = afterWeight;
    }

    public String getBeforeImage() {
        return beforeImage;
    }

    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }

    public String getBeforeWeight() {
        return beforeWeight;
    }

    public void setBeforeWeight(String beforeWeight) {
        this.beforeWeight = beforeWeight;
    }

    public String getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(String totalLoss) {
        this.totalLoss = totalLoss;
    }

    public String getIstest() {
        return Istest;
    }

    public void setIstest(String istest) {
        Istest = istest;
    }

    public String getTypedate() {
        return typedate;
    }

    public void setTypedate(String typedate) {
        this.typedate = typedate;
    }
}
