package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/29/2017.
 */

public class SituationOfMobileModel {
    private boolean IsLocked;//是否被锁定，true-是，false-否
    private boolean IsDownline;//是否是下线，true-是，false-否
    private boolean IsRegistered;//是否注册，true-是，false-否
    private boolean IsInMyClub;//是否是本俱乐部客户，true-是，false-否
    private boolean IsMyCustomer;//是否为自己添加的客户，true-是，false-否

    public boolean isMyCustomer() {
        return IsMyCustomer;
    }

    public void setMyCustomer(boolean myCustomer) {
        IsMyCustomer = myCustomer;
    }

    public boolean isLocked() {
        return IsLocked;
    }

    public void setLocked(boolean locked) {
        IsLocked = locked;
    }

    public boolean isDownline() {
        return IsDownline;
    }

    public void setDownline(boolean downline) {
        IsDownline = downline;
    }

    public boolean isRegistered() {
        return IsRegistered;
    }

    public void setRegistered(boolean registered) {
        IsRegistered = registered;
    }

    public boolean isInMyClub() {
        return IsInMyClub;
    }

    public void setInMyClub(boolean inMyClub) {
        IsInMyClub = inMyClub;
    }
}
