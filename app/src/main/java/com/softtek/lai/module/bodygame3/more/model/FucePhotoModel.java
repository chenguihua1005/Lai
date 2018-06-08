package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FucePhotoModel {
    private String ACMId;//照片ID
    private String ImgUrl;//复测图片
    private String weekth;//图片显示体管周
    private boolean isSelect;//是否选中

    public FucePhotoModel(String ACMId, String imgUrl, String weekth, boolean isSelect) {
        this.ACMId = ACMId;
        ImgUrl = imgUrl;
        this.weekth = weekth;
        this.isSelect = isSelect;
    }

    public String getACMId() {
        return ACMId;
    }

    public void setACMId(String ACMId) {
        this.ACMId = ACMId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getWeekth() {
        return weekth;
    }

    public void setWeekth(String weekth) {
        this.weekth = weekth;
    }
}
