package com.softtek.lai.module.newmemberentry.view.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.newmemberentry.view.model.Phot;

import java.util.List;

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
