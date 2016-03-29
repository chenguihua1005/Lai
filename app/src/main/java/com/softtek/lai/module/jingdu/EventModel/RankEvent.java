package com.softtek.lai.module.jingdu.EventModel;

import com.softtek.lai.module.jingdu.model.Rank;

import java.util.List;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public class RankEvent {
    private List<Rank> ranks;

    public List<Rank> getRanks() {
        return ranks;
    }

    public RankEvent(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}
