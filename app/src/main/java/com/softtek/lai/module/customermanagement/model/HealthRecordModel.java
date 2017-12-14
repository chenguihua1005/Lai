package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 12/12/2017.
 */

public class HealthRecordModel {
    /**
     * 记录来源
     * -1-默认值, 没有实际意义
     * 0-莱秤推送
     * 2-健康记录
     * 3-创建档案
     * 5-莱聚+测量
     * 6-新莱秤测量
     */
    private int Sourcetype;
    private String AcInfoId;

    public int getSourcetype() {
        return Sourcetype;
    }

    public void setSourcetype(int sourcetype) {
        Sourcetype = sourcetype;
    }

    public String getAcInfoId() {
        return AcInfoId;
    }

    public void setAcInfoId(String acInfoId) {
        AcInfoId = acInfoId;
    }

    @Override
    public String toString() {
        return "HealthRecordModel{" +
                "Sourcetype=" + Sourcetype +
                ", AcInfoId='" + AcInfoId + '\'' +
                '}';
    }
}
