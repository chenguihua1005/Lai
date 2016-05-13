package com.softtek.lai.module.laisportmine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public interface MineService {
    //我的跑团
    @GET("/HerbMyData/GetNowRgName")
    void doGetNowRgName(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData<RunTeamModel>>callback
    );
    //退出跑团
    @POST("/HerbMyData/SignOutRG")
    void doSignOutRG(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData>callback
    );
    //慈善列表
    @GET("/SportMsg/GetDonateMsg")
    void doGetDonateMsg(
            @Header("token")String token,
            @Query("accountid")String accountid,
            Callback<ResponseData<List<PublicWewlfModel>>>callback
    );
    //更改慈善、活动消息阅读时间
    @POST("/SportMsg/UpdateMsgRTime")
    void doUpdateMsgRTime(
            @Header("token")String token,
            @Query("accountid")String accountid,
            @Query("type")String type,
            Callback<ResponseData>callback
    );
    //活动列表
    @GET("/SportMsg/GetActiveMsg")
    void GetActiveMsg(
            @Header("token")String token,
            @Query("accountid")String accountid,
            Callback<ResponseData<List<ActionModel>>>callback
    );
    //PK通知
    @GET("/Challenged/GetPKINotice")
    void doGetPKINotice(
            @Header("token")String token,
            Callback<ResponseData<List<PkNoticeModel>>>callback
    );
    //删除通知
    @POST("/MsgCenter/DelNoticeOrMeasureMsg")
    void doDelNoticeOrMeasureMsg(
            @Header("token")String token,
            @Query("MessageId")String MessageId,
            Callback<ResponseData>callback
    );
}
