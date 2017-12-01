package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 11/22/2017.
 */

public class InviteModel {

    /**
     * Items : [{"AccountId":124525,"UserName":"王肇康","Mobile":"13584185022","Photo":null,"Certification":"CN458965"},{"AccountId":104447,"UserName":"黄小仁","Mobile":"13509002191","Photo":null,"Certification":"CN1454582"},{"AccountId":103662,"UserName":"李懿鑫","Mobile":"13510122862","Photo":"201708130125237762038920.png","Certification":"CN2863676"},{"AccountId":103657,"UserName":"jane","Mobile":"13961749156","Photo":null,"Certification":"CN1357499"}]
     * PageCount : 188
     */

    private int PageCount;
    private List<ItemsBean> Items;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class ItemsBean {
        /**
         * AccountId : 124525
         * UserName : 王肇康
         * Mobile : 13584185022
         * Photo : null
         * Certification : CN458965
         */

        private int AccountId;
        private String UserName;
        private String Mobile;
        private String Photo;
        private String Certification;
        private int Status;

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

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

        public String getCertification() {
            return Certification;
        }

        public void setCertification(String Certification) {
            this.Certification = Certification;
        }
    }
}
