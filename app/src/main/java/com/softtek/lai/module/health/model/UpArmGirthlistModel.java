package com.softtek.lai.module.health.model;

/**
 * Created by lareina.qiao on 4/25/2016.
 */
public class UpArmGirthlistModel {
    private String AccountId;
    private String createDate;
    private String UpArmGirth;

    @Override
    public String toString() {
        return "UpArmGirthlistModel{" +
                "AccountId='" + AccountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", UpArmGirth='" + UpArmGirth + '\'' +
                '}';
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }
}
