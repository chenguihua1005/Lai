/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class AssistantClassEvent {

    List<AssistantClassInfoModel> list;

    @Override
    public String toString() {
        return "AssistantClassEvent{" +
                "list=" + list +
                '}';
    }

    public AssistantClassEvent(List<AssistantClassInfoModel> list) {
        this.list = list;
    }

    public List<AssistantClassInfoModel> getList() {

        return list;
    }

    public void setList(List<AssistantClassInfoModel> list) {
        this.list = list;
    }
}
