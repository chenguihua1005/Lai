package com.softtek.lai.module.retest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.retest.model.Banji;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/21/2016.
 */
public interface RestService {
    @GET("/MeasuredRecordLog/ShowAllClassListBySP")
    void doGetRetestclass(@Header("token")String token,
                          @Query("id")long id,
                          Callback<ResponseData<List<Banji>>> callback);
}
