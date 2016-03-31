/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.model;

import java.io.File;

/**
 * Created by jerry.guan on 3/29/2016.
 */
public interface BannerUpdateCallBack {

    void onSuccess(String bannerUrl, File image);

    void onFailed();
}
