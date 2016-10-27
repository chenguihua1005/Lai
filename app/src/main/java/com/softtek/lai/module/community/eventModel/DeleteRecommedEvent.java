package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/27/2016.
 */

public class DeleteRecommedEvent {

    private String dynamicId;

    public DeleteRecommedEvent(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }
}
