package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by lareina.qiao on 12/12/2016.
 */

public class EditSignaModel {
    private Long AccountId;
    private String PName;

    @Override
    public String toString() {
        return "EditSignaModel{" +
                "AccountId=" + AccountId +
                ", PName='" + PName + '\'' +
                '}';
    }

    public Long getAccountId() {
        return AccountId;
    }

    public void setAccountId(Long accountId) {
        AccountId = accountId;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }
}
