package com.softtek.lai.module.bodygame2sr.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2sr.model.SRBodyGameInfo;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 7/15/2016.
 */
public interface BodyGameSRService {
    //班级主页
    @GET("/NewClass/ClassMainIndex")
    void doClassMainIndex(
            @Header("token")String token,
            @Query("accountid")String accountid,
            RequestCallback<ResponseData<ClassMainModel>> callback
    );
    //切换班级
    @GET("/NewClass/ClassChangeById")
    void  doClassChangeById(
            @Header("token")String token,
            @Query("classid")String classid,
            @Query("accountid")String accountid,
            RequestCallback<ResponseData<ClassChangeModel>>callback
    );
    //获取学员列表

    @GET("/NewClass/ClMemberChange")
    void  doClMemberChange(
            @Header("token")String token,
            @Query("accountid")String accountid,
            @Query("classid")String classid,
            @Query("type")String type,
            RequestCallback<ResponseData<MemberChangeModel>> callback
    );

    //获取SR首页信息
    @GET("/HerbNewUser/GetSRIndexInformation")
    void getSRIndexInformation(@Header("token")String token,
                               RequestCallback<ResponseData<SRBodyGameInfo>> callback);
}
