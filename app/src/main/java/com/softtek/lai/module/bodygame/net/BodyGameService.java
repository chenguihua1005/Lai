/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.FuceNumModel;
import com.softtek.lai.module.bodygame.model.TiGuanSaiModel;
import com.softtek.lai.module.bodygame.model.TipsDetailModel;
import com.softtek.lai.module.bodygame.model.TipsModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public interface BodyGameService {

    //banner接口
    @GET("/Index/TiGuanSaiClick")
    void doGetTiGuanSaiClickw(@Header("token") String token,
                              Callback<ResponseData<TiGuanSaiModel>> callback);

    @GET("/MeasuredRecordLog/GetNotMeasuredMemberCountBySRP")
    void doGetFuceNum(
            @Header("token") String token,
            @Query("id") long id,
            Callback<ResponseData<FuceNumModel>> callback
    );
    //tip
    @GET("/HerbTips/GetTipsList")
    void doGetTips(
            @Header("token")String token,
            Callback<ResponseData<List<TipsModel>>>callback
    );
    @GET("/HerbTips/GetTipsContentById")
    void doGetTipsDetail(
            @Header("token")String token,
            @Query("id")long id,
            Callback<ResponseData<List<TipsDetailModel>>>callback
    );
}
