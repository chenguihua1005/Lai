package com.softtek.lai.module.bodygamest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.DownPhoto;


import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public interface PhotoListService {
    //获取图片列表接口
    @GET("/UploadPhotos/GetUploadPhoto")
    void doGetDownPhoto(
            @Header("token")String token,
            @Query("AccountId")String AccountId,
            Callback<ResponseData<List<DownPhoto>>>callback
    );
}
