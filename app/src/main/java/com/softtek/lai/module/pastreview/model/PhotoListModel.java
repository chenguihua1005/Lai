package com.softtek.lai.module.pastreview.model;

/**
 * Created by lareina.qiao on 7/4/2016.
 */
public class PhotoListModel {
    private String LLId;
    private String ImgUrl;
    private String CreateDate;
    private String Weight;

    public String getLLId() {
        return LLId;
    }

    public void setLLId(String LLId) {
        this.LLId = LLId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    @Override
    public String toString() {
        return "PhotoListModel{" +
                "LLId='" + LLId + '\'' +
                ", ImgUrl='" + ImgUrl + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", Weight='" + Weight + '\'' +
                '}';
    }
}
