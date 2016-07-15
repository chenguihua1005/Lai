package com.softtek.lai.module.bodygame2pc.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2pc.model.PCBodyGameInfo;
import com.softtek.lai.module.bodygame2pc.model.PCClassMainModel;
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
    //班级主页
    @GET("/NewClass/ClassMainIndex")
    void doClassMainIndex(
            @Header("token")String token,
            @Query("accountid")String accountid,
            RequestCallback<ResponseData<PCClassMainModel>> callback
    );
//获取学员列表

    @GET("/NewClass/ClMemberChange")
    void  doClMemberChange(
            @Header("token")String token,
            @Query("accoutid")String accoutid,
            @Query("classid")String classid,
            @Query("type")String type,
            RequestCallback<ResponseData<MemberChangeModel>> callback
    );

    void getPCBodyGameInfo(@Header("token")String token,
                           RequestCallback<ResponseData<PCBodyGameInfo>> callback);
}
