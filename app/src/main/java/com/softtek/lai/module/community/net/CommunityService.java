package com.softtek.lai.module.community.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.community.model.CommunityModel;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public interface CommunityService {

    //获取推荐健康圈内容
    @GET("/HealthyCircle/HealthRecommend")
    void getrecommendHealthyContent(@Query("accountid")long accountId,
                                    @Query("pageIndex")int pageIndex,
                                    RequestCallback<ResponseData<List<HealthyCommunityModel>>> callback);

    //获取健康圈我的内容
    @GET("/HealthyCircle/HealthMine")
    void getHealthyMine(@Header("token")String token,
                        @Query("accountid")int accountId,
                        @Query("pageIndex")int pageIndex,
                        RequestCallback<ResponseData<List<HealthyCommunityModel>>> callback);

    //保存我健康圈我的动态
    @POST("/HealthyCircle/GetReleaseDynamic")
    void saveDynamic(@Header("token")String token,
                     @Body CommunityModel model,
                     RequestCallback<ResponseData> callback);
}
