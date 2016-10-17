package com.softtek.lai.module.sport2;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.group.model.SportMainModel;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by jerry.guan on 10/17/2016.
 */
public interface SportService {

    //莱运动-首页
    @FormUrlEncoded
    @POST("/HerbSports/GetSportIndex")
    void getSportIndex(@Header("token") String token,
                       @Field("AccountId") long accountid,
                       @Field("DateTimeTotalStep") String todaystep,
                       RequestCallback<ResponseData<SportMineModel>> callback);
}
