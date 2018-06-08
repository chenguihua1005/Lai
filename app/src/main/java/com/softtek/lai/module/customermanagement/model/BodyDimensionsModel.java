package com.softtek.lai.module.customermanagement.model;

/**
 * Created by jia.lu on 3/16/2018.
 */

public class BodyDimensionsModel {

    /**
     * ParamName : 胸围
     * Value : 90
     * Unit : cm
     */

    private String ParamName;
    private float Value;
    private String Unit;

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String ParamName) {
        this.ParamName = ParamName;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float Value) {
        this.Value = Value;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }
}
