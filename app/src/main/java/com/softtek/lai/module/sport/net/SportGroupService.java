package com.softtek.lai.module.sport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.sport.model.CityModel;
import com.softtek.lai.module.sport.model.DxqModel;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
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
                        RequestCallback<ResponseData> callback);

    //获取大区列表
    @GET("/HerbSports/GetBregionList")
    void getBregionList(@Header(TOKEN) String token,
                        RequestCallback<ResponseData<List<DxqModel>>> callback);

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
}
