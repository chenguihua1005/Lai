package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.CollectModel;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public interface CollectService {
    //获取收藏列表的接口
    @GET("/v1/LaiClassRoom/GetBookmarkArticle")
    void getBookmarkArticle(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<CollectModel>> callback
    );

    //获取历史列表的接口
    @GET("/v1/LaiClassRoom/GetVisitArticleHistory")
    void getVisitArticleHistory(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<CollectModel>> callback
    );

    //莱课堂增加点击量与浏览记录
    @FormUrlEncoded
    @POST("/v1/LaiClassRoom/AddHotAndHistory")
    void getAddHotAndHistory(
            @Header("token") String token,
            @Field("accountid") long accountid,
            @Field("articleid") String articleid,
            Callback<ResponseData> callback
    );


    //专题详情页
    @GET("/v1/LaiClassRoom/GetTopicArticleList")
    void getSubjectdetail(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("topicid") String topicid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<CollectModel>> callback
    );

}
