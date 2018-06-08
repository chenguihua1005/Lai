package com.softtek.lai.module.laijumine.model;

/**
 * Created by lareina.qiao on 2/8/2017.
 */

public class MyInfoModel {
    private String UserName;//用户名
    private String Photo;//头像
    private String ThPhoto;//头像缩略图
    private String Signature;//签名
    private String AcBanner;//封面图片
    private String Certification;//资格证号
    private String DynamicNum;//动态数
    private String FocusNum;//关注数
    private String LoveNum;//粉丝数
    private String RecordTime;//更新时间
    private int CustomerNum;//顾客数
    private String LossLevel;//减重等级
    private String SportLevel;//运动等级
    private String UnReadMsgNum;//未读消息数

    public boolean isCanRefreshCertification() {
        return CanRefreshCertification;
    }

    public void setCanRefreshCertification(boolean canRefreshCertification) {
        CanRefreshCertification = canRefreshCertification;
    }

    private boolean CanRefreshCertification;//是否可以刷新资格认证

    @Override
    public String toString() {
        return "MyInfoModel{" +
                "UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                ", ThPhoto='" + ThPhoto + '\'' +
                ", Signature='" + Signature + '\'' +
                ", AcBanner='" + AcBanner + '\'' +
                ", Certification='" + Certification + '\'' +
                ", DynamicNum='" + DynamicNum + '\'' +
                ", FocusNum='" + FocusNum + '\'' +
                ", LoveNum='" + LoveNum + '\'' +
                ", RecordTime='" + RecordTime + '\'' +
                ", CustomerNum=" + CustomerNum +
                ", LossLevel='" + LossLevel + '\'' +
                ", SportLevel='" + SportLevel + '\'' +
                ", UnReadMsgNum='" + UnReadMsgNum + '\'' +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThPhoto() {
        return ThPhoto;
    }

    public void setThPhoto(String thPhoto) {
        ThPhoto = thPhoto;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getAcBanner() {
        return AcBanner;
    }

    public void setAcBanner(String acBanner) {
        AcBanner = acBanner;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public String getDynamicNum() {
        return DynamicNum;
    }

    public void setDynamicNum(String dynamicNum) {
        DynamicNum = dynamicNum;
    }

    public String getFocusNum() {
        return FocusNum;
    }

    public void setFocusNum(String focusNum) {
        FocusNum = focusNum;
    }

    public String getLoveNum() {
        return LoveNum;
    }

    public void setLoveNum(String loveNum) {
        LoveNum = loveNum;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public int getCustomerNum() {
        return CustomerNum;
    }

    public void setCustomerNum(int customerNum) {
        CustomerNum = customerNum;
    }

    public String getLossLevel() {
        return LossLevel;
    }

    public void setLossLevel(String lossLevel) {
        LossLevel = lossLevel;
    }

    public String getSportLevel() {
        return SportLevel;
    }

    public void setSportLevel(String sportLevel) {
        SportLevel = sportLevel;
    }

    public String getUnReadMsgNum() {
        return UnReadMsgNum;
    }

    public void setUnReadMsgNum(String unReadMsgNum) {
        UnReadMsgNum = unReadMsgNum;
    }


}
