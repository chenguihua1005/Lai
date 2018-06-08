/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.FileModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public interface FileService {

//    @FormUrlEncoded
//    @POST("/HerbUser/CreatFile")
//    void doFile(
//                @Header("token") String token,
//                @Field("nickname") String nickname,
//                @Field("brithday") String birthday,
//                @Field("height") Integer height,
//                @Field("weight") Integer weight,
//                @Field("gender") Integer gender,
//            @Field("circum") double circum,
//            @Field("waistline") double waistline,
//            @Field("hiplie") double hiplie,
//            @Field("uparmgirth") double uparmgirth,
//            @Field("upleggirth") double upleggirth,
//            @Field("doleggirth") double doleggirth,
//            Callback<ResponseData<FileModel>> callback);


    @POST("/HerbUser/CreatFile")
    void doFile(@Header("token") String token,
                @Body FileModel file,
                Callback<ResponseData<FileModel>> callback);


}
