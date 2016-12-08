/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.SPModel;
import com.softtek.lai.module.message2.model.PhotosModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public interface JingduService {

    //上传图片
    @POST("/FileUpload/PostFile")
    @Multipart
    void upimg(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotosModel>> callback);

}
