package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 11/21/2017.
 */

public class PersonnelModel {


    /**
     * Clubs : [{"ID":"cc54f8d9-c854-4df6-9633-0024dd4fb3ef","Name":"芭提雅"},{"ID":"e109e224-21df-48c7-8745-0036b4f914be","Name":"忆秦园"},{"ID":"a7fc72d1-54d6-45dc-ac38-003023ebb662","Name":"宝芝林"},{"ID":"7e50c2d8-9ac5-45a7-9c2f-0062d23c9f67","Name":"天下会"}]
     * TotalCustomer : 5
     * TodayCustomer : 4
     * Workers : [{"AccountId":1018,"Name":"张泽东","Mobile":"13439105043","Photo":null,"TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0},{"AccountId":1020,"Name":"石荣","Mobile":"15101665311","Photo":"201608251109450257524163.png","TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0}]
     * Self : {"TotalCustomer":0,"TodayCustomer":0,"TotalMarketingStaff":0,"TodayMarketingStaff":0}
     */

    private int TotalCustomer;
    private int TodayCustomer;
    private SelfBean Self;
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

    public SelfBean getSelf() {
        return Self;
    }

    public void setSelf(SelfBean Self) {
        this.Self = Self;
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

    public static class SelfBean {
        /**
         * TotalCustomer : 0
         * TodayCustomer : 0
         * TotalMarketingStaff : 0
         * TodayMarketingStaff : 0
         */

        private int TotalCustomer;
        private int TodayCustomer;
        private int TotalMarketingStaff;
        private int TodayMarketingStaff;

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

    public static class ClubsBean {
        /**
         * ID : cc54f8d9-c854-4df6-9633-0024dd4fb3ef
         * Name : 芭提雅
         */

        private String ID;
        private String Name;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
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
