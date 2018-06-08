package com.softtek.lai.module.sport2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.sport2.model.GradeHonorModel;
import com.softtek.lai.module.sport2.model.ScoreModel;
import com.softtek.lai.module.sport2.model.XunZhangModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public interface GradesService {

    //3.3.2	成绩勋章信息
    @GET("/StepCount/GetGeneralHonor")
    void getGradeHonor(@Header("token") String token,
                       Callback<ResponseData<GradeHonorModel>> callback);



    //2.19.2	勋章详情页
    @GET("/StepCount/GetStepHonor")
    void doGetXunZhang(@Header("token") String token,
                       Callback<ResponseData<XunZhangModel>> callback);

    //StepCount/GetMineScore
    @GET("/StepCount/GetMineScore")
    void getMineScore(@Header("token") String token,
                       @Query("accountid") String accountid,
                       Callback<ResponseData<ScoreModel>> callback);

}
