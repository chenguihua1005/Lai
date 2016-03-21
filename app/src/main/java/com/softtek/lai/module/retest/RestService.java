package com.softtek.lai.module.retest;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.retest.model.Banji;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by lareina.qiao on 3/21/2016.
 */
public interface RestService {
    @GET("/api/MeasuredRecordLog/ShowAllClassListBySP")
    void doGetTiGuanSaiClickw(@Field("id") long id,
                              Callback<ResponseData<Banji>> callback);
}
