package com.softtek.lai.module.health.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.health.model.HealthDateModel;
import com.softtek.lai.module.health.model.HealthyRecordModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by John on 2016/4/12.
 */
public interface HealthyService {

        @GET("/HealthRecords/GetHealthRecords")
        void doGetHealth(
                @Header("token") String token,
                Callback<ResponseData<HealthDateModel>> callback);
        //获取体脂
        @GET("/HealthRecords/GetHealthPysicalRecords")
        void doGetHealthPysicalRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                Callback<ResponseData<PysicalModel>> callback
        );

}
