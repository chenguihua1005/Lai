package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.SearchModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 2017/3/10.
 */

public interface SearchService {

    @GET("/v1/LaiClassRoom/SearchArticle")
    void getChaosInfo(@Header("token") String token,
                      @Query("accountid")long accountId,
                      @Query("keyword")String keyword,
                      @Query("pageindex")int pageIndex,
                      @Query("pagesize")int pageSize,
                      RequestCallback<ResponseData<SearchModel>> callback);
}
