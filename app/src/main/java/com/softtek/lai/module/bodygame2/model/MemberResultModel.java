package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class MemberResultModel {
    private String AccountId;
    private String AssistantName;
    private String ClassId;
    private String ClassName;
    private String HonCou;
    private String InitWt;
    private String LastWt;
    private String LossW;
    private String Num;
    private String StarCou;
    private String UserName;
    private String acmCou;

    public MemberResultModel(String accountId, String assistantName, String classId, String className, String honCou, String initWt, String lastWt, String lossW, String num, String starCou, String userName, String acmCou) {
        AccountId = accountId;
        AssistantName = assistantName;
        ClassId = classId;
        ClassName = className;
        HonCou = honCou;
        InitWt = initWt;
        LastWt = lastWt;
        LossW = lossW;
        Num = num;
        StarCou = starCou;
        UserName = userName;
        this.acmCou = acmCou;
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

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getStarCou() {
        return StarCou;
    }

    public void setStarCou(String starCou) {
        StarCou = starCou;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAcmCou() {
        return acmCou;
    }

    public void setAcmCou(String acmCou) {
        this.acmCou = acmCou;
    }
}
