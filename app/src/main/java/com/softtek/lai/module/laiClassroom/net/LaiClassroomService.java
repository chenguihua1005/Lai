package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.FilteData;

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
                        @Query("type")String type,
                        @Query("topictype")String subjectId,
                        @Query("orderby")String order,
                        @Query("pageindex")int pageIndex,
                        @Query("pagesize")int pageSize,
                        Callback<ResponseData> callback);


}
