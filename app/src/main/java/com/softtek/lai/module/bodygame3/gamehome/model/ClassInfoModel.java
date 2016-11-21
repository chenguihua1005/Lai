package com.softtek.lai.module.bodygame3.gamehome.model;

/**
 * Created by lareina.qiao on 2016/11/16.
 */

public class ClassInfoModel {
        private String ClassId;//班级标号
        private String ClassName ;//班级名称
        private String ClassWeek ;//班级所在周
        private String role ;//用户所在班级角色

        @Override
        public String toString() {
            return "ClassInfo{" +
                    "ClassId='" + ClassId + '\'' +
                    ", ClassName='" + ClassName + '\'' +
                    ", ClassWeek='" + ClassWeek + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String classId) {
            ClassId = classId;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public String getClassWeek() {
            return ClassWeek;
        }

        public void setClassWeek(String classWeek) {
            ClassWeek = classWeek;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

}
