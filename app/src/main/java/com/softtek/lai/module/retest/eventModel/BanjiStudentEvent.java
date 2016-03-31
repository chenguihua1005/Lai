/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.BanjiStudentModel;

import java.util.List;

/**
 * Created by lareina.qiao on 3/24/2016.
 */
public class BanjiStudentEvent {
    private List<BanjiStudentModel> banjiStudentModels;

    public BanjiStudentEvent(List<BanjiStudentModel> banjiStudentModels) {
        this.banjiStudentModels = banjiStudentModels;
    }

    public List<BanjiStudentModel> getBanjiStudentModels() {
        return banjiStudentModels;
    }

    public void setBanjiStudentModels(List<BanjiStudentModel> banjiStudentModels) {
        this.banjiStudentModels = banjiStudentModels;
    }
}
