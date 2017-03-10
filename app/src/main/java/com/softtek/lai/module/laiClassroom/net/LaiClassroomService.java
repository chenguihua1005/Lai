package com.softtek.lai.module.laiClassroom.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laiClassroom.model.MonographicListModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 3/8/2017.
 */

public interface LaiClassroomService {
    @GET("/v1/LaiClassRoom/GetArticleTopic")
    void doGetArticleTopic(
            @Header("token") String token,
            @Query("pageindex")int pageindex,
            @Query("pagesize")int pagesize,
            Callback<ResponseData<MonographicListModel>>callback
    );


}
