package com.softtek.lai.module.community.eventModel;

/**
 * Created by jerry.guan on 2/17/2017.
 */

public enum Where {

    DYNAMIC_LIST(0),
    FOCUS_LIST(1),
    PHOTOWALL_LIST(2),
    PERSONAL_DYNAMIC_LIST(3),
    TOPIC_DETAIL_LIST(4),
    DYNAMIC_DETAIL(5);

    int value;

    Where(int value) {
        this.value = value;
    }
}
