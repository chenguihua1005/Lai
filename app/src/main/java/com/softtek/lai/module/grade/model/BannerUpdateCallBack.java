package com.softtek.lai.module.grade.model;

import java.io.File;

/**
 * Created by jerry.guan on 3/29/2016.
 */
public interface BannerUpdateCallBack {

    void onSuccess(String bannerUrl, File image);

    void onFailed();
}
