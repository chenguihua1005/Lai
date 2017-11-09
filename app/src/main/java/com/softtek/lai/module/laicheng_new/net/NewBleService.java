package com.softtek.lai.module.laicheng_new.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng_new.model.BleResponseData;
import com.softtek.lai.module.laicheng_new.model.PostQnData;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 2017/10/27.
 */

public interface NewBleService {
    @POST("/v1/DataSync/UploadData6")
    void uploadTestData(@Header("token") String token,
                        @Query("accountId") long accountId,
                        @Query("type") int type,
                        @Query("classId") String classId,
                        @Body PostQnData postQnData,
                        RequestCallback<ResponseData<BleResponseData>> callback);
}
