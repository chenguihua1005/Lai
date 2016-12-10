package com.softtek.lai.module.bodygame3.head.model;

import java.util.List;

/**
 * Created by lareina.qiao on 12/2/2016.
 */

public class MemberInfoModel {
    private long Accountid;//
    private String UserName;//学员姓名
    private String UserPhoto;//学员头像
    private String UserThPhoto;//学员头像缩略图
    private String ClassRole;//学员所在班级所属角色（学员可展示体重变化曲线图，其他角色不能）
    private String HXAccountId;//环信id
    private String MilkAngle;//奶昔天使
    private String MilkAngleId;//奶昔天使id
    private String Introducer;//爱心学员
    private String IntroducerId;//爱心学员id
    private String IsFocus;//是否关注（true关注，false未关注）
    private String IsFriend;//是否为好友（1.是）
    private String TotalLossWeight;//总减重
    private List<NewsTopFourModel> NewsTopFour;//动态图片列表
    private String InitWeight;//初始体重
    private String InitImg;//初始体重图片
    private String InitThImg;//初始体重图片缩略图
    private String CurrentWeight;//目前体重
    private String CurttentImg;//目前体重图
    private String CurttentThImg;//目前体重缩略图
    private String PersonalityName;//个人签名

    public long getAccountid() {
        return Accountid;
    }

    public void setAccountid(long accountid) {
        Accountid = accountid;
    }

    @Override
    public String toString() {
        return "MemberInfoModel{" +
                "UserName='" + UserName + '\'' +
                ", UserPhoto='" + UserPhoto + '\'' +
                ", UserThPhoto='" + UserThPhoto + '\'' +
                ", ClassRole='" + ClassRole + '\'' +
                ", HXAccountId='" + HXAccountId + '\'' +
                ", MilkAngle='" + MilkAngle + '\'' +
                ", MilkAngleId='" + MilkAngleId + '\'' +
                ", Introducer='" + Introducer + '\'' +
                ", IntroducerId='" + IntroducerId + '\'' +
                ", IsFocus='" + IsFocus + '\'' +
                ", IsFriend='" + IsFriend + '\'' +
                ", TotalLossWeight='" + TotalLossWeight + '\'' +
                ", NewsTopFour=" + NewsTopFour +
                ", InitWeight='" + InitWeight + '\'' +
                ", InitImg='" + InitImg + '\'' +
                ", InitThImg='" + InitThImg + '\'' +
                ", CurrentWeight='" + CurrentWeight + '\'' +
                ", CurttentImg='" + CurttentImg + '\'' +
                ", CurttentThImg='" + CurttentThImg + '\'' +
                ", PersonalityName='" + PersonalityName + '\'' +
                '}';
    }

    public String getPersonalityName() {
        return PersonalityName;
    }

    public void setPersonalityName(String personalityName) {
        PersonalityName = personalityName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public String getUserThPhoto() {
        return UserThPhoto;
    }

    public void setUserThPhoto(String userThPhoto) {
        UserThPhoto = userThPhoto;
    }

    public String getClassRole() {
        return ClassRole;
    }

    public void setClassRole(String classRole) {
        ClassRole = classRole;
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getMilkAngle() {
        return MilkAngle;
    }

    public void setMilkAngle(String milkAngle) {
        MilkAngle = milkAngle;
    }

    public String getMilkAngleId() {
        return MilkAngleId;
    }

    public void setMilkAngleId(String milkAngleId) {
        MilkAngleId = milkAngleId;
    }

    public String getIntroducer() {
        return Introducer;
    }

    public void setIntroducer(String introducer) {
        Introducer = introducer;
    }

    public String getIntroducerId() {
        return IntroducerId;
    }

    public void setIntroducerId(String introducerId) {
        IntroducerId = introducerId;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }

    public String getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(String isFriend) {
        IsFriend = isFriend;
    }

    public String getTotalLossWeight() {
        return TotalLossWeight;
    }

    public void setTotalLossWeight(String totalLossWeight) {
        TotalLossWeight = totalLossWeight;
    }

    public List<NewsTopFourModel> getNewsTopFour() {
        return NewsTopFour;
    }

    public void setNewsTopFour(List<NewsTopFourModel> newsTopFour) {
        NewsTopFour = newsTopFour;
    }

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }

    public String getInitImg() {
        return InitImg;
    }

    public void setInitImg(String initImg) {
        InitImg = initImg;
    }

    public String getInitThImg() {
        return InitThImg;
    }

    public void setInitThImg(String initThImg) {
        InitThImg = initThImg;
    }

    public String getCurrentWeight() {
        return CurrentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        CurrentWeight = currentWeight;
    }

    public String getCurttentImg() {
        return CurttentImg;
    }

    public void setCurttentImg(String curttentImg) {
        CurttentImg = curttentImg;
    }

    public String getCurttentThImg() {
        return CurttentThImg;
    }

    public void setCurttentThImg(String curttentThImg) {
        CurttentThImg = curttentThImg;
    }
}
