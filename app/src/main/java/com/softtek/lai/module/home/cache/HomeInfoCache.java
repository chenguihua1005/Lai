/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.cache;

import com.softtek.lai.module.home.model.HomeInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry.guan on 3/18/2016.
 */
public class HomeInfoCache implements Serializable {

    private List<HomeInfo> infos;

    public HomeInfoCache() {
    }

    public HomeInfoCache(List<HomeInfo> infos) {
        this.infos = infos;
    }

    public List<HomeInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<HomeInfo> infos) {
        this.infos = infos;
    }
}
