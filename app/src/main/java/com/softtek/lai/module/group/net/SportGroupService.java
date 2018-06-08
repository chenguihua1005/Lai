package com.softtek.lai.module.group.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.group.model.CityModel;
import com.softtek.lai.module.group.model.DxqModel;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.model.MineResultModel;
import com.softtek.lai.module.group.model.SportMainModel;
import com.softtek.lai.module.group.model.StepResponseModel;
import com.softtek.lai.module.group.model.TotalGroupModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface SportGroupService {

    public static final String TOKEN = "token";

    //是否已加入跑团
    @GET("/HerbSports/IsJoinRunGroup")
    void isJoinRunGroup(@Header(TOKEN) String token,
                        @Query("accountid") String accountid,
                        RequestCallback<ResponseData<StepResponseModel>> callback);

    //获取大区列表
    @GET("/HerbSports/GetBregionList")
    void getBregionList(@Header(TOKEN) String token,
                        RequestCallback<ResponseData<List<DxqModel>>> callback);

    //根据城市load跑团
    @GET("/HerbSports/GetHQRGlist")
    void getHQRGlist(@Header(TOKEN) String token,
                     @Query("BregionId") String bregionId,
                     RequestCallback<ResponseData<List<GroupModel>>> callback);

    //获取小区列表
    @GET("/HerbSports/GetSregionList")
    void getSregionList(@Header(TOKEN) String token,
                        @Query("BregionId") String bregionId,
                        RequestCallback<ResponseData<List<DxqModel>>> callback);

    //获取城市列表
    @GET("/HerbSports/GetCityList")
    void getCityList(@Header(TOKEN) String token,
                     @Query("SregionId") String sregionId,
                     RequestCallback<ResponseData<List<CityModel>>> callback);

    //根据城市load跑团
    @GET("/HerbSports/GetRGListByCity")
    void getRGListByCity(@Header(TOKEN) String token,
                         @Query("CityId") String cityId,
                         RequestCallback<ResponseData<List<GroupModel>>> callback);

    //根据父级id获取跑团
    @GET("/HerbSports/GetRGListByPId")
    void getRGListByPId(@Header(TOKEN) String token,
                        @Query("RgId") String rgId,
                        RequestCallback<ResponseData<List<GroupModel>>> callback);

    //模糊查询跑团
    @GET("/HerbSports/GetRGByNameOrCode")
    void getRGByNameOrCode(@Header(TOKEN) String token,
                           @Query("str") String str,
                           RequestCallback<ResponseData<List<GroupModel>>> callback);


    //用户加入跑团
    @FormUrlEncoded
    @POST("/HerbSports/JoinRunGroup")
    void joinRunGroup(@Header(TOKEN) String token,
                      @Field("RGId") String rGId,
                      @Field("RGAccId") String rGAccId,
                      RequestCallback<ResponseData> callback);

    //莱运动-首页
    @FormUrlEncoded
    @POST("/HerbSports/GetSportIndex")
    void getSportIndex(@Header(TOKEN) String token,
                       @Field("AccountId") String accountid,
                       @Field("DateTimeTotalStep") String todaystep,
                       RequestCallback<ResponseData<SportMainModel>> callback);

    //莱运动-首页
    @FormUrlEncoded
    @POST("/HerbSports/GetMineResult")
    void getMineResult(@Header(TOKEN) String token,
                       @Field("AccountId") String accountid,
                       @Field("DateTimeTotalStep") String todaystep,
                       RequestCallback<ResponseData<MineResultModel>> callback);
}
