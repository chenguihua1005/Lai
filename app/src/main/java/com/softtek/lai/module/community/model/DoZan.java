package com.softtek.lai.module.community.model;

/**
 * Created by jerry.guan on 4/21/2016.
 * 点赞表单
 */
public class DoZan {

    private long AccountId;
    private String HealthId;

    public DoZan(long accountId, String healthId) {
        AccountId = accountId;
        HealthId = healthId;
    }

    public DoZan() {
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getHealthId() {
        return HealthId;
    }

    public void setHealthId(String healthId) {
        HealthId = healthId;
    }
}


