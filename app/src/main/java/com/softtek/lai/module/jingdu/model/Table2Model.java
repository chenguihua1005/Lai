package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 4/15/2016.
 */
public class Table2Model {
    private String LoseWeight;   //累计减重数
    private String ClassId;	    //班级id
    private String ClassName;	//班级名称
    private String WeightRate;

    public Table2Model(String loseWeight, String classId, String className, String weightRate) {
        LoseWeight = loseWeight;
        ClassId = classId;
        ClassName = className;
        WeightRate = weightRate;
    }

    public String getLoseWeight() {
        return LoseWeight;
    }

    public void setLoseWeight(String loseWeight) {
        LoseWeight = loseWeight;
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

    public String getWeightRate() {
        return WeightRate;
    }

    public void setWeightRate(String weightRate) {
        WeightRate = weightRate;
    }

    @Override
    public String toString() {
        return "Table2Model{" +
                "LoseWeight='" + LoseWeight + '\'' +
                ", ClassId='" + ClassId + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", WeightRate='" + WeightRate + '\'' +
                '}';
    }
}
