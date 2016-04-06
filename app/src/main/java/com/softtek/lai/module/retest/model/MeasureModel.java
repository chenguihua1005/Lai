/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 3/28/2016.
 */
public class MeasureModel {
    private String phone;
    private String username;
    private String chestgirth;
    private String waistgirth;
    private String hipgirth;
    private String upperarmgirth;
    private String thighgirth;
    private String calfgirth;
    private String measureddata;
    private String totalcount;
    private String pageindex;
    private String pagesize;
    private String totalpage;

    public MeasureModel() {

    }

    @Override
    public String toString() {
        return "MeasureModel{" +
                "phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", chestgirth='" + chestgirth + '\'' +
                ", waistgirth='" + waistgirth + '\'' +
                ", hipgirth='" + hipgirth + '\'' +
                ", upperarmgirth='" + upperarmgirth + '\'' +
                ", thighgirth='" + thighgirth + '\'' +
                ", calfgirth='" + calfgirth + '\'' +
                ", measureddata='" + measureddata + '\'' +
                ", totalcount='" + totalcount + '\'' +
                ", pageindex='" + pageindex + '\'' +
                ", pagesize='" + pagesize + '\'' +
                ", totalpage='" + totalpage + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChestgirth() {
        return chestgirth;
    }

    public void setChestgirth(String chestgirth) {
        this.chestgirth = chestgirth;
    }

    public String getWaistgirth() {
        return waistgirth;
    }

    public void setWaistgirth(String waistgirth) {
        this.waistgirth = waistgirth;
    }

    public String getHipgirth() {
        return hipgirth;
    }

    public void setHipgirth(String hipgirth) {
        this.hipgirth = hipgirth;
    }

    public String getUpperarmgirth() {
        return upperarmgirth;
    }

    public void setUpperarmgirth(String upperarmgirth) {
        this.upperarmgirth = upperarmgirth;
    }

    public String getThighgirth() {
        return thighgirth;
    }

    public void setThighgirth(String thighgirth) {
        this.thighgirth = thighgirth;
    }

    public String getCalfgirth() {
        return calfgirth;
    }

    public void setCalfgirth(String calfgirth) {
        this.calfgirth = calfgirth;
    }

    public String getMeasureddata() {
        return measureddata;
    }

    public void setMeasureddata(String measureddata) {
        this.measureddata = measureddata;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getPageindex() {
        return pageindex;
    }

    public void setPageindex(String pageindex) {
        this.pageindex = pageindex;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public MeasureModel(String phone, String username, String chestgirth, String waistgirth, String hipgirth, String upperarmgirth, String thighgirth, String calfgirth, String measureddata, String totalcount, String pageindex, String pagesize, String totalpage) {
        this.phone = phone;
        this.username = username;
        this.chestgirth = chestgirth;
        this.waistgirth = waistgirth;
        this.hipgirth = hipgirth;
        this.upperarmgirth = upperarmgirth;
        this.thighgirth = thighgirth;
        this.calfgirth = calfgirth;
        this.measureddata = measureddata;
        this.totalcount = totalcount;
        this.pageindex = pageindex;
        this.pagesize = pagesize;
        this.totalpage = totalpage;
    }
}
