package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/27/2016.
 */

public class DeleteRecommedEvent {

    private String dynamicId;
    private Where where;

    public DeleteRecommedEvent(String dynamicId, Where where) {
        this.dynamicId = dynamicId;
        this.where = where;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }
}
