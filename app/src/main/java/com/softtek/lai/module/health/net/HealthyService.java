package com.softtek.lai.module.health.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.health.model.HealthyRecordModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by John on 2016/4/12.
 */
public interface HealthyService {

        @GET("/HealthRecords/GetHealthRecords")
        void doGetHealth(
                @Header("token") String token,
                RequestCallback<ResponseData<List<HealthyRecordModel>>> callback);

}
