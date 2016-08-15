package com.softtek.lai.module.bodygame2pc.model;

import com.softtek.lai.module.pastreview.model.PhotoListModel;

import java.util.List;

/**
 * Created by lareina.qiao on 7/14/2016.
 */
public class StumemberDetialModel {
    private StuClmInfoModel ClmInfo;
    private StuLossStoryModel LossStory;
    private List<StuHonorListModel> HonorList;
    private List<PhotoListModel> PhotoList;

    public StuClmInfoModel getClmInfo() {
        return ClmInfo;
    }

    public void setClmInfo(StuClmInfoModel clmInfo) {
        ClmInfo = clmInfo;
    }

    public StuLossStoryModel getLossStory() {
        return LossStory;
    }

    public void setLossStory(StuLossStoryModel lossStory) {
        LossStory = lossStory;
    }

    public List<StuHonorListModel> getHonorList() {
        return HonorList;
    }

    public void setHonorList(List<StuHonorListModel> honorList) {
        HonorList = honorList;
    }

    public List<PhotoListModel> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<PhotoListModel> photoList) {
        PhotoList = photoList;
    }

    @Override
    public String toString() {
        return "StumemberDetialModel{" +
                "ClmInfo=" + ClmInfo +
                ", LossStory=" + LossStory +
                ", HonorList=" + HonorList +
                ", PhotoList=" + PhotoList +
                '}';
    }
}
