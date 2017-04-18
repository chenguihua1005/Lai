package com.softtek.lai.module.healthyreport.model;

import java.util.List;

/**
 * Created by jerry.guan on 4/17/2017.
 */

public class HealthyChartModel {


    /**
     * itemId : 17e0b83a-6ccc-4846-956c-0264c71c8a54
     * indexDescription : <font color="#ff6666">你的体重超标啦！</font><br />体重是反映和衡量一个人健康状况的重要指标之一，偏瘦或超重都不利于人体健康，也会影响一个人外形的美感。<br />请同时关注你的体脂率。如果体脂率和体重两个指标都高于理想值，说明你有肥胖的倾向；肥胖是各种慢性疾病酝酿的温床！请注意控制热量的摄入，采纳合理的营养搭配，每天运动至少30分钟，循序渐进，让营养顾问帮你制定一个科学的减重计划吧！请记住，减重不等于减肥，减脂才等于减肥哦！
     * colorBar : {"value":74.2,"unit":"kg","color":"ff6666","arrowColor":"db5151","range":[{"value":67.6,"color":"ff6666","valueTip":""},{"value":60,"color":"93c952","valueTip":""},{"value":35,"color":"66b3ff","valueTip":""},{"value":125,"color":"","valueTip":""}]}
     * chart : {"unit":"","valueList":[{"date":"2017-04-17","value":0},{"date":"2017-04-16","value":0},{"date":"2017-04-15","value":88.4},{"date":"2017-04-14","value":74.2},{"date":"2017-04-13","value":0},{"date":"2017-04-12","value":0},{"date":"2017-04-11","value":0},{"date":"2017-04-10","value":0}]}
     */

    private String itemId;
    private String indexDescription;
    private ColorBar colorBar;
    private ChartBean chart;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getIndexDescription() {
        return indexDescription;
    }

    public void setIndexDescription(String indexDescription) {
        this.indexDescription = indexDescription;
    }

    public ColorBar getColorBar() {
        return colorBar;
    }

    public void setColorBar(ColorBar colorBar) {
        this.colorBar = colorBar;
    }

    public ChartBean getChart() {
        return chart;
    }

    public void setChart(ChartBean chart) {
        this.chart = chart;
    }

    public static class ColorBar {
        /**
         * value : 74.2
         * unit : kg
         * color : ff6666
         * arrowColor : db5151
         * range : [{"value":67.6,"color":"ff6666","valueTip":""},{"value":60,"color":"93c952","valueTip":""},{"value":35,"color":"66b3ff","valueTip":""},{"value":125,"color":"","valueTip":""}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowColor;
        private List<Range> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowColor() {
            return arrowColor;
        }

        public void setArrowColor(String arrowColor) {
            this.arrowColor = arrowColor;
        }

        public List<Range> getRange() {
            return range;
        }

        public void setRange(List<Range> range) {
            this.range = range;
        }

        public static class Range {
            /**
             * value : 67.6
             * color : ff6666
             * valueTip :
             */

            private double value;
            private String color;
            private String valueTip;

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getValueTip() {
                return valueTip;
            }

            public void setValueTip(String valueTip) {
                this.valueTip = valueTip;
            }
        }
    }

    public static class ChartBean {
        /**
         * unit :
         * valueList : [{"date":"2017-04-17","value":0},{"date":"2017-04-16","value":0},{"date":"2017-04-15","value":88.4},{"date":"2017-04-14","value":74.2},{"date":"2017-04-13","value":0},{"date":"2017-04-12","value":0},{"date":"2017-04-11","value":0},{"date":"2017-04-10","value":0}]
         */

        private String unit;
        private String startDate;
        private List<ValueList> valueList;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public List<ValueList> getValueList() {
            return valueList;
        }

        public void setValueList(List<ValueList> valueList) {
            this.valueList = valueList;
        }

        public static class ValueList {
            /**
             * date : 2017-04-17
             * value : 0
             */

            private String date;
            private int value;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
