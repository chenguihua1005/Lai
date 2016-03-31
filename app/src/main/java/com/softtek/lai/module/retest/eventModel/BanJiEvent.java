/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.BanjiModel;

import java.util.List;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class BanJiEvent {

    private List<BanjiModel> banjiModels;

    public BanJiEvent(List<BanjiModel> banjiModels) {
        this.banjiModels = banjiModels;
    }

    public List<BanjiModel> getBanjiModels() {
        return banjiModels;
    }

    public void setBanjiModels(List<BanjiModel> banjiModels) {
        this.banjiModels = banjiModels;
    }
}
