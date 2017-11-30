package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jessica.zhang on 11/29/2017.
 */

public class SituationOfMobileModel {
    private boolean IsLocked;
    private boolean IsDownline;
    private boolean IsRegistered;
    private boolean IsInMyClub;

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
