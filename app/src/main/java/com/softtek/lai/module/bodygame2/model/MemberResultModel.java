package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class MemberResultModel {
    private String AccountId;
    private String AssistantName;
    private String superName;
    private String ClassId;
    private String ClassName;
    private String HonCou;
    private String InitWt;
    private String LastWt;
    private String LossW;
    private int Num;
    private int StarCou;
    private String UserName;
    private int acmCou;
    private int Gender;
    private String PCPhoto;



    public MemberResultModel() {
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getPCPhoto() {
        return PCPhoto;
    }

    public void setPCPhoto(String PCPhoto) {
        this.PCPhoto = PCPhoto;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAssistantName() {
        return AssistantName;
    }

    public void setAssistantName(String assistantName) {
        AssistantName = assistantName;
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

    public String getHonCou() {
        return HonCou;
    }

    public void setHonCou(String honCou) {
        HonCou = honCou;
    }

    public String getInitWt() {
        return InitWt;
    }

    public void setInitWt(String initWt) {
        InitWt = initWt;
    }

    public String getLastWt() {
        return LastWt;
    }

    public void setLastWt(String lastWt) {
        LastWt = lastWt;
    }

    public String getLossW() {
        return LossW;
    }

    public void setLossW(String lossW) {
        LossW = lossW;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getStarCou() {
        return StarCou;
    }

    public void setStarCou(int starCou) {
        StarCou = starCou;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getAcmCou() {
        return acmCou;
    }

    public void setAcmCou(int acmCou) {
        this.acmCou = acmCou;
    }
}
