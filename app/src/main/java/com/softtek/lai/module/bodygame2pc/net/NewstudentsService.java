/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2pc.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2pc.model.PhotModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public interface NewstudentsService {


    //上传图片
    @POST("/FileUpload/PostFile")
    @Multipart
    void upimg(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotModel>> callback);
}
