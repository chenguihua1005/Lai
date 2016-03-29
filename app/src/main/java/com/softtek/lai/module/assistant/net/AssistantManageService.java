package com.softtek.lai.module.assistant.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.assistant.model.AssistantApplyInfo;
import com.softtek.lai.module.assistant.model.AssistantClassInfo;
import com.softtek.lai.module.assistant.model.AssistantDetailInfo;
import com.softtek.lai.module.assistant.model.AssistantInfo;
import com.softtek.lai.module.assistant.model.InviteStudentInfo;
import com.softtek.lai.module.counselor.model.Assistant;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface AssistantManageService {
    @GET("/HerbAssistant/ShowAllApplyAssistants")
    void showAllApplyAssistants(@Header("token") String token,
                                @Query("accountId") String accountId,
                                Callback<ResponseData<List<AssistantApplyInfo>>> callback);

    @GET("/HerbAssistant/ShowAllClassList")
    void showAllClassList(@Header("token") String token,
                          @Query("managerId") String managerId,
                          Callback<ResponseData<List<AssistantClassInfo>>> callback);

    @GET("/HerbAssistant/ShowAssistantByClass")
    void showAssistantByClass(@Header("token") String token,
                              @Query("managerId") String managerId,
                              @Query("classId") String classId,
                              Callback<ResponseData<List<AssistantInfo>>> callback);

    @GET("/HerbAssistant/ShowAssistantDetails")
    void showAssistantDetails(@Header("token") String token,
                              @Query("assistantId") String assistantId,
                              @Query("classId") String classId,
                              Callback<ResponseData<AssistantDetailInfo>> callback);

    @POST("/HerbAssistant/ReviewAssistantApplyLists")
    void reviewAssistantApplyList(@Header("token") String token,
                                  @Query("applyId") long applyId,
                                  @Query("status") int status,
                                  Callback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/SendInviterMsg")
    void sendInviterMsg(@Header("token") String token,
                        @Field("Inviters") String inviters,
                        @Field("ClassId") String classId,
                        Callback<ResponseData> callback);

    @GET("/HerbrClass/GetNotInvitePC")
    void getNotInvitePC(@Header("token") String token,
                        @Query("classid") String classid,
                        @Query("spaccid") String spaccid,
                        Callback<ResponseData<List<InviteStudentInfo>>> callback);


}
