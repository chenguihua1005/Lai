package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/10/2016.
 */
public class DeleteFocusEvent {

    private String accountId;

    public DeleteFocusEvent(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
