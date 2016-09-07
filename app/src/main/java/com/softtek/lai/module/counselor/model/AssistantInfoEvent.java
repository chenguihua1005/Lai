/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class AssistantInfoEvent {

    public List<AssistantInfoModel> list;

    public List<AssistantInfoModel> getList() {
        return list;
    }

    public void setList(List<AssistantInfoModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AssistantInfoEvent{" +
                "list=" + list +
                '}';
    }

    public AssistantInfoEvent(List<AssistantInfoModel> list) {
        this.list = list;
    }
}
