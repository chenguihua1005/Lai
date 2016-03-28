package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.Banji;

import java.util.List;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class BanJiEvent {

    private List<Banji> banjis;

    public BanJiEvent(List<Banji> banjis) {
        this.banjis = banjis;
    }

    public List<Banji> getBanjis() {
        return banjis;
    }

    public void setBanjis(List<Banji> banjis) {
        this.banjis = banjis;
    }
}