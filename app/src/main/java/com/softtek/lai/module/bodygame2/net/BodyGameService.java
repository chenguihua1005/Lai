package com.softtek.lai.module.bodygame2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.SearchMemberModel;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
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
            @Query("accountid")String accountid,
            RequestCallback<ResponseData<ClassChangeModel>>callback
    );
    //获取学员列表

    @GET("/NewClass/ClMemberChange")
    void  doClMemberChange(
            @Header("token")String token,
            @Query("accountid")String accoutid,
            @Query("classid")String classid,
            @Query("type")String type,
            RequestCallback<ResponseData<MemberChangeModel>> callback
    );

    //获取班级列表

    @GET("/NewClass/GetClasslist")
    void  doGetClasslist(
            @Header("token")String token,
            @Query("accountid")String accountid,
            RequestCallback<ResponseData<ClassModel>> callback
    );
    //获取个人资料
    @GET("/NewClass/GetClmemberDetial")
    void doGetClmemberDetial(
            @Header("token")String token,
            @Query("roletype")int roletype,
            @Query("accountid")String accountid,
            @Query("classid")String classid,
            RequestCallback<ResponseData<memberDetialModel>>callback
    );
    //顾问首页
    @GET("/HerbNewUser/GetSPIndexInformation")
    void getSPIndexInformation(@Header("token")String token,
                               RequestCallback<ResponseData<SPBodyGameInfo>> callback);

    //首页SP检索
    @GET("/HerbNewUser/SearchMember")
    void doSearchMember(
            @Header("token")String token,
            @Query("AccountId")String AccountId,
            @Query("Key")String Key,
            RequestCallback<ResponseData<SearchMemberModel>>callback
    );
    //首页SR检索
    @GET("/HerbNewUser/SR_SearchMember")
    void doSearchSRMember(
            @Header("token")String token,
            @Query("AccountId")String AccountId,
            @Query("Key")String Key,
            RequestCallback<ResponseData<SearchMemberModel>>callback
    );

    //移除班级
    @POST("/NewClass/ClmemberExit")
    void doClmemberExit(
            @Header("token")String token,
            @Query("accountid")String accountid,
            @Query("classid")String classid,
            RequestCallback<ResponseData>callback
    );
}
