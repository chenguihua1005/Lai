package com.softtek.lai.module.bodygame3.conversation.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;

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

}
