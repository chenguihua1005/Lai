package com.softtek.lai.module.laicheng_new.model;


import java.util.List;

public class CustomerData {

    /**
     * Items : [{"Mobile":"18912356130","Name":"杨峰","Gender":0,"Height":170,"BirthDay":"1990-1-1","Age":27,"Tag":false,"TagName":"未注册","Photo":"","Creator":"王肇康","CreatedTime":"2017年11月29日"},{"Mobile":"17706173856","Name":"大白菜","Gender":1,"Height":155,"BirthDay":"1990-11-24","Age":27,"Tag":false,"TagName":"未注册","Photo":"","Creator":"王肇康","CreatedTime":"2017年11月24日"}]
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
         * Mobile : 18912356130
         * Name : 杨峰
         * Gender : 0
         * Height : 170
         * BirthDay : 1990-1-1
         * Age : 27
         * Tag : false
         * TagName : 未注册
         * Photo :
         * Creator : 王肇康
         * CreatedTime : 2017年11月29日
         */

        private String Mobile;
        private String Name;
        private int Gender;
        private int Height;
        private String BirthDay;
        private int Age;
        private boolean Tag;
        private String TagName;
        private String Photo;
        private String Creator;
        private String CreatedTime;

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public String getBirthDay() {
            return BirthDay;
        }

        public void setBirthDay(String BirthDay) {
            this.BirthDay = BirthDay;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public boolean isTag() {
            return Tag;
        }

        public void setTag(boolean Tag) {
            this.Tag = Tag;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String Creator) {
            this.Creator = Creator;
        }

        public String getCreatedTime() {
            return CreatedTime;
        }

        public void setCreatedTime(String CreatedTime) {
            this.CreatedTime = CreatedTime;
        }
    }
}
