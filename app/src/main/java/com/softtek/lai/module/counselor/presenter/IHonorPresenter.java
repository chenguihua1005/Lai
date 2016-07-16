/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface IHonorPresenter {

    //获取荣誉榜
    void getSPHonor();

    //获取荣誉榜
    void getSRHonor();

    //SP分享荣誉信息(XX天共减重多少斤)
    void getUserHonors();

    //助教SR分享荣誉信息(XX天共减重多少斤)
    void getShareSRHonor();

    //SP分享当期
    void getSPDangQiHonor();
}
