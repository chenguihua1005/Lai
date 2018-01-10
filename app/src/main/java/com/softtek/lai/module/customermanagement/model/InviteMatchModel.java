package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 1/8/2018.
 */

public class InviteMatchModel {

    /**
     * ClassId : 4e043c99-72e2-4208-b8a2-248c3e86725d
     * ClassName : 苹果班11
     * ClassGroups : [{"CGId":"2e1b658b-875c-49ef-89a6-e0e7ddd99505","CGName":"默认小组1"}]
     * ClassRoles : [{"ClassRoleId":2,"ClassRoleName":"组教练"},{"ClassRoleId":3,"ClassRoleName":"助教"},{"ClassRoleId":4,"ClassRoleName":"学员"}]
     */

    private String ClassId;
    private String ClassName;
    private List<ClassGroupsBean> ClassGroups;
    private List<ClassRolesBean> ClassRoles;

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

    public List<ClassGroupsBean> getClassGroups() {
        return ClassGroups;
    }

    public void setClassGroups(List<ClassGroupsBean> ClassGroups) {
        this.ClassGroups = ClassGroups;
    }

    public List<ClassRolesBean> getClassRoles() {
        return ClassRoles;
    }

    public void setClassRoles(List<ClassRolesBean> ClassRoles) {
        this.ClassRoles = ClassRoles;
    }

    public static class ClassGroupsBean {
        /**
         * CGId : 2e1b658b-875c-49ef-89a6-e0e7ddd99505
         * CGName : 默认小组1
         */

        private String CGId;
        private String CGName;

        public String getCGId() {
            return CGId;
        }

        public void setCGId(String CGId) {
            this.CGId = CGId;
        }

        public String getCGName() {
            return CGName;
        }

        public void setCGName(String CGName) {
            this.CGName = CGName;
        }
    }

    public static class ClassRolesBean {
        /**
         * ClassRoleId : 2
         * ClassRoleName : 组教练
         */

        private int ClassRoleId;
        private String ClassRoleName;

        public int getClassRoleId() {
            return ClassRoleId;
        }

        public void setClassRoleId(int ClassRoleId) {
            this.ClassRoleId = ClassRoleId;
        }

        public String getClassRoleName() {
            return ClassRoleName;
        }

        public void setClassRoleName(String ClassRoleName) {
            this.ClassRoleName = ClassRoleName;
        }
    }
}
