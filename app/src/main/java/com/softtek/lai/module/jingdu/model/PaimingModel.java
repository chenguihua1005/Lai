package com.softtek.lai.module.jingdu.model;

/**
 * Created by julie.zhu on 4/16/2016.
 */
public class PaimingModel {
    private int paiming;

    public PaimingModel(int paiming) {
        this.paiming = paiming;
    }

    public int getPaiming() {
        return paiming;
    }

    public void setPaiming(int paiming) {
        this.paiming = paiming;
    }

    @Override
    public String toString() {
        return "PaimingModel{" +
                "paiming=" + paiming +
                '}';
    }
}
