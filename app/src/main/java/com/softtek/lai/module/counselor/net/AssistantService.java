package com.softtek.lai.module.counselor.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.Assistant;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;

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
public interface AssistantService {
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

}
