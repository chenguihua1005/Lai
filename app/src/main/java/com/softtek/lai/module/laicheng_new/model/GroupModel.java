package com.softtek.lai.module.laicheng_new.model;

import java.util.List;

/**
 * Created by jia.lu on 3/15/2018.
 */

public class GroupModel {

    /**
     * classId : f686d093-0347-42fe-8dac-15751999bea3
     * className : 2
     * members : [{"AccountId":1,"Mobile":"13361810000","UserName":"3310","Gender":1,"Height":155,"BirthDay":"1990-3-5","Age":28,"Photo":"2018_03_05/201803051728237237856572.jpg","ClassId":"f686d093-0347-42fe-8dac-15751999bea3","ClassName":"2"}]
     */

    private String classId;
    private String className;
    private List<MembersBean> members;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public static class MembersBean {
        /**
         * AccountId : 1
         * Mobile : 13361810000
         * UserName : 3310
         * Gender : 1
         * Height : 155
         * BirthDay : 1990-3-5
         * Age : 28
         * Photo : 2018_03_05/201803051728237237856572.jpg
         * ClassId : f686d093-0347-42fe-8dac-15751999bea3
         * ClassName : 2
         */

        private long AccountId;
        private String Mobile;
        private String UserName;
        private int Gender;
        private int Height;
        private String BirthDay;
        private int Age;
        private String Photo;
        private String ClassId;
        private String ClassName;

        public long getAccountId() {
            return AccountId;
        }

        public void setAccountId(long AccountId) {
            this.AccountId = AccountId;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
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

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

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
    }
}
