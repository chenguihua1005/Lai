package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/9/2016.
 * 关注通知
 */
public class FocusEvent {

    private String accountId;
    private int focusStatus;//0表示取消关注，1表示加关注
    private Where where;

    public FocusEvent(String accountId, int focusStatus,Where where) {
        this.accountId = accountId;
        this.focusStatus=focusStatus;
        this.where=where;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
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
