package com.softtek.lai.module.bodygame2.model;

/**
 * Created by jerry.guan on 7/11/2016.
 * SP的三个学员
 */
public class SPPCMoldel {

    private int AccountId;
    private String AssistantName;
    private int ClassId;
    private String ClassName;
    private int HonCou;
    private float InitWt;
    private float LastWt;
    private float LossW;
    private int Num;
    private int StarCou;
    private String UserName;
    private int acmCou;
    private int Gender;
    private String PCPhoto;

    public String getPCPhoto() {
        return PCPhoto;
    }

    public void setPCPhoto(String PCPhoto) {
        this.PCPhoto = PCPhoto;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getAssistantName() {
        return AssistantName;
    }

    public void setAssistantName(String assistantName) {
        AssistantName = assistantName;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getHonCou() {
        return HonCou;
    }

    public void setHonCou(int honCou) {
        HonCou = honCou;
    }

    public float getInitWt() {
        return InitWt;
    }

    public void setInitWt(float initWt) {
        InitWt = initWt;
    }

    public float getLastWt() {
        return LastWt;
    }

    public void setLastWt(float lastWt) {
        LastWt = lastWt;
    }

    public float getLossW() {
        return LossW;
    }

    public void setLossW(float lossW) {
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
