package com.softtek.lai.module.bodygame3.history.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.head.model.PhotoWallListModel;
import com.softtek.lai.module.bodygame3.history.model.HistoryDetailsBean;
import com.softtek.lai.utils.RequestCallback;

import okhttp3.RequestBody;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 12/9/2016.
 */

public interface HistoryService {
    @GET("/V1/HealthyCircle/GetPhotoWalls")
    void getClassDynamic(@Header("token") String token,
                         @Query("Loginaccid") long LoginaccId,
                         @Query("ClassId") String classId,
                         @Query("PageIndex") String pageIndex,
                         @Query("PageSize") String pageSize,
                         RequestCallback<ResponseData<PhotoWallListModel>> callback);

    @GET("/V1/HistoryClass/GetHistoryClassDetails")
    void getHistoryInfo(@Header("token") String token,
                        @Query("AccountId") long accountId,
                        @Query("ClassId") String classId,
                        RequestCallback<ResponseData<HistoryDetailsBean>> callback);

    @FormUrlEncoded
    @POST("/V1/HealthyCircle/CommitComments")
    void postCommnents(@Header("token") String token,
                       @Field("HealthId") String healthId,
                       @Field("AccountId") long accountId,
                       @Field("Comments") String comments,
                       RequestCallback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/HealthyCircle/InsertThumbUp")
    void postZan(@Header("token") String token,
                 @Field("AccountId") long userId,
                 @Field("Username") String username,
                 @Field("HealthId") String healthId,
                 RequestCallback<ResponseData> callback);
}
