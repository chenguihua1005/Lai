package com.softtek.lai.module.bodygame3.photowall.model;

/**
 * 话题模型
 * Created by jerry.guan on 2016/12/9.
 */
public class TopicModel {
    private String WordKeyId;//话题id
    private String WordKey;//话题关键字
    private String ThemePhoto;//话题图片
    private String ThemeHot;//话题热度
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getWordKeyId() {
        return WordKeyId;
    }

    public void setWordKeyId(String wordKeyId) {
        WordKeyId = wordKeyId;
    }

    public String getWordKey() {
        return WordKey;
    }

    public void setWordKey(String wordKey) {
        WordKey = wordKey;
    }

    public String getThemePhoto() {
        return ThemePhoto;
    }

    public void setThemePhoto(String themePhoto) {
        ThemePhoto = themePhoto;
    }

    public String getThemeHot() {
        return ThemeHot;
    }

    public void setThemeHot(String themeHot) {
        ThemeHot = themeHot;
    }
}
