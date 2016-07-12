package com.softtek.lai.module.bodygame2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public interface BodyGameService {
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
            RequestCallback<ResponseData<ClassChangeModel>>callback
    );
    //切换班级
    @GET("/NewClass/ClMemberChange")
    void  doClMemberChange(
            @Header("token")String token,
            @Query("classid")String classid,
            RequestCallback<ResponseData<MemberChangeModel>>callback
    );
    //获取个人资料
    @GET("/NewClass/GetClmemberDetial")
    void doGetClmemberDetial(
            @Header("token")String token,
            @Query("accountid")String accountid,
            @Query("classid")String classid,
            RequestCallback<RequestCallback<memberDetialModel>>callback
    );
}
