package com.softtek.lai.module.health.presenter;

/**
 * Created by John on 2016/4/13.
 */
public interface IHealthyRecord {

    void doGetHealthPysicalRecords(String Startdate,String Enddate,int i);
    void GetHealthWeightRecords(String Startdate,String Enddate,int i);
}
