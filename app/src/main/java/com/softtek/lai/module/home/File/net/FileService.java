package com.softtek.lai.module.home.File.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.File.model.File;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public interface  FileService {

    @FormUrlEncoded
    @POST("/HerbUser/CreatFile")
    void doFile(@Field("appid") String appid,
                @Field("token") String token,
                @Field("nickname") String nickname,
                @Field("birthday") String birthday,
                @Field("height") Integer height,
                @Field("weight") Integer weight,
                @Field("gender") Integer gender,
                Callback<ResponseData<File>> callback);
}
