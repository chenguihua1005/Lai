/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.*;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface CounselorService {
    @GET("/HerbrClass/GetSR")
    void getAssistantList(@Header("token") String token,
                          @Query("ClassId") String classId,
                          Callback<ResponseData<List<AssistantModel>>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/SendInviterSR")
    void sendInviterSR(@Header("token") String token,
                       @Field("ClassId") String classId,
                       @Field("Inviters") String Inviters,
                       Callback<ResponseData> callback);

    @GET("/HerbrClass/GetClass")
    void getClassList(@Header("token") String token,
                      Callback<ResponseData<List<ClassInfoModel>>> callback);

    @GET("/Index/GetSPHonor")
    void getSPHonor(@Header("token") String token,
                    Callback<ResponseData<HonorInfoModel>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/CreateClass")
    void createClass(@Header("token") String token,
                     @Field("ClassName") String className,
                     @Field("StartDate") String startDate,
                     @Field("EndDate") String endDate,
                     @Field("ManagerId") String managerId,
                     Callback<ResponseData<ClassIdModel>> callback);

    @GET("/HerbAssistant/ShowAllApplyAssistants")
    void showAllApplyAssistants(@Header("token") String token,
                                @Query("accountId") String accountId,
                                Callback<ResponseData<List<AssistantApplyInfoModel>>> callback);

    @GET("/HerbAssistant/ShowAllClassList")
    void showAllClassList(@Header("token") String token,
                          @Query("managerId") String managerId,
                          Callback<ResponseData<List<AssistantClassInfoModel>>> callback);

    @GET("/HerbAssistant/ShowAssistantByClass")
    void showAssistantByClass(@Header("token") String token,
                              @Query("managerId") String managerId,
                              @Query("classId") String classId,
                              Callback<ResponseData<List<AssistantInfoModel>>> callback);

    @GET("/HerbAssistant/ShowAssistantDetails")
    void showAssistantDetails(@Header("token") String token,
                              @Query("assistantId") String assistantId,
                              @Query("classId") String classId,
                              Callback<ResponseData<AssistantDetailInfoModel>> callback);

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
                        Callback<ResponseData<List<InviteStudentInfoModel>>> callback);

    @GET("/Index/GetMatchInfo")
    void getMatchInfo(@Header("token") String token,
                      @Query("dtime") String dtime,
                      @Query("group") String group,
                      Callback<ResponseData<List<MarchInfoModel>>> callback);
}
