package com.softtek.lai.module.jingdu.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.jingdu.model.Rank;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public interface JingduService {
    @GET("/HerbrClass/SearchClassMember")
    void getproinfo(@Header("token") String token,
                    @Query("classId")String classId,
                    @Query("ordertype")String ordertype,
                    Callback<ResponseData<List<Rank>>> callback);
}
