package com.softtek.lai.module.laicheng.model;

import java.io.Serializable;

/**
 * Created by shelly.xu on 2017/4/10.
 */

public class HistoryModel implements Serializable{
    private String recordId;//访客记录id
    private String measuredTime;//访客测量时间
    private VisitorInfoModel visitor;

    public HistoryModel(String recordId, String measuredTime, VisitorInfoModel visitor) {
        this.recordId = recordId;
        this.measuredTime = measuredTime;
        this.visitor = visitor;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getMeasuredTime() {
        return measuredTime;
    }

    public void setMeasuredTime(String measuredTime) {
        this.measuredTime = measuredTime;
    }

    public VisitorInfoModel getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorInfoModel visitor) {
        this.visitor = visitor;
    }
}
