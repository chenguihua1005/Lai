package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 12/28/2017.
 */

public class UnionClassModel {

    /**
     * Items : [{"ClassId":"5a7747f4-c98a-4151-9897-3db0958f9b5a","ClassName":"11111","UserName":"30000","Photo":"2017_11_22/201711221713303397741655.jpg","CreateDate":"2018-1-3","Status":0}]
     * PageCount : 1
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
         * ClassId : 5a7747f4-c98a-4151-9897-3db0958f9b5a
         * ClassName : 11111
         * UserName : 30000
         * Photo : 2017_11_22/201711221713303397741655.jpg
         * CreateDate : 2018-1-3
         * Status : 0
         */

        private String ClassId;
        private String ClassName;
        private String UserName;
        private String Photo;
        private String CreateDate;
        private int Status;

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String ClassId) {
            this.ClassId = ClassId;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
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

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
