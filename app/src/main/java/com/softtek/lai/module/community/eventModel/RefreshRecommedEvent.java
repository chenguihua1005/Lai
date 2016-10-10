package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/9/2016.
 */
public class RefreshRecommedEvent {

    private String accountId;

    public RefreshRecommedEvent(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
