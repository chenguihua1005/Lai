package com.softtek.lai.module.bodygame2.model;

/**
 * Created by lareina.qiao on 7/12/2016.
 */
public class memberDetialModel {
    private ClmInfoModel ClmInfo;
    private LossStoryModel LossStory;
    private HonorListModel HonorList;
    private PesonDatePhotoModel PhotoList;

    public memberDetialModel(ClmInfoModel clmInfo, LossStoryModel lossStory, HonorListModel honorList, PesonDatePhotoModel photoList) {
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

    public HonorListModel getHonorList() {
        return HonorList;
    }

    public void setHonorList(HonorListModel honorList) {
        HonorList = honorList;
    }

    public PesonDatePhotoModel getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(PesonDatePhotoModel photoList) {
        PhotoList = photoList;
    }
}
