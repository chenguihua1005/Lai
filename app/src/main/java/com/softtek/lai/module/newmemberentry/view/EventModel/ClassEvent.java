/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.EventModel;

import com.softtek.lai.module.newmemberentry.view.model.PargradeModel;

import java.util.List;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public class ClassEvent {
    private List<PargradeModel> pargradeModels;

    public List<PargradeModel> getPargradeModels() {
        return pargradeModels;
    }

    public ClassEvent(List<PargradeModel> pargradeModels) {
        this.pargradeModels = pargradeModels;
    }

    public void setPargradeModels(List<PargradeModel> pargradeModels) {
        this.pargradeModels = pargradeModels;
    }
}
