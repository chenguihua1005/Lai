package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class PersonnelModel {

    /**
     * Clubs : [{"ID":1,"Name":"芭提雅"},{"ID":4,"Name":"忆秦园"},{"ID":3,"Name":"宝芝林"},{"ID":2,"Name":"天下会"}]
     * TotalCustomer : 5
     * TodayCustomer : 4
     * Workers : [{"AccountId":1018,"Name":"张泽东","Mobile":"13439105043","Photo":null,"TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0},{"AccountId":1020,"Name":"石荣","Mobile":"15101665311","Photo":"201608251109450257524163.png","TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0},{"AccountId":1024,"Name":"毕云超","Mobile":"18608715476","Photo":null,"TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0},{"AccountId":1025,"Name":"郭宝池","Mobile":"13683297719","Photo":"2017_04_08/201704081141345194829936.jpg","TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0},{"AccountId":1027,"Name":"张小明","Mobile":"13551625419","Photo":null,"TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0}]
     */

    private int TotalCustomer;
    private int TodayCustomer;
    private List<ClubsBean> Clubs;
    private List<WorkersBean> Workers;

    public int getTotalCustomer() {
        return TotalCustomer;
    }

    public void setTotalCustomer(int TotalCustomer) {
        this.TotalCustomer = TotalCustomer;
    }

    public int getTodayCustomer() {
        return TodayCustomer;
    }

    public void setTodayCustomer(int TodayCustomer) {
        this.TodayCustomer = TodayCustomer;
    }

    public List<ClubsBean> getClubs() {
        return Clubs;
    }

    public void setClubs(List<ClubsBean> Clubs) {
        this.Clubs = Clubs;
    }

    public List<WorkersBean> getWorkers() {
        return Workers;
    }

    public void setWorkers(List<WorkersBean> Workers) {
        this.Workers = Workers;
    }

    public static class ClubsBean {
        /**
         * ID : 1
         * Name : 芭提雅
         */

        private int ID;
        private String Name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

    public static class WorkersBean {
        /**
         * AccountId : 1018
         * Name : 张泽东
         * Mobile : 13439105043
         * Photo : null
         * TotalCustomer : 0
         * TodayCustomer : 0
         * TotalMarketingStaff : 0
         * TodayMarketingStaff : 0
         */

        private int AccountId;
        private String Name;
        private String Mobile;
        private String Photo;
        private int TotalCustomer;
        private int TodayCustomer;
        private int TotalMarketingStaff;
        private int TodayMarketingStaff;

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public int getTotalCustomer() {
            return TotalCustomer;
        }

        public void setTotalCustomer(int TotalCustomer) {
            this.TotalCustomer = TotalCustomer;
        }

        public int getTodayCustomer() {
            return TodayCustomer;
        }

        public void setTodayCustomer(int TodayCustomer) {
            this.TodayCustomer = TodayCustomer;
        }

        public int getTotalMarketingStaff() {
            return TotalMarketingStaff;
        }

        public void setTotalMarketingStaff(int TotalMarketingStaff) {
            this.TotalMarketingStaff = TotalMarketingStaff;
        }

        public int getTodayMarketingStaff() {
            return TodayMarketingStaff;
        }

        public void setTodayMarketingStaff(int TodayMarketingStaff) {
            this.TodayMarketingStaff = TodayMarketingStaff;
        }
    }
}
