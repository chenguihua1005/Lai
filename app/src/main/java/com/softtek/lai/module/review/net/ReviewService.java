package com.softtek.lai.module.review.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/12/2016.
 */
public interface ReviewService {

    //获取往期回顾的班级列表
    @GET("/HerbrClass/GetClass")
    void getClass(@Header("token")String token,
                  @Query("classtype")String classtype,
                  RequestCallback<ResponseData<List<ClassInfoModel>>> callback);
}
