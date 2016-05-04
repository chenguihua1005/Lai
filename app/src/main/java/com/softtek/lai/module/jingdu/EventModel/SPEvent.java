package com.softtek.lai.module.jingdu.EventModel;

import com.softtek.lai.module.jingdu.model.SPModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/4/2016.
 */
public class SPEvent {
    private List<SPModel> spModels;

    public List<SPModel> getSpModels() {
        return spModels;
    }

    public SPEvent(List<SPModel> spModels) {
        this.spModels = spModels;
    }

    public void setSpModels(List<SPModel> spModels) {
        this.spModels = spModels;
    }
}
