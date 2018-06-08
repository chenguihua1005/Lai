package com.softtek.lai.module.bodygame3.conversation.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.conversation.model.ClassListInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactListModel;
import com.softtek.lai.module.bodygame3.conversation.model.CountModel;
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

    @GET("/v1/HerbUser/GetContacts")
    void getEMChatContacts(
            @Header("token") String token,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<ContactListModel>> callback
    );

    //获取未处理好友申请数量

    @GET("/v1/HerbUser/GetFriendPendingCount")
    void getFriendPendingCount(
            @Header("token") String token,
            @Query("accountId") long accountId,
            Callback<ResponseData<CountModel>> callback
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
            @Header("classid") String CId,
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
            @Header("classid") String CId,
            @Query("ClassId") String ClassId,
            Callback<ResponseData> callback
    );

    //加好友(个人详情)
    @POST("/v1/HerbUser/SentFriendApply")
    void sentFriendApply(
            @Header("token") String token,
            @Header("classid") String CId,
            @Query("senderId") long senderId,
            @Query("receiverId") long receiverId,
            @Query("classId") String classId,
            Callback<ResponseData> callback
    );
    //加好友(通讯录全局搜索)
    @POST("/v1/HerbUser/SentFriendApply")
    void doSentFriendApply(
            @Header("token") String token,
            @Header("classid") String CId,
            @Query("senderId") long senderId,
            @Query("receiverId") long receiverId,
            @Query("classId") String classId,
            @Query("from")int from,
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

    //获取班级详情（环信群ID）
    @GET("/v1/HerbalifeClass/GetClassByHxGroupId")
    void getClassByHxGroupId(
            @Header("token") String token,
            @Query("hxGroupId") String hxGroupId,
            @Query("accountId") long accountId,
            Callback<ResponseData<ContactClassModel>> callback
    );

    //查看学员是否有加入环信群的消息
//    @GET("/v1/HerbalifeClass/HxInviteToGroup")
//    void getMsgHxInviteToGroup(
//            @Header("token") String token,
//            @Query("AccountId") long AccountId,
//            Callback<ResponseData<List<HxInviteToGroupModel>>> callback
//    );

    //告诉服务端加入环信群处理完成
    @GET("/v1/HerbalifeClass/CompleteJoinHx")
    void completeJoinHx(
            @Header("token") String token,
            @Query("classId") String classId,//班级Id
            @Query("MessageId") String MessageId,//处理消息Id
            Callback<ResponseData> callback
    );

}
