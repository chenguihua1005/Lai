/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-16
 */

package com.softtek.lai.common;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class ResponseData<T> {

    private int status;
    private String msg;
    private int PageCount;
    private T data;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "state=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
