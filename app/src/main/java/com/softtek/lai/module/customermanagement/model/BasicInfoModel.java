package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class BasicInfoModel {
    private BasicModel Basics;//基本信息
    private LatestRecordModel Latest;//最新测量记录

    public BasicModel getBasics() {
        return Basics;
    }

    public void setBasics(BasicModel basics) {
        Basics = basics;
    }

    public LatestRecordModel getLatest() {
        return Latest;
    }

    public void setLatest(LatestRecordModel latest) {
        Latest = latest;
    }
}
