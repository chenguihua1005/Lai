package com.softtek.lai.module.ranking.event;

/**
 * Created by jerry.guan on 10/25/2016.
 */

public class RankZan {

    private String guid;

    private boolean isRunGroup;
    private boolean isMine;

    public RankZan(String guid, boolean isRunGroup, boolean isMine) {
        this.guid = guid;
        this.isRunGroup = isRunGroup;
        this.isMine = isMine;
    }

    public boolean isRunGroup() {
        return isRunGroup;
    }

    public void setRunGroup(boolean runGroup) {
        isRunGroup = runGroup;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
