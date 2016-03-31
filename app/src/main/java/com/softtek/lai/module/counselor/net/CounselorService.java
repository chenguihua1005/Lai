package com.softtek.lai.module.counselor.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.Assistant;
import com.softtek.lai.module.counselor.model.AssistantApplyInfo;
import com.softtek.lai.module.counselor.model.AssistantClassInfo;
import com.softtek.lai.module.counselor.model.AssistantDetailInfo;
import com.softtek.lai.module.counselor.model.AssistantInfo;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.model.HonorInfo;
import com.softtek.lai.module.counselor.model.InviteStudentInfo;
import com.softtek.lai.module.counselor.model.MarchInfo;

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
public interface CounselorService {
    @GET("/HerbrClass/GetSR")
    void getAssistantList(@Header("token") String token,
                          @Query("ClassId") String classId,
                          Callback<ResponseData<List<Assistant>>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/SendInviterSR")
    void sendInviterSR(@Header("token") String token,
                       @Field("ClassId") String classId,
                       @Field("Inviters") String Inviters,
                       Callback<ResponseData> callback);

    @GET("/HerbrClass/GetClass")
    void getClassList(@Header("token") String token,
                      Callback<ResponseData<List<ClassInfo>>> callback);

    @GET("/Index/GetSPHonor")
    void getSPHonor(@Header("token") String token,
                    Callback<ResponseData<HonorInfo>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/CreateClass")
    void createClass(@Header("token") String token,
                     @Field("ClassName") String className,
                     @Field("StartDate") String startDate,
                     @Field("EndDate") String endDate,
                     @Field("ManagerId") String managerId,
                     Callback<ResponseData<ClassId>> callback);

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

    @GET("/Index/GetMatchInfo")
    void getMatchInfo(@Header("token") String token,
                      @Query("dtime") String dtime,
                      @Query("group") String group,
                      Callback<ResponseData<List<MarchInfo>>> callback);
}
