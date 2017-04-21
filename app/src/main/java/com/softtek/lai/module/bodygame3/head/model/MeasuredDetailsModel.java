package com.softtek.lai.module.bodygame3.head.model;

import java.io.Serializable;

/**
 * Created by 87356 on 2016/12/3.
 */
public class MeasuredDetailsModel implements Serializable {
    private String WeekNum;//周数
    private String TypeDate;//日期
    private String Image;//图片
    private String Thumbnail;//缩略图
    private String UserName;//用户名
    private String Photo;//头像
    private String Mobile;//手机号
    private String ClassName;//班级名称
    private String InitWeight;//初始体重

    private String Weight;//现在体重
    private String weightUnit;//体重单位

    private String Pysical;//体脂
    private String bodyFatUnit;//体脂单位

    private String Fat;//内脂  没单位
    private String Circum;//胸围
    private String Waistline;//腰围
    private String Hiplie;//臀围
    private String UpArmGirth;//上臂围
    private String UpLegGirth;//大腿围
    private String DoLegGirth;//小腿围
    private String AMStatus;
    private String Gender;
    private String StartDate;
    private String EndDate;
    private String MeasureDate;
    private String Img;
    private String ImgThumbnail;

    private String Bmi; //BMI
    private String BMIUnit;

    private String FatFreeMass;//去脂体重
    private String fatFreemassUnit;
    //    private String ViscusFatIndex;     //内脏脂肪指数
    private String BodyWaterRate;//身体水分率
    private String waterContentRateUnit;

    private String BodyWater;//身体水分
    private String waterContentUnit;

    private String MuscleMass;//肌肉量
    private String musclemassUnit;

    private String BoneMass;//骨量
    private String bonemassUnit;

    private String BasalMetabolism;//基础代谢
    private String basalmetabolicrateUnit;

    private String PhysicalAge;//身体年龄

//    Height, BirthDate,Gender

    private String Height;//身高
    private String BirthDate;//生日
    private long AccountId;//

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

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

    public String getAMStatus() {
        return AMStatus;
    }

    public void setAMStatus(String AMStatus) {
        this.AMStatus = AMStatus;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

    public String getWeekNum() {
        return WeekNum;
    }

    public void setWeekNum(String weekNum) {
        WeekNum = weekNum;
    }

    public String getTypeDate() {
        return TypeDate;
    }

    public void setTypeDate(String typeDate) {
        TypeDate = typeDate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
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

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getBodyFatUnit() {
        return bodyFatUnit;
    }

    public void setBodyFatUnit(String bodyFatUnit) {
        this.bodyFatUnit = bodyFatUnit;
    }

    public String getBMIUnit() {
        return BMIUnit;
    }

    public void setBMIUnit(String BMIUnit) {
        this.BMIUnit = BMIUnit;
    }

    public String getFatFreemassUnit() {
        return fatFreemassUnit;
    }

    public void setFatFreemassUnit(String fatFreemassUnit) {
        this.fatFreemassUnit = fatFreemassUnit;
    }

    public String getWaterContentRateUnit() {
        return waterContentRateUnit;
    }

    public void setWaterContentRateUnit(String waterContentRateUnit) {
        this.waterContentRateUnit = waterContentRateUnit;
    }

    public String getWaterContentUnit() {
        return waterContentUnit;
    }

    public void setWaterContentUnit(String waterContentUnit) {
        this.waterContentUnit = waterContentUnit;
    }

    public String getMusclemassUnit() {
        return musclemassUnit;
    }

    public void setMusclemassUnit(String musclemassUnit) {
        this.musclemassUnit = musclemassUnit;
    }

    public String getBonemassUnit() {
        return bonemassUnit;
    }

    public void setBonemassUnit(String bonemassUnit) {
        this.bonemassUnit = bonemassUnit;
    }

    public String getBasalmetabolicrateUnit() {
        return basalmetabolicrateUnit;
    }

    public void setBasalmetabolicrateUnit(String basalmetabolicrateUnit) {
        this.basalmetabolicrateUnit = basalmetabolicrateUnit;
    }
}
