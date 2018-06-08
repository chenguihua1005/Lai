package com.softtek.lai.module.laisportmine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.model.SystemNewsModel;
import com.softtek.lai.module.message2.model.NoticeModel;

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

    //慈善列表/V1/MsgCenter/GetDonateMsgList
    @GET("/V1/MsgCenter/GetDonateMsgList")
    void doGetDonateMsg(
            @Header("token")String token,
            @Query("accountid")String accountid,
            Callback<ResponseData<List<NoticeModel>>>callback
    );
    //系统消息
    @GET("/SportMsg/GetSysMsg")
    void doGetSysMsg(
            @Header("token")String token,
            @Query("accountid")String accountid,
            Callback<ResponseData<List<SystemNewsModel>>>callback
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
    @GET("/V1/MsgCenter/GetChallMsgList")
    void doGetPKINotice(
            @Header("token")String token,
            @Query("accountid")String accountid,
            Callback<ResponseData<List<PkNoticeModel>>>callback
    );
    //删除通知
    @POST("/MsgCenter/DelNoticeOrMeasureMsg")
    void doDelNoticeOrMeasureMsg(
            @Header("token")String token,
            @Query("MessageId")String MessageId,
            @Query("type")String type,
            Callback<ResponseData>callback
    );
    //删除pk通知
    @POST("/SportMsg/DelPKMsg")
    void doDelPKMsg(
            @Header("token")String token,
            @Query("MessageId")String MessageId,
            Callback<ResponseData>callback
    );

}
