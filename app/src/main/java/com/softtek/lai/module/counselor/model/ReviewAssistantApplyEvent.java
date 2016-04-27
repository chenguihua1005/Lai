/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/24/2016.
 */
public class ReviewAssistantApplyEvent {

    int posion;

    @Override
    public String toString() {
        return "ReviewAssistantApplyEvent{" +
                "posion=" + posion +
                '}';
    }

    public int getPosion() {
        return posion;
    }

    public void setPosion(int posion) {
        this.posion = posion;
    }

    public ReviewAssistantApplyEvent(int posion) {

        this.posion = posion;
    }
}
