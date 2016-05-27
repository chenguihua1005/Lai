package com.softtek.lai.stepcount.model;

import zilla.libcore.db.Id;
import zilla.libcore.db.Table;

/**
 * Created by jerry.guan on 5/23/2016.
 * 保存用户步数的对象
 */
public class UserStep {

    private int id;

    private long accountId;

    private long stepCount;

    private String recordTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getStepCount() {
        return stepCount;
    }

    public void setStepCount(long stepCount) {
        this.stepCount = stepCount;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
