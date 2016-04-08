package com.softtek.lai.module.retest.model;

import java.io.Serializable;

/**
 * Created by lareina.qiao on 4/6/2016.
 */
public class ItemsModel implements Serializable{
    private String uid;
    private String weight;
    private String bmi;
    private String bodyfat;
    private String bodyfatrate;
    private String fatfreemass;
    private String watercontent;
    private String watercontentrate;
    private String visceralfatindex;
    private String bonemass;
    private String musclemass;
    private String basalmetabolicrate;
    private String physicalage;
    private String created;
    private String birthdate;
    private String gender;
    private String height;

    @Override
    public String toString() {
        return "ItemsModel{" +
                "uid='" + uid + '\'' +
                ", weight='" + weight + '\'' +
                ", bmi='" + bmi + '\'' +
                ", bodyfat='" + bodyfat + '\'' +
                ", bodyfatrate='" + bodyfatrate + '\'' +
                ", fatfreemass='" + fatfreemass + '\'' +
                ", watercontent='" + watercontent + '\'' +
                ", watercontentrate='" + watercontentrate + '\'' +
                ", visceralfatindex='" + visceralfatindex + '\'' +
                ", bonemass='" + bonemass + '\'' +
                ", musclemass='" + musclemass + '\'' +
                ", basalmetabolicrate='" + basalmetabolicrate + '\'' +
                ", physicalage='" + physicalage + '\'' +
                ", created='" + created + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(String bodyfat) {
        this.bodyfat = bodyfat;
    }

    public String getBodyfatrate() {
        return bodyfatrate;
    }

    public void setBodyfatrate(String bodyfatrate) {
        this.bodyfatrate = bodyfatrate;
    }

    public String getFatfreemass() {
        return fatfreemass;
    }

    public void setFatfreemass(String fatfreemass) {
        this.fatfreemass = fatfreemass;
    }

    public String getWatercontent() {
        return watercontent;
    }

    public void setWatercontent(String watercontent) {
        this.watercontent = watercontent;
    }

    public String getWatercontentrate() {
        return watercontentrate;
    }

    public void setWatercontentrate(String watercontentrate) {
        this.watercontentrate = watercontentrate;
    }

    public String getVisceralfatindex() {
        return visceralfatindex;
    }

    public void setVisceralfatindex(String visceralfatindex) {
        this.visceralfatindex = visceralfatindex;
    }

    public String getBonemass() {
        return bonemass;
    }

    public void setBonemass(String bonemass) {
        this.bonemass = bonemass;
    }

    public String getMusclemass() {
        return musclemass;
    }

    public void setMusclemass(String musclemass) {
        this.musclemass = musclemass;
    }

    public String getBasalmetabolicrate() {
        return basalmetabolicrate;
    }

    public void setBasalmetabolicrate(String basalmetabolicrate) {
        this.basalmetabolicrate = basalmetabolicrate;
    }

    public String getPhysicalage() {
        return physicalage;
    }

    public void setPhysicalage(String physicalage) {
        this.physicalage = physicalage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
