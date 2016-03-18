package com.softtek.lai.module.retest.net;

import com.softtek.lai.common.ResponseData;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public interface RetestService {
    @GET("api/MeasuredRecordLog/ShowAllClassListBySP")
    void dogetclass(
            @Field("id") long id,
            Callback<ResponseData>callback
    );
}
