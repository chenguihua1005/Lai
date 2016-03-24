package com.softtek.lai.module.newmemberentry.view.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public interface NewstudentsService {
    //新成员录入

    @POST("/HerbrClass/CreatDynamic")
    void memberentry(@Header("token") String token,
                     @Body Newstudents newstudents,
                     Callback<ResponseData<Newstudents>> callback);


    //上传图片
//    @POST("/FileUpload/PostImgFile")
//    void upimg(@Body upload upload,
//                  Callback<ResponseData<upload>> callback);

}
