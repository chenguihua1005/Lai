package com.softtek.lai.module.bodygame.eventmodel;

import com.softtek.lai.module.bodygame.model.TotolModel;

import java.util.List;

/**
 * Created by lareina.qiao on 4/11/2016.
 */
public class TotalEventModel {
    private List<TotolModel> totolModels;

    @Override
    public String toString() {
        return "TotalEventModel{" +
                "totolModels=" + totolModels +
                '}';
    }

    public List<TotolModel> getTotolModels() {
        return totolModels;
    }

    public void setTotolModels(List<TotolModel> totolModels) {
        this.totolModels = totolModels;
    }
}
