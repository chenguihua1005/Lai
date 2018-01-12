package com.softtek.lai.module.customermanagement.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.customermanagement.model.InviteMatchModel;
import com.softtek.lai.module.customermanagement.view.InviteMatchActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 1/8/2018.
 */

public interface InviteService {
    @GET("/v1/Club/GetListOfClassesWithDetail")
    void getListOfClassesWithDetail(@Header("token") String token,
                                    RequestCallback<ResponseData<List<InviteMatchModel>>> callbackRequestCallback);

    @POST("/v1/SMS/InviteFromCustomer")
    void getCodeFromMobile(@Header("token")String token,
                            @Query("mobile")String mobile,RequestCallback<ResponseData<InviteMatchActivity.RecCode>> callback);

    @POST("/v1/Club/InviteFromCustomer")
    void InviteFromCustomer(@Header("token") String token,
                            @Body InviteMatchActivity.PostData postData, RequestCallback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/v1/Club/SaveInitialMeasuredData")
    void saveInitialMeasuredData(@Header("token") String token,
                                 @Field("AccountId") long accountId,
                                 @Field("ClassId") String classId,
                                 @Field("Weight") float weight,
                                 @Field("Pysical") float bodyFat,
                                 @Field("Fat") float internalFat,
                                 RequestCallback<ResponseData> callback);
}
