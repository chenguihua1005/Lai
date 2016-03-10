package com.softtek.lai.module.home.File.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.File.model.File;

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

    @FormUrlEncoded
    @POST("/HerbUser/CreatFile")
    void doFile(
                @Header("token") String token,
                @Field("nickname") String nickname,
                @Field("brithday") String birthday,
                @Field("height") Integer height,
                @Field("weight") Integer weight,
                @Field("gender") Integer gender,
                Callback<ResponseData<File>> callback);

//    @FormUrlEncoded
//    @POST("/HerbUser/CreatFile")
//    void doFile1(@Field("token")String token,
//                 @Body File file,
//                 Callback<ResponseData<File>> callback);


}
