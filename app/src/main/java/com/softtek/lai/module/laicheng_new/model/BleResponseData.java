package com.softtek.lai.module.laicheng_new.model;

/**
 * Created by jia.lu on 2017/10/27.
 */

public class BleResponseData {

    /**
     * recordId : eeeab644-cc74-4f66-85e2-05f8f635378f
     * bodyTypeTile : 偏瘦
     * bodyTypeColor : #79b1ac
     */

    private String recordId;
    private String bodyTypeTile;
    private String bodyTypeColor;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getBodyTypeTile() {
        return bodyTypeTile;
    }

    public void setBodyTypeTile(String bodyTypeTile) {
        this.bodyTypeTile = bodyTypeTile;
    }

    public String getBodyTypeColor() {
        return bodyTypeColor;
    }

    public void setBodyTypeColor(String bodyTypeColor) {
        this.bodyTypeColor = bodyTypeColor;
    }
}
