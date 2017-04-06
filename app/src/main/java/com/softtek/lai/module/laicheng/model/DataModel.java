package com.softtek.lai.module.laicheng.model;

/**
 * Created by shelly.xu on 4/6/2017.
 */

public class DataModel {
    //体重
    private int bodytype;
    private String bodytypedescription;//肥胖型
    private WeightModel weight_con;
    private WeightitemModel weight_item;

    //BMI
    private int bmi;
    private WeightModel bmi_con;
    private WeightitemModel bmi_item;

    //体脂肪
    private int bodyfat;
    private WeightitemModel bodyfat_item;

    //体脂率
    private int bodyfatrate;
    private WeightModel bodyfatrate_con;
    private WeightitemModel bodyfatrate_item;
    //去脂体重
    private int fatfreemass;
    private WeightModel fatfreemass_con;
    private WeightitemModel fatfreemass_item;
    //身体水分
    private int watercontent;
    private WeightitemModel watercontent_item;

    //身体水分率
    private int watercontentrate;
    private WeightModel watercontentrate_con;
    private WeightitemModel watercontentrate_item;

    //内脏脂肪指数
    private int visceralfatindex;
    private WeightModel visceralfatindex_con;
    private WeightitemModel visceralfatindex_item;

    //骨量
    private int bonemass;
    private WeightModel bonemass_con;
    private WeightitemModel bonemass_item;

    //肌肉量
    private int musclemass;
    private WeightModel musclemass_con;
    private WeightitemModel musclemass_item;

    //基础代谢
    private int basalmetabolicrate;
    private WeightModel basalmetabolicrate_con;
    private WeightitemModel basalmetabolicrate_item;
    //身体年龄
    private int physicalage;
    private WeightModel physicalage_con;
    private WeightitemModel physicalage_item;

    public DataModel(int bodytype, String bodytypedescription, WeightModel weight_con, WeightitemModel weight_item,
                     int bmi, WeightModel bmi_con, WeightitemModel bmi_item, int bodyfat, WeightitemModel bodyfat_item, int bodyfatrate,
                     WeightModel bodyfatrate_con, WeightitemModel bodyfatrate_item, int fatfreemass, WeightModel fatfreemass_con,
                     WeightitemModel fatfreemass_item, int watercontent, WeightitemModel watercontent_item, int watercontentrate, WeightModel watercontentrate_con,
                     WeightitemModel watercontentrate_item, int visceralfatindex, WeightModel visceralfatindex_con, WeightitemModel visceralfatindex_item,
                     int bonemass, WeightModel bonemass_con, WeightitemModel bonemass_item, int musclemass, WeightModel musclemass_con, WeightitemModel musclemass_item,
                     int basalmetabolicrate, WeightModel basalmetabolicrate_con, WeightitemModel basalmetabolicrate_item, int physicalage,
                     WeightModel physicalage_con, WeightitemModel physicalage_item) {
        this.bodytype = bodytype;
        this.bodytypedescription = bodytypedescription;
        this.weight_con = weight_con;
        this.weight_item = weight_item;
        this.bmi = bmi;
        this.bmi_con = bmi_con;
        this.bmi_item = bmi_item;
        this.bodyfat = bodyfat;
        this.bodyfat_item = bodyfat_item;
        this.bodyfatrate = bodyfatrate;
        this.bodyfatrate_con = bodyfatrate_con;
        this.bodyfatrate_item = bodyfatrate_item;
        this.fatfreemass = fatfreemass;
        this.fatfreemass_con = fatfreemass_con;
        this.fatfreemass_item = fatfreemass_item;
        this.watercontent = watercontent;
        this.watercontent_item = watercontent_item;
        this.watercontentrate = watercontentrate;
        this.watercontentrate_con = watercontentrate_con;
        this.watercontentrate_item = watercontentrate_item;
        this.visceralfatindex = visceralfatindex;
        this.visceralfatindex_con = visceralfatindex_con;
        this.visceralfatindex_item = visceralfatindex_item;
        this.bonemass = bonemass;
        this.bonemass_con = bonemass_con;
        this.bonemass_item = bonemass_item;
        this.musclemass = musclemass;
        this.musclemass_con = musclemass_con;
        this.musclemass_item = musclemass_item;
        this.basalmetabolicrate = basalmetabolicrate;
        this.basalmetabolicrate_con = basalmetabolicrate_con;
        this.basalmetabolicrate_item = basalmetabolicrate_item;
        this.physicalage = physicalage;
        this.physicalage_con = physicalage_con;
        this.physicalage_item = physicalage_item;
    }

    public int getBodytype() {
        return bodytype;
    }

    public void setBodytype(int bodytype) {
        this.bodytype = bodytype;
    }

    public String getBodytypedescription() {
        return bodytypedescription;
    }

    public void setBodytypedescription(String bodytypedescription) {
        this.bodytypedescription = bodytypedescription;
    }

    public WeightModel getWeight_con() {
        return weight_con;
    }

    public void setWeight_con(WeightModel weight_con) {
        this.weight_con = weight_con;
    }

    public WeightitemModel getWeight_item() {
        return weight_item;
    }

    public void setWeight_item(WeightitemModel weight_item) {
        this.weight_item = weight_item;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public WeightModel getBmi_con() {
        return bmi_con;
    }

    public void setBmi_con(WeightModel bmi_con) {
        this.bmi_con = bmi_con;
    }

    public WeightitemModel getBmi_item() {
        return bmi_item;
    }

    public void setBmi_item(WeightitemModel bmi_item) {
        this.bmi_item = bmi_item;
    }

    public int getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(int bodyfat) {
        this.bodyfat = bodyfat;
    }

    public WeightitemModel getBodyfat_item() {
        return bodyfat_item;
    }

    public void setBodyfat_item(WeightitemModel bodyfat_item) {
        this.bodyfat_item = bodyfat_item;
    }

    public int getBodyfatrate() {
        return bodyfatrate;
    }

    public void setBodyfatrate(int bodyfatrate) {
        this.bodyfatrate = bodyfatrate;
    }

    public WeightModel getBodyfatrate_con() {
        return bodyfatrate_con;
    }

    public void setBodyfatrate_con(WeightModel bodyfatrate_con) {
        this.bodyfatrate_con = bodyfatrate_con;
    }

    public WeightitemModel getBodyfatrate_item() {
        return bodyfatrate_item;
    }

    public void setBodyfatrate_item(WeightitemModel bodyfatrate_item) {
        this.bodyfatrate_item = bodyfatrate_item;
    }

    public int getFatfreemass() {
        return fatfreemass;
    }

    public void setFatfreemass(int fatfreemass) {
        this.fatfreemass = fatfreemass;
    }

    public WeightModel getFatfreemass_con() {
        return fatfreemass_con;
    }

    public void setFatfreemass_con(WeightModel fatfreemass_con) {
        this.fatfreemass_con = fatfreemass_con;
    }

    public WeightitemModel getFatfreemass_item() {
        return fatfreemass_item;
    }

    public void setFatfreemass_item(WeightitemModel fatfreemass_item) {
        this.fatfreemass_item = fatfreemass_item;
    }

    public int getWatercontent() {
        return watercontent;
    }

    public void setWatercontent(int watercontent) {
        this.watercontent = watercontent;
    }

    public WeightitemModel getWatercontent_item() {
        return watercontent_item;
    }

    public void setWatercontent_item(WeightitemModel watercontent_item) {
        this.watercontent_item = watercontent_item;
    }

    public int getWatercontentrate() {
        return watercontentrate;
    }

    public void setWatercontentrate(int watercontentrate) {
        this.watercontentrate = watercontentrate;
    }

    public WeightModel getWatercontentrate_con() {
        return watercontentrate_con;
    }

    public void setWatercontentrate_con(WeightModel watercontentrate_con) {
        this.watercontentrate_con = watercontentrate_con;
    }

    public WeightitemModel getWatercontentrate_item() {
        return watercontentrate_item;
    }

    public void setWatercontentrate_item(WeightitemModel watercontentrate_item) {
        this.watercontentrate_item = watercontentrate_item;
    }

    public int getVisceralfatindex() {
        return visceralfatindex;
    }

    public void setVisceralfatindex(int visceralfatindex) {
        this.visceralfatindex = visceralfatindex;
    }

    public WeightModel getVisceralfatindex_con() {
        return visceralfatindex_con;
    }

    public void setVisceralfatindex_con(WeightModel visceralfatindex_con) {
        this.visceralfatindex_con = visceralfatindex_con;
    }

    public WeightitemModel getVisceralfatindex_item() {
        return visceralfatindex_item;
    }

    public void setVisceralfatindex_item(WeightitemModel visceralfatindex_item) {
        this.visceralfatindex_item = visceralfatindex_item;
    }

    public int getBonemass() {
        return bonemass;
    }

    public void setBonemass(int bonemass) {
        this.bonemass = bonemass;
    }

    public WeightModel getBonemass_con() {
        return bonemass_con;
    }

    public void setBonemass_con(WeightModel bonemass_con) {
        this.bonemass_con = bonemass_con;
    }

    public WeightitemModel getBonemass_item() {
        return bonemass_item;
    }

    public void setBonemass_item(WeightitemModel bonemass_item) {
        this.bonemass_item = bonemass_item;
    }

    public int getMusclemass() {
        return musclemass;
    }

    public void setMusclemass(int musclemass) {
        this.musclemass = musclemass;
    }

    public WeightModel getMusclemass_con() {
        return musclemass_con;
    }

    public void setMusclemass_con(WeightModel musclemass_con) {
        this.musclemass_con = musclemass_con;
    }

    public WeightitemModel getMusclemass_item() {
        return musclemass_item;
    }

    public void setMusclemass_item(WeightitemModel musclemass_item) {
        this.musclemass_item = musclemass_item;
    }

    public int getBasalmetabolicrate() {
        return basalmetabolicrate;
    }

    public void setBasalmetabolicrate(int basalmetabolicrate) {
        this.basalmetabolicrate = basalmetabolicrate;
    }

    public WeightModel getBasalmetabolicrate_con() {
        return basalmetabolicrate_con;
    }

    public void setBasalmetabolicrate_con(WeightModel basalmetabolicrate_con) {
        this.basalmetabolicrate_con = basalmetabolicrate_con;
    }

    public WeightitemModel getBasalmetabolicrate_item() {
        return basalmetabolicrate_item;
    }

    public void setBasalmetabolicrate_item(WeightitemModel basalmetabolicrate_item) {
        this.basalmetabolicrate_item = basalmetabolicrate_item;
    }

    public int getPhysicalage() {
        return physicalage;
    }

    public void setPhysicalage(int physicalage) {
        this.physicalage = physicalage;
    }

    public WeightModel getPhysicalage_con() {
        return physicalage_con;
    }

    public void setPhysicalage_con(WeightModel physicalage_con) {
        this.physicalage_con = physicalage_con;
    }

    public WeightitemModel getPhysicalage_item() {
        return physicalage_item;
    }

    public void setPhysicalage_item(WeightitemModel physicalage_item) {
        this.physicalage_item = physicalage_item;
    }
}
