/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActZKModel implements Serializable {

    private ActZKPersonModel ActDetial;
    private String ActType;
    private String PageCount;
    private String Target;
    private List<ActDetiallistModel> ActDetiallist;

    @Override
    public String toString() {
        return "ActZKModel{" +
                "ActDetial=" + ActDetial +
                ", ActType='" + ActType + '\'' +
                ", PageCount='" + PageCount + '\'' +
                ", Target='" + Target + '\'' +
                ", ActDetiallist=" + ActDetiallist +
                '}';
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    public ActZKPersonModel getActDetial() {
        return ActDetial;
    }

    public void setActDetial(ActZKPersonModel actDetial) {
        ActDetial = actDetial;
    }

    public String getActType() {
        return ActType;
    }

    public void setActType(String actType) {
        ActType = actType;
    }

    public List<ActDetiallistModel> getActDetiallist() {
        return ActDetiallist;
    }

    public void setActDetiallist(List<ActDetiallistModel> actDetiallist) {
        ActDetiallist = actDetiallist;
    }
}
