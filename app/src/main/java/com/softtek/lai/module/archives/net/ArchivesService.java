package com.softtek.lai.module.archives.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.archives.model.archives;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/10/2016.
 */
public interface ArchivesService {
    @FormUrlEncoded
    @POST("/HerbUser/CreatFile")
    void doCreatArchives(
            @Header("token")String token,
            @Field("nickname") String nickname,
            @Field("brithday") String birthday,
            @Field("height") Integer height,
            @Field("weight") Integer weight,
            @Field("gender") Integer gender,
            Callback<ResponseData<archives>>callback);

//    @FormUrlEncoded
//    @POST("/HerbUser/CreatFile")
//    void doFile1(@Field("token")String token,
//                 @Body File file,
//                 Callback<ResponseData<File>> callback);

}
