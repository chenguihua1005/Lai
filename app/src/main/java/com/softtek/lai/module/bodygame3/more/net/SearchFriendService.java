package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.SearchFriendModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 2/24/2017.
 */

public interface SearchFriendService {
    //查询莱聚+成员加好友
    @GET("/v1/HerbUser/FindFriends")
    void doFindFriends(
            @Header("token")String token,
            @Query("keywords")String keywords,
            @Query("pageIndex")int pageIndex,
            Callback<ResponseData<SearchFriendModel>>callback
    );
}
