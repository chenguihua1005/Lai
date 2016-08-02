package com.softtek.lai.module.confirmInfo.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.message.model.PhotosModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by zcy on 2016/4/13.
 */
public interface ConfirmInfoService {

    //获取参赛确认信息
    @GET("/MsgCenter/GetConfirmInfo")
    void doGetConfirmInfo(
            @Header("token") String token,
            @Query("accountid") long accountid, //学员id
            @Query("classid") long classid,         //班级id
            Callback<ResponseData<GetConfirmInfoModel>> callback);

    // 修改参赛数据
    @POST("/MsgCenter/UpConfirmInfo")
    void changeUpConfirmInfo(@Header("token") String token,
                             @Body ConinfoModel coninfoModel,
                             Callback<ResponseData<ConinfoModel>> callback);

    //上传图片
    @POST("/FileUpload/PostFile")
    @Multipart
    void upimg(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotosModel>> callback);

}
