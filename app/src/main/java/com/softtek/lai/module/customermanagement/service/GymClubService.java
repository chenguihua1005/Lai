package com.softtek.lai.module.customermanagement.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.customermanagement.model.GymModel;
import com.softtek.lai.module.customermanagement.model.UnionClassModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 1/3/2018.
 */

public interface GymClubService {
    @GET("/v1/Club/GetClubClasses")
    void getClubClasses(@Header("token") String token, RequestCallback<ResponseData<List<GymModel>>> callback);

    @GET("/v1/Club/GetListOfClassJointly")
    void getListOfClassJointly(@Header("token") String token,
                               @Query("className") String className,
                               @Query("index") int index,
                               @Query("size") int size,
                               RequestCallback<ResponseData<UnionClassModel>> callback);

    @POST("/v1/Club/InviteClassJointly")
    void inviteClassJointly(@Header("token") String token,
                            @Query("classId") String classId,RequestCallback<ResponseData> callback);
}
