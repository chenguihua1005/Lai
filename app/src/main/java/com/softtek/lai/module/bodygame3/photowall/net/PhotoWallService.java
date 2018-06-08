package com.softtek.lai.module.bodygame3.photowall.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.photowall.model.PhotoWallListModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 12/13/2016.
 */

public interface PhotoWallService {

    //删除照片墙
    @POST("/V1/HealthyCircle/DeletePhotoWall")
    void deletePhotoWall(@Header("token")String token,
                      @Query("healthid") String healthId,
                      RequestCallback<ResponseData> callback);

    //照片墙
    @GET("/V1/HealthyCircle/GetPhotoWalls")
    void doGetPhotoWalls(
            @Header("classid")String cId,
            @Header("token")String token,
            @Query("Loginaccid")long accountId,//用户id
            @Query("ClassId")String ClassId,//班级id
            @Query("PageIndex")int PageIndex,//第几页
            @Query("PageSize")int PageSize,//一页几条
            Callback<ResponseData<PhotoWallListModel>> callback
    );

    //评论
    @FormUrlEncoded
    @POST("/V1/HealthyCircle/CommitComments")
    void commitComment(
            @Header("token")String token,
            @Field("AccountId") long accountId,//用户id
            @Field("HealthId") String healthId,//照片墙id
            @Field("Comments") String comment,//评论内容
            Callback<ResponseData> callback
    );
}
