package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FucePhotoModel {
    private String ImgUrl;//复测图片
    private String weekth;//图片显示体管周
    private boolean isSelect;//是否选中

    public FucePhotoModel(String imgUrl, String weekth, boolean isSelect) {
        ImgUrl = imgUrl;
        this.weekth = weekth;
        this.isSelect = isSelect;
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
