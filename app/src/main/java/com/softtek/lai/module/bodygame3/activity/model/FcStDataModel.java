package com.softtek.lai.module.bodygame3.activity.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 12/13/2016.
 */

public class FcStDataModel implements Serializable {
    private String UserName;//用户名
    private String Gender;//性别
    private String Photo;//头像
    private String Mobile;//手机号
    private String ClassName;//班级名
    private String StartDate;//开班日期
    private String EndDate;//结束日期
    private int WeekNum;//第几周
    private String MeasureDate;//复测日期
    private String InitWeight;//初始体重
    private String Weight;//现在体重
    private String Pysical;//体脂
    private String Fat;//内脂
    private String Circum;//胸围
    private String Waistline;//腰围
    private String Hiplie;//臀围
    private String UpArmGirth;//上臂围
    private String UpLegGirth;//大腿围
    private String DoLegGirth;//小腿围
    private String Img;//图片
    private String ImgThumbnail;//图片缩略图
    private String Status;
    private String UpdateTips;

    private String Bmi; //BMI
    private String FatFreeMass;//去脂体重
//    private String ViscusFatIndex;     //内脏脂肪指数
    private String BodyWaterRate;//身体水分率
    private String BodyWater;//身体水分
    private String MuscleMass;//肌肉量
    private String BoneMass;//骨量
    private String BasalMetabolism;//基础代谢
    private String PhysicalAge;//身体年龄




    public String getBmi() {
        return Bmi;
    }

    public void setBmi(String bmi) {
        Bmi = bmi;
    }

    public String getFatFreeMass() {
        return FatFreeMass;
    }

    public void setFatFreeMass(String fatFreeMass) {
        FatFreeMass = fatFreeMass;
    }

//    public String getViscusFatIndex() {
//        return ViscusFatIndex;
//    }
//
//    public void setViscusFatIndex(String viscusFatIndex) {
//        ViscusFatIndex = viscusFatIndex;
//    }

    public String getBodyWaterRate() {
        return BodyWaterRate;
    }

    public void setBodyWaterRate(String bodyWaterRate) {
        BodyWaterRate = bodyWaterRate;
    }

    public String getBodyWater() {
        return BodyWater;
    }

    public void setBodyWater(String bodyWater) {
        BodyWater = bodyWater;
    }

    public String getMuscleMass() {
        return MuscleMass;
    }

    public void setMuscleMass(String muscleMass) {
        MuscleMass = muscleMass;
    }

    public String getBoneMass() {
        return BoneMass;
    }

    public void setBoneMass(String boneMass) {
        BoneMass = boneMass;
    }

    public String getBasalMetabolism() {
        return BasalMetabolism;
    }

    public void setBasalMetabolism(String basalMetabolism) {
        BasalMetabolism = basalMetabolism;
    }

    public String getPhysicalAge() {
        return PhysicalAge;
    }

    public void setPhysicalAge(String physicalAge) {
        PhysicalAge = physicalAge;
    }

    public String getUpdateTips() {
        return UpdateTips;
    }

    public void setUpdateTips(String updateTips) {
        UpdateTips = updateTips;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getMeasureDate() {
        return MeasureDate;
    }

    public void setMeasureDate(String measureDate) {
        MeasureDate = measureDate;
    }

    public String getInitWeight() {
        return InitWeight;
    }

    public void setInitWeight(String initWeight) {
        InitWeight = initWeight;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getWaistline() {
        return Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getImgThumbnail() {
        return ImgThumbnail;
    }

    public void setImgThumbnail(String imgThumbnail) {
        ImgThumbnail = imgThumbnail;
    }

    public int getWeekNum() {
        return WeekNum;
    }

    public void setWeekNum(int weekNum) {
        WeekNum = weekNum;
    }
}
