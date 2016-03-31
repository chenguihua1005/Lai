/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.cache;

import com.softtek.lai.module.home.model.HomeInfoModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry.guan on 3/18/2016.
 */
public class HomeInfoCache implements Serializable {

    private List<HomeInfoModel> infos;

    public HomeInfoCache() {
    }

    public HomeInfoCache(List<HomeInfoModel> infos) {
        this.infos = infos;
    }

    public List<HomeInfoModel> getInfos() {
        return infos;
    }

    public void setInfos(List<HomeInfoModel> infos) {
        this.infos = infos;
    }
}
