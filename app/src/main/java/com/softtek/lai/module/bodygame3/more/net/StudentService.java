package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.HnumsModel;
import com.softtek.lai.module.bodygame3.more.model.HonorModel;
import com.softtek.lai.module.bodygame3.more.model.ServiceTeam;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 *
 * Created by jarvis.Liu on 3/31/2016.
 */
public interface StudentService {

    //荣誉榜
    @GET("/Index/New_GetStudentHonor")
    void getStudentHonorPC(
            @Header("token") String token,
            @Query("accountid") String accountid,
            Callback<ResponseData<HonorModel>> callback
    );

    //获取学员奖章个数
    @GET("/HerbUser/New_GetStudentHonours")
    void getStudentHonours(
            @Header("token") String token,
            Callback<ResponseData<HnumsModel>> callback
    );

    //服务团队
    @GET("/v1/MsgCenter/GetServiceGroup")
    void getServiceTeam(
            @Header("token") String token,
            @Query("classId") String classId,
            Callback<ResponseData<ServiceTeam>> callback
    );



}
