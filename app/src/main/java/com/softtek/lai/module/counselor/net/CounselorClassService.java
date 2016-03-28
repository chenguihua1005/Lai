package com.softtek.lai.module.counselor.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.login.model.Identify;
import com.softtek.lai.module.login.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface CounselorClassService {

    @GET("/HerbrClass/GetClass")
    void getClassList(@Header("token") String token,
                      Callback<ResponseData<List<ClassInfo>>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/CreateClass")
    void createClass(@Header("token") String token,
                @Field("ClassName")String className,
                @Field("StartDate")String startDate,
                @Field("EndDate")String endDate,
                @Field("ManagerId")String managerId,
                Callback<ResponseData<ClassId>> callback);

}
