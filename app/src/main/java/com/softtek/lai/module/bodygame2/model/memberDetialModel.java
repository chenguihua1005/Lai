package com.softtek.lai.module.bodygame2.model;

import java.util.List;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class memberDetialModel {
    private ClmInfoModel ClmInfo;
    private LossStoryModel LossStory;
    private List<HonorListModel> HonorList;
    private List<PesonDatePhotoModel> PhotoList;

    public memberDetialModel(ClmInfoModel clmInfo, LossStoryModel lossStory, List<HonorListModel> honorList, List<PesonDatePhotoModel> photoList) {
        ClmInfo = clmInfo;
        LossStory = lossStory;
        HonorList = honorList;
        PhotoList = photoList;
    }

    public ClmInfoModel getClmInfo() {
        return ClmInfo;
    }

    public void setClmInfo(ClmInfoModel clmInfo) {
        ClmInfo = clmInfo;
    }

    public LossStoryModel getLossStory() {
        return LossStory;
    }

    public void setLossStory(LossStoryModel lossStory) {
        LossStory = lossStory;
    }

    public List<HonorListModel> getHonorList() {
        return HonorList;
    }

    public void setHonorList(List<HonorListModel> honorList) {
        HonorList = honorList;
    }

    public List<PesonDatePhotoModel> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<PesonDatePhotoModel> photoList) {
        PhotoList = photoList;
    }
}
