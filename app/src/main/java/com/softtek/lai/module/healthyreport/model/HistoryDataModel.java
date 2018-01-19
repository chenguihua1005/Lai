package com.softtek.lai.module.healthyreport.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/20/2016.
 * 接受服务器请求的历史数据model
 */
public class HistoryDataModel {


    /**
     * pageIndex : 1
     * totalPages : 1
     * records : [{"recordId":"cb657073-bb2a-4d6c-a6f2-351de0cf33d0","week":"周五","measuredTime":"4-14 16:01","weight":"44.2","bodyFatRate":"","viscusFatIndex":"2","sourceType":0}]
     */

    private int pageIndex;
    private int totalPages;
    private List<RecordsBean> records;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * recordId : cb657073-bb2a-4d6c-a6f2-351de0cf33d0
         * week : 周五
         * measuredTime : 4-14 16:01
         * weight : 44.2
         * bodyFatRate :
         * viscusFatIndex : 2
         * sourceType : 0
         */

        private String recordId;
        private String week;
        private String measuredTime;
        private String weight;
        private String bodyFatRate;
        private String viscusFatIndex;
        private int sourceType;
        private String classId;
        private String className;

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

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getMeasuredTime() {
            return measuredTime;
        }

        public void setMeasuredTime(String measuredTime) {
            this.measuredTime = measuredTime;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBodyFatRate() {
            return bodyFatRate;
        }

        public void setBodyFatRate(String bodyFatRate) {
            this.bodyFatRate = bodyFatRate;
        }

        public String getViscusFatIndex() {
            return viscusFatIndex;
        }

        public void setViscusFatIndex(String viscusFatIndex) {
            this.viscusFatIndex = viscusFatIndex;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }
    }
}
