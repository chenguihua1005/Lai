package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 11/18/2016.
 * 更多界面接口
 */

public interface MoreService {


    //获取小区列表
    @GET("/V1/MoreFunction/GetRegionalAndCitys")
    void getRegionalAndCitys(@Header("token")String token,
                             Callback<ResponseData<List<SmallRegion>>> callback);
    //创建班级
    @POST("/V1/MoreFunction/EstablishClass")
    void creatClass(@Header("token")String token,
                    @Body LaiClass clazz,
                    Callback<ResponseData<LaiClass>> callback);

    //获取更多页面的班级数据
    @GET("/V1/MoreFunction/GetMoreInfo")
    void getMoreInfo(@Header("token")String token,
                     @Query("AccountId")long accountId,
                     Callback<ResponseData<List<ClassModel>>> callback);

}
