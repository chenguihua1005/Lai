package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public class ClmListModel {
    private String AssistantId;
    private String ordernum;
    private String IsStar;
    private String IsTest;
    private String Photo;
    private String Gender;
    private String SuperName;
    private String accountid;
    private String firstweight;
    private String honorcnt;
    private String lastweight;
    private String loss;
    private String username;


    public String getOrdernum() {
        return ordernum;
    }

    @Override
    public String toString() {
        return "ClmListModel{" +
                "AssistantId='" + AssistantId + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", IsStar='" + IsStar + '\'' +
                ", IsTest='" + IsTest + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Gender='" + Gender + '\'' +
                ", SuperName='" + SuperName + '\'' +
                ", accountid='" + accountid + '\'' +
                ", firstweight='" + firstweight + '\'' +
                ", honorcnt='" + honorcnt + '\'' +
                ", lastweight='" + lastweight + '\'' +
                ", loss='" + loss + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAssistantId() {
        return AssistantId;
    }

    public void setAssistantId(String assistantId) {
        AssistantId = assistantId;
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

    public String getSuperName() {
        return SuperName;
    }

    public void setSuperName(String superName) {
        SuperName = superName;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
