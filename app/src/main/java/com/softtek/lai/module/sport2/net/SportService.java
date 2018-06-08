package com.softtek.lai.module.sport2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.group.model.SportMainModel;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.module.sport2.model.Unread;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 10/17/2016.
 */
public interface SportService {

    //莱运动-首页
    @FormUrlEncoded
    @POST("/HerbSports/NewGetSportIndex")
    void getSportIndex(@Header("token") String token,
                       @Field("AccountId") long accountid,
                       @Field("DateTimeTotalStep") String todaystep,
                       RequestCallback<ResponseData<SportMineModel>> callback);

    //消息提醒
    @GET("/SportMsg/GetNewMsgRemind")
    void getNewMsgRemind(@Header("token") String token,
                         @Query("AccountId") String accountId,
                         RequestCallback<ResponseData<Unread>> callback);

}
