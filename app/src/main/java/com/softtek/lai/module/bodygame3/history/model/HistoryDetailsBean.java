package com.softtek.lai.module.bodygame3.history.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jia.lu on 12/10/2016.
 */

public class HistoryDetailsBean implements Serializable{


    private List<ListWeightBean> list_Weight;
    private List<ListWeightPerBean> list_WeightPer;
    private List<ListFatPerBean> list_FatPer;
    private List<ListTop1Bean> list_Top1;
    private boolean CanReCreate;

    public boolean isCanReCreate() {
        return CanReCreate;
    }

    public void setCanReCreate(boolean canReCreate) {
        CanReCreate = canReCreate;
    }

    public List<ListWeightBean> getList_Weight() {
        return list_Weight;
    }

    public void setList_Weight(List<ListWeightBean> list_Weight) {
        this.list_Weight = list_Weight;
    }

    public List<ListWeightPerBean> getList_WeightPer() {
        return list_WeightPer;
    }

    public void setList_WeightPer(List<ListWeightPerBean> list_WeightPer) {
        this.list_WeightPer = list_WeightPer;
    }

    public List<ListFatPerBean> getList_FatPer() {
        return list_FatPer;
    }

    public void setList_FatPer(List<ListFatPerBean> list_FatPer) {
        this.list_FatPer = list_FatPer;
    }

    public List<ListTop1Bean> getList_Top1() {
        return list_Top1;
    }

    public void setList_Top1(List<ListTop1Bean> list_Top1) {
        this.list_Top1 = list_Top1;
    }

    public static class ListWeightBean implements Serializable{

        private String GroupName;
        private String LossPer;
        private String GroupId;
        private String LossWeight;

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getLossPer() {
            return LossPer;
        }

        public void setLossPer(String LossPer) {
            this.LossPer = LossPer;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }

        public String getLossWeight() {
            return LossWeight;
        }

        public void setLossWeight(String LossWeight) {
            this.LossWeight = LossWeight;
        }
    }

    public static class ListWeightPerBean implements Serializable{

        private String GroupName;
        private String LossPer;
        private String GroupId;
        private String LossWeight;

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getLossPer() {
            return LossPer;
        }

        public void setLossPer(String LossPer) {
            this.LossPer = LossPer;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }

        public String getLossWeight() {
            return LossWeight;
        }

        public void setLossWeight(String LossWeight) {
            this.LossWeight = LossWeight;
        }
    }

    public static class ListFatPerBean implements Serializable{
        private String GroupName;
        private String LossPer;
        private String GroupId;
        private String LossWeight;

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getLossPer() {
            return LossPer;
        }

        public void setLossPer(String LossPer) {
            this.LossPer = LossPer;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }

        public String getLossWeight() {
            return LossWeight;
        }

        public void setLossWeight(String LossWeight) {
            this.LossWeight = LossWeight;
        }
    }

    public static class ListTop1Bean implements Serializable{

        private int AccountId;
        private String UserName;
        private String Photo;
        private String Loss;

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getLoss() {
            return Loss;
        }

        public void setLoss(String Loss) {
            this.Loss = Loss;
        }
    }
}
