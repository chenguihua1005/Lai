package com.softtek.lai.module.sport2.eventmodel;

/**
 * Created by jerry.guan on 10/24/2016.
 */

public class PkZanEvent {

    private boolean isLeft;

    private long pkId;

    public PkZanEvent(boolean isLeft, long pkId) {
        this.isLeft = isLeft;
        this.pkId = pkId;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public long getPkId() {
        return pkId;
    }

    public void setPkId(long pkId) {
        this.pkId = pkId;
    }
}
