package com.softtek.lai.module.bodygame2pc.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2pc.model.StumemberDetialModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 7/14/2016.
 */
public interface BodyGamePcService {
    //获取个人资料
    @GET("/NewClass/GetClmemberDetial")
    void doGetStuClmemberDetial(
            @Header("token")String token,
            @Query("roletype")int roletype,
            @Query("accountid")String accountid,
            @Query("classid")String classid,
            RequestCallback<ResponseData<StumemberDetialModel>> callback
    );
}
