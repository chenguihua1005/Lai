/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.EventModel;

import com.softtek.lai.module.jingdu.model.RankModel;

import java.util.List;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public class RankEvent {
    private List<RankModel> ranks;

    public List<RankModel> getRanks() {
        return ranks;
    }

    public RankEvent(List<RankModel> ranks) {
        this.ranks = ranks;
    }

    public void setRanks(List<RankModel> ranks) {
        this.ranks = ranks;
    }
}
