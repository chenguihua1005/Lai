package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.ArticalList;
import com.softtek.lai.module.laiClassroom.model.FilteData;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.model.VideoDetailModel;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public interface LaiClassroomService {

    @GET("/v1/LaiClassRoom/GetDefaultSelect")
    void getFilteData(@Header("token")String token,
                      @Query("accountid")String userId,
                      Callback<ResponseData<FilteData>> callback);

    @GET("/v1/LaiClassRoom/GetLaiArticleList")
    void getArticleList(@Header("token")String token,
                        @Query("accountid")String accountId,
                        @Query("type")String type,
                        @Query("topictype")String subjectId,
                        @Query("orderby")String order,
                        @Query("pageindex")int pageIndex,
                        @Query("pagesize")int pageSize,
                        Callback<ResponseData<ArticalList>> callback);
    //获取专题
    @GET("/v1/LaiClassRoom/GetArticleTopic")
    void doGetArticleTopic(
            @Header("token") String token,
            @Query("pageindex")int pageindex,
            @Query("pagesize")int pagesize,
            Callback<ResponseData<SubjectModel>>callback
    );

    @GET("/v1/LaiClassRoom/GetLaiVideo")
    void getVideoDetail(@Header("token")String token,
                        @Query("accountid")long accountId,
                        @Query("articleid")String articleId,
                        Callback<ResponseData<VideoDetailModel>> callback);

    //收藏
    @GET("/v1/LaiClassRoom/BookmarkArticle")
    void doLike(@Header("token")String token,
                @Query("accountid")long accountId,
                @Query("articleid")String articleId,
                Callback<ResponseData> callback);
    //取消收藏
    @GET("/v1/LaiClassRoom/CancleBookmarkArticle")
    void unLike(@Header("token")String token,
                @Query("accountid")long accountId,
                @Query("articleid")String articleId,
                Callback<ResponseData> callback);

}
