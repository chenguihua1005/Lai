package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/9/2016.
 * 关注通知
 */
public class FocusEvent {

    private String accountId;
    private int focusStatus;

    public FocusEvent(String accountId, int focusStatus) {
        this.accountId = accountId;
        this.focusStatus=focusStatus;
    }

    public int getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(int focusStatus) {
        this.focusStatus = focusStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
