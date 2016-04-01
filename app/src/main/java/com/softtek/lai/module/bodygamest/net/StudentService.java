package com.softtek.lai.module.bodygamest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public interface StudentService {
    //获取图片列表接口
    @GET("/Index/GetStudentHonor")
    void getStudentHonor(
            @Header("token") String token,
            Callback<ResponseData<List<StudentHonorInfo>>> callback
    );
}
