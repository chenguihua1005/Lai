package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 10/12/2016.
 */
public class ZanEvent {

    private String dynamicId;
    private boolean isZan;
    private Where where;//0表示从关注列表点的赞，1表示从推荐列表点的赞,2表示话题详情,3表示动态详情,4表示照片墙

    public ZanEvent( String dynamicId, boolean isZan,Where where) {
        this.dynamicId = dynamicId;
        this.isZan = isZan;
        this.where=where;
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

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }
}
