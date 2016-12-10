package com.softtek.lai.module.bodygame3.history.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jia.lu on 12/9/2016.
 */

public interface HistoryService {
    @GET("/V1/HealthyCircle/GetPhotoWalls")
    void getClassDynamic(@Header("token")String token,
                         @Query("Loginaccid") long LoginaccId,
                         @Query("ClassId") String classId,
                         @Query("PageIndex") String pageIndex,
                         @Query("PageSize") String pageSize,
                         Callback<ResponseData<PhotoWallListModel>> callback);
}
