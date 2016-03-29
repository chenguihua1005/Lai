package com.softtek.lai.module.bodygame.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.FuceNum;
import com.softtek.lai.module.bodygame.model.TiGuanSai;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public interface BodyGameService {


    @GET("/Index/TiGuanSaiClick")
    void doGetTiGuanSaiClickw(@Header("token")String token,
                              Callback<ResponseData<TiGuanSai>> callback);
    @GET("/MeasuredRecordLog/GetNotMeasuredMemberCountBySRP")
    void doGetFuceNum(
            @Header("token")String token,
            @Query("id")long id,
            Callback<ResponseData<FuceNum>>callback
    );
}
