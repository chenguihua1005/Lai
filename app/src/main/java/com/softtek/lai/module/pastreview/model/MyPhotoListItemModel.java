package com.softtek.lai.module.pastreview.model;

import java.util.List;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class MyPhotoListItemModel {
    private String LLId;
    private List<String> ImgUrl;
    private List<String> CreateDate;
    private List<String> Weight;

    public List<String> getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(List<String> createDate) {
        CreateDate = createDate;
    }

    public List<String> getWeight() {
        return Weight;
    }

    public void setWeight(List<String> weight) {
        Weight = weight;
    }

    public String getLLId() {
        return LLId;
    }

    public void setLLId(String LLId) {
        this.LLId = LLId;
    }

    public List<String> getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        ImgUrl = imgUrl;
    }

}
