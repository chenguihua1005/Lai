package com.softtek.lai.module.bodygamest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.model.StudentScripInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jarvis.Liu on 3/31/2016.
 */
public interface StudentService {
    //荣誉榜
    @GET("/Index/GetStudentHonor")
    void getStudentHonor(
            @Header("token") String token,
            Callback<ResponseData<List<StudentHonorInfo>>> callback
    );

    //成绩单
    @GET("/Transcript/GetTranscrip")
    void getTranscrip(
            @Header("token") String token,
            @Query("classid") String classid,
            Callback<ResponseData<List<StudentScripInfo>>> callback
    );
}