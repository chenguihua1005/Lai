package com.softtek.lai.module.bodygame3.conversation.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.conversation.model.ClassListInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.model.FriendModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jessica.zhang on 11/25/2016.
 */

public interface ContactService {
    /**
     * 踢馆赛3.0  获取通讯录接口
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @param callback
     */

    @GET("/v1//HerbUser/GetContacts")
    void getEMChatContacts(
            @Header("token") String token,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<List<ChatContactModel>>> callback
    );


    @GET("/v1/HerbUser/GetClassListByAccountId")
    void GetClassListByAccountId(
            @Header("token") String token,
            @Query("accountId") String accountId,
            Callback<ResponseData<List<ContactClassModel>>> callback
    );

    /**
     * 班级群聊成员列表
     *
     * @param classId
     * @param pageIndex
     * @param pageSize
     * @param callback
     */

    @GET("/v1/HerbUser/GetContactsByClassId")
    void GetContactsByClassId(
            @Header("token") String token,
            @Query("classId") String classId,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<ClassListInfoModel>> callback
    );

    //新朋友列表

    @GET("/v1/HerbUser/FriendApplyList")
    void getFriendApplyList(
            @Header("token") String token,
            @Query("accountId") long accountId,
            Callback<ResponseData<List<FriendModel>>> callback
    );

    //移除好友申请信息


    @POST("/v1/HerbUser/RemoveFriendApplyInfo")
    void removeFriendApplyInfo(
            @Header("token") String token,
            @Query("applyId") String applyId,
            Callback<ResponseData> callback
    );


    //好友申请审批( 同意按钮)

    @POST("/v1/HerbUser/ReviewFriendApplication")
    void reviewFriendApplication(
            @Header("token") String token,
            @Query("applyId") String applyId,
            @Query("isAgree") int isAgree,
            Callback<ResponseData> callback
    );

    //解散班级环信群
    @GET("/v1/MsgCenter/DissolutionHxGroup")
    void dissolutionHxGroup(
            @Header("token") String token,
            @Query("ClassId") String ClassId,
            Callback<ResponseData> callback
    );

    //加好友(个人详情)
    @POST("/v1/HerbUser/SentFriendApply")
    void sentFriendApply(
            @Header("token") String token,
            @Query("senderId") long senderId,
            @Query("receiverId") long receiverId,
            @Query("classId") String classId,
            Callback<ResponseData> callback
    );

    // 移除好友
    @POST("/v1/HerbUser/RemoveFriend")
    void removeFriend(
            @Header("token") String token,
            @Query("accountId") long accountId,
            @Query("rid") String rid,//好友关系ID（通讯录接口的AFriendId字段值）
            Callback<ResponseData> callback
    );


}
