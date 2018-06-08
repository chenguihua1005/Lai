package com.softtek.lai.module.healthyreport.model;

/**
 * Created by jia.lu on 3/15/2018.
 */

public class BodyDimensions {
    private String ParamName;
    private float Value;
    private String Unit;

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
