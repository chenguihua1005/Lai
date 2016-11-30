package com.softtek.lai.module.bodygame3.conversation.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.conversation.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.model.FriendModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
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
            Callback<ResponseData<List<ClassMemberModel>>> callback
    );

    //新朋友列表

    @GET("/v1/HerbUser/FriendApplyList")
    void getFriendApplyList(
            @Header("token") String token,
            @Query("accountId") long accountId,
            Callback<ResponseData<List<FriendModel>>> callback
    );


}
