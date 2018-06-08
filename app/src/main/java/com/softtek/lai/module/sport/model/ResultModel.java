/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ResultModel implements Serializable {

    private String errNum;
    private String retMsg;
    private RetDataModel retData;

    @Override
    public String toString() {
        return "ResultModel{" +
                "errNum='" + errNum + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", retData=" + retData +
                '}';
    }

    public RetDataModel getRetData() {
        return retData;
    }

    public void setRetData(RetDataModel retData) {
        this.retData = retData;
    }

    public String getErrNum() {
        return errNum;
    }

    public void setErrNum(String errNum) {
        this.errNum = errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
