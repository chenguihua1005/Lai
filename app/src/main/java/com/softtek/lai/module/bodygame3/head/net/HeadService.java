package com.softtek.lai.module.bodygame3.head.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.HeadModel2;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public interface HeadService {
    //    首页版本1接口
    @GET("/V1/HerbalifeClass/GetClassInfoDefaultHome")
    void getfirst(
            @Header("token") String token,
            @Query("accountid") String accountid,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<ClassinfoModel>> callback
    );

    //首页版本2
    @GET("/V1/HerbalifeClass/GetTotalPCandLoss")
    void getsecond(
            @Header("token") String token,
            Callback<ResponseData<HeadModel2>> callback
    );

    //检索班级
    @GET("")
    void getclass();

    //检索小伙伴
    @GET("/V1/HerbalifeClass/GetSearchClassPartner")
    void getpartner(
            @Header("token") String token,
            @Query("classid") String classid,
            @Query("keyword") String keyword,
            Callback<ResponseData<PartnersModel>> callback
    );

}
