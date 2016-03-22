package com.softtek.lai.module.retest.model;

import com.softtek.lai.module.retest.eventModel.BanJiEvent;

import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class Banji extends BanJiEvent {

        private long id;
        private long ClassId;
        private String ClassName;
        private long ManagerId;
        private String StartDate;
        private int Total;

    public Banji(List<Banji> banjis) {
        super(banjis);
    }


    @Override
    public String toString() {
        return "Banji{" +
                "id=" + id +
                ", ClassId=" + ClassId +
                ", ClassName='" + ClassName + '\'' +
                ", ManagerId=" + ManagerId +
                ", StartDate='" + StartDate + '\'' +
                ", Total=" + Total +
                '}';
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClassId() {
            return ClassId;
        }

        public void setClassId(long classId) {
            ClassId = classId;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public long getManagerId() {
            return ManagerId;
        }

        public void setManagerId(long managerId) {
            ManagerId = managerId;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String startDate) {
            StartDate = startDate;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int total) {
            Total = total;
        }

}
