/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class AssistantApplyEvent {

    List<AssistantApplyInfoModel> lists;

    @Override
    public String toString() {
        return "AssistantApplyEvent{" +
                "lists=" + lists +
                '}';
    }

    public AssistantApplyEvent(List<AssistantApplyInfoModel> lists) {
        this.lists = lists;
    }

    public List<AssistantApplyInfoModel> getLists() {

        return lists;
    }

    public void setLists(List<AssistantApplyInfoModel> lists) {
        this.lists = lists;
    }
}
