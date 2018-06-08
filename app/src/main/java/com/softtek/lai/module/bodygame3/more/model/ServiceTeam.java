package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * Created by curry.zhang on 1/6/2017.
 */

public class ServiceTeam {

    private String CoachName;   //总教练名称
    private String CoachImg;    //总教练头像
    private long CoachId;
    private List<ServiceModel> Services;

    public class ServiceModel {
        private String CGName;  //组别名
        private List<Waiter> Waiters;

        public String getCGName() {
            return CGName;
        }

        public void setCGName(String CGName) {
            this.CGName = CGName;
        }

        public List<Waiter> getWaiters() {
            return Waiters;
        }

        public void setWaiters(List<Waiter> waiters) {
            Waiters = waiters;
        }

        @Override
        public String toString() {
            return "ServiceModel{" +
                    "CGName='" + CGName + '\'' +
                    ", Waiters=" + Waiters +
                    '}';
        }
    }

    public class Waiter {
        private String WaiterName;  //成员名
        private String WaiterImg;   //成员头像
        private String ClassRole;   //成员角色
        private String WaiterCGId;  //小组Id
        private Long WaiterAccount;  //成员Id

        public Long getWaiterAccount() {
            return WaiterAccount;
        }

        public void setWaiterAccount(Long waiterAccount) {
            WaiterAccount = waiterAccount;
        }

        public Waiter() {
        }

        public String getWaiterName() {
            return WaiterName;
        }

        public void setWaiterName(String waiterName) {
            WaiterName = waiterName;
        }

        public String getWaiterImg() {
            return WaiterImg;
        }

        public void setWaiterImg(String waiterImg) {
            WaiterImg = waiterImg;
        }

        public String getClassRole() {
            return ClassRole;
        }

        public void setClassRole(String classRole) {
            ClassRole = classRole;
        }

        public String getWaiterCGId() {
            return WaiterCGId;
        }

        public void setWaiterCGId(String waiterCGId) {
            WaiterCGId = waiterCGId;
        }

        @Override
        public String toString() {
            return "Waiter{" +
                    "WaiterName='" + WaiterName + '\'' +
                    ", WaiterImg='" + WaiterImg + '\'' +
                    ", ClassRole='" + ClassRole + '\'' +
                    ", WaiterCGId='" + WaiterCGId + '\'' +
                    ", WaiterAccount=" + WaiterAccount +
                    '}';
        }
    }

    public String toString() {
        return "ServiceTeam{" +
                "CoachName='" + CoachName + '\'' +
                ", CoachImg='" + CoachImg + '\'' +
                ", Services=" + Services +
                '}';
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getCoachImg() {
        return CoachImg;
    }

    public void setCoachImg(String coachImg) {
        CoachImg = coachImg;
    }

    public List<ServiceModel> getServices() {
        return Services;
    }

    public void setServices(List<ServiceModel> services) {
        Services = services;
    }

    public long getCoachId() {
        return CoachId;
    }

    public void setCoachId(long coachId) {
        CoachId = coachId;
    }
}
