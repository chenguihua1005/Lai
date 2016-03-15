package com.softtek.lai.module.File.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public interface  FileService {

//    @FormUrlEncoded
//    @POST("/HerbUser/CreatFile")
//    void doFile(
//                @Header("token") String token,
//                @Field("nickname") String nickname,
//                @Field("brithday") String birthday,
//                @Field("height") Integer height,
//                @Field("weight") Integer weight,
//                @Field("gender") Integer gender,
//                Callback<ResponseData<File>> callback);
//
//    @FormUrlEncoded
//    @POST("/HerbUser/CreatFile")
//    void addDimensionRecord(
//            @Header("token") String token,
//            @Field("circum") double circum,
//            @Field("waistline") double waistline,
//            @Field("hiplie") double hiplie,
//            @Field("uparmgirth") double uparmgirth,
//            @Field("upleggirth") double upleggirth,
//            @Field("doleggirth") double doleggirth,
//            Callback<ResponseData<File>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/CreatFile")
    void doFile(@Field("token")String token,
                 @Body File file,
                 Callback<ResponseData<File>> callback);


}
