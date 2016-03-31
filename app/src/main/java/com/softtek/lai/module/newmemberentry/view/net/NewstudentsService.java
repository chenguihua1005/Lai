/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.newmemberentry.view.model.Phot;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public interface NewstudentsService {
    //新成员录入
    @POST("/HerbNewUser/CreatNewUser")
    void memberentry(@Header("token") String token,
                     @Body Newstudents newstudents,
                     Callback<ResponseData<List<Newstudents>>> callback);


    //上传图片
    @POST("/FileUpload/PostFile")
    @Multipart
    void upimg(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<Phot>> callback);

}
