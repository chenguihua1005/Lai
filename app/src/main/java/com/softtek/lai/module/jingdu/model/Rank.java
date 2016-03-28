package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class Rank {
    private String ran;
    private String name;
    private double LossAfter;
    private double LossBefore;
    private double LossWeight;

    public Rank(String ran, String name, double lossAfter, double lossBefore, double lossWeight) {
        this.ran = ran;
        this.name = name;
        LossAfter = lossAfter;
        LossBefore = lossBefore;
        LossWeight = lossWeight;
    }

    public String getRan() {
        return ran;
    }

    public void setRan(String ran) {
        this.ran = ran;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLossAfter() {
        return LossAfter;
    }

    public void setLossAfter(double lossAfter) {
        LossAfter = lossAfter;
    }

    public double getLossBefore() {
        return LossBefore;
    }

    public void setLossBefore(double lossBefore) {
        LossBefore = lossBefore;
    }

    public double getLossWeight() {
        return LossWeight;
    }

    public void setLossWeight(double lossWeight) {
        LossWeight = lossWeight;
    }
}
