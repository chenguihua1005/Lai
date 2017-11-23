package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class PersonnelModel {
    private String userPhoto;
    private String personnelName;
    private String personnelPhone;
    private int customerSum;
    private int customerToday;
    private int marketSum;
    private int marketToday;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhone) {
        this.userPhoto = userPhone;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getPersonnelPhone() {
        return personnelPhone;
    }

    public void setPersonnelPhone(String personnelPhone) {
        this.personnelPhone = personnelPhone;
    }

    public int getCustomerSum() {
        return customerSum;
    }

    public void setCustomerSum(int customerSum) {
        this.customerSum = customerSum;
    }

    public int getCustomerToday() {
        return customerToday;
    }

    public void setCustomerToday(int customerToday) {
        this.customerToday = customerToday;
    }

    public int getMarketSum() {
        return marketSum;
    }

    public void setMarketSum(int marketSum) {
        this.marketSum = marketSum;
    }

    public int getMarketToday() {
        return marketToday;
    }

    public void setMarketToday(int marketToday) {
        this.marketToday = marketToday;
    }
}
