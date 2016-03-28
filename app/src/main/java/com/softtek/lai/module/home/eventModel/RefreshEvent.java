package com.softtek.lai.module.home.eventModel;

/**
 * Created by jerry.guan on 3/25/2016.
 */
public class RefreshEvent {

    public int flag;
    public boolean result=false;

    public RefreshEvent() {
    }

    public RefreshEvent(boolean result,int flag) {
        this.result = result;
        this.flag=flag;
    }
}
