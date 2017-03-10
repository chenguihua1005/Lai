package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.CollectModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 3/10/2017.
 */

public interface CollectService {
    @GET("/v1/LaiClassRoom/GetBookmarkArticle")
    void getBookmarkArticle(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<CollectModel>> callback
    );
}
