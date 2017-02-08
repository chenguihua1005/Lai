package com.softtek.lai.module.community.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.community.model.CommunityModel;
import com.softtek.lai.module.community.model.DoZan;
import com.softtek.lai.module.community.model.HealthyDynamicModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.model.ImageResponse;
import com.softtek.lai.module.community.model.ImageResponse2;
import com.softtek.lai.module.community.model.PersonalRecommendModel;
import com.softtek.lai.module.community.model.TopicInfo;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public interface CommunityService {

    //获取动态列表
    @GET("/v1/HealthyCircle/GetDynamicList")
    void getDynamicList(@Query("Loginaccid")long accountId,
                        @Query("PageIndex")int pageIndex,
                        @Query("PageSize")int pageSize,
                        RequestCallback<ResponseData<HealthyRecommendModel>> callback);
    //获取热门话题信息
    @GET("/v1/HealthyCircle/GetHotTopicInfo")
    void getHotTopicInfo(RequestCallback<ResponseData<TopicInfo>> callback);

    //获取话题列表
    @GET("/v1/HealthyCircle/GetTopicList")
    void getTopicList(RequestCallback<ResponseData<List<TopicInfo>>> callback);

    //获取健康圈关注内容
    @GET("/HealthyCircle/NewHealthMine")
    void getHealthyMine(@Header("token")String token,
                        @Query("loginaccid")long loginaccid,
                        @Query("accountid")long accountId,
                        @Query("pageIndex")int pageIndex,
                        RequestCallback<ResponseData<PersonalRecommendModel>> callback);
    //请求路径:Api/V1/HealthyCircle/CreatePhotoWall
    //保存我健康圈我的动态
//    @POST("/HealthyCircle/GetReleaseDynamic")
    @POST("/V1/HealthyCircle/CreatePhotoWall")
    void saveDynamic(@Header("token")String token,
                     @Body CommunityModel model,
                     RequestCallback<ResponseData> callback);

    //健康圈动态详情
    @GET("/HealthyCircle/GetHealthDetail")
    void getHealthyDynamciDetail(@Query("accountid")long accountId,
                                 @Query("HealthId")String healthyId,
                                 RequestCallback<ResponseData<HealthyDynamicModel>> callback);

    //健康圈点赞
    @POST("/HealthyCircle/InsertThumbUp")
    void clickLike(@Header("token")String token,
                   @Body DoZan zan,
                   RequestCallback<ResponseData> callback);
    //上传照片接口
    @Multipart
    @POST("/CompetitionLog/PostMultiImgs")
    void uploadMutilpartImage(@Header("token")String token,
                              @Part("photo")TypedFile file,
                              RequestCallback<ResponseData<ImageResponse>> callback);
    //上传单图接口
    @Multipart
    @POST("/V1/FileUpload/PostFile")
    void uploadSingleImage(@Header("token")String token,
                              @Part("photo")TypedFile file,
                              RequestCallback<ResponseData<ImageResponse2>> callback);

    //上传多图新接口
    @Multipart
    @POST("/V1/FileUpload/PostFiles")
    void uploadImage(@Header("token")String token,
                     @Part("photo")TypedFile file,
                     RequestCallback<ResponseData<List<ImageResponse2>>> callback);

    //删除动态
    @POST("/V1/HealthyCircle/DeletePhotoWall")
    void deleteHealth(@Header("token")String token,
                      @Query("healthid") String healthId,
                      RequestCallback<ResponseData> callback);

    //关注用户
    @POST("/HealthyCircle/FocusAccount")
    void focusAccount(@Header("token")String token,
                      @Query("accountid")long accountId,
                      @Query("focusaccid")long focusAccount,
                      RequestCallback<ResponseData> callback);

    //取消关注
    @POST("/HealthyCircle/CancleFocusAccount")
    void cancleFocusAccount(@Header("token")String token,
                            @Query("accountid")long accountId,
                            @Query("focusaccid")long focusAccount,
                            RequestCallback<ResponseData> callback);
    //关注列表
    @GET("/v1/HealthyCircle/GetFocusDynamicList")
    void healthyFocus(@Header("token")String token,
                      @Query("Loginaccid")long accountId,
                      @Query("PageIndex")int pageIndex,
                      @Query("PageSize")int pageSize,
                      RequestCallback<ResponseData<HealthyRecommendModel>> callback);
}
