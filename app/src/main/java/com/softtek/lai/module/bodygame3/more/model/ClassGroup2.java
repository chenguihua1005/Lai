package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by jerry.guan on 11/22/2016.
 * 班级小组模型
 */

public class ClassGroup2 {


    private String ClassGroupName;
    private String ClassGoupId;
    private int GrouperCount;//班级小组人数
    private List<ClassManagerListBean> ClassManagerList;

    private boolean CanBeDeleted;//是否可以删除，true-可以，false-不可以

    public boolean isCanBeDeleted() {
        return CanBeDeleted;
    }

    public void setCanBeDeleted(boolean canBeDeleted) {
        CanBeDeleted = canBeDeleted;
    }

    public String getClassGroupName() {
        return ClassGroupName;
    }

    public void setClassGroupName(String ClassGroupName) {
        this.ClassGroupName = ClassGroupName;
    }

    public String getClassGoupId() {
        return ClassGoupId;
    }

    public void setClassGoupId(String ClassGoupId) {
        this.ClassGoupId = ClassGoupId;
    }

    public int getGrouperCount() {
        return GrouperCount;
    }

    public void setGrouperCount(int GrouperCount) {
        this.GrouperCount = GrouperCount;
    }

    public List<ClassManagerListBean> getClassManagerList() {
        return ClassManagerList;
    }

    public void setClassManagerList(List<ClassManagerListBean> ClassManagerList) {
        this.ClassManagerList = ClassManagerList;
    }

    public static class ClassManagerListBean {


        private String CMPhoto;
        private long AccountId;
        private String CMUserName;
        private int ClassRole;

        public String getCMPhoto() {
            return CMPhoto;
        }

        public void setCMPhoto(String CMPhoto) {
            this.CMPhoto = CMPhoto;
        }

        public long getAccountId() {
            return AccountId;
        }

        public void setAccountId(long AccountId) {
            this.AccountId = AccountId;
        }

        public String getCMUserName() {
            return CMUserName;
        }

        public void setCMUserName(String CMUserName) {
            this.CMUserName = CMUserName;
        }

        public int getClassRole() {
            return ClassRole;
        }

        public void setClassRole(int ClassRole) {
            this.ClassRole = ClassRole;
        }
    }
}
