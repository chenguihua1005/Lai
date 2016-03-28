package com.softtek.lai.module.newmemberentry.view.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.Pargrade;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 3/23/2016.
 */
public interface GuwenService {
    //顾问拥有的班级
    @GET("/HerbNewUser/GetClassBySP")
    void doGetGuwenClass(
            @Header("token") String token,
            @Query("managerId")long managerId,
            Callback<ResponseData<List<Pargrade>>>callback
                         );
}
