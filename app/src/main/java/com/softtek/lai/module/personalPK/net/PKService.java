package com.softtek.lai.module.personalPK.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKForm;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.module.personalPK.model.PKObjRequest;
import com.softtek.lai.module.personalPK.model.SavePK;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public interface PKService {

    //获取全国PK列表
    @GET("/Challenged/GetBeChallenged")
    void getPKList(@Header("token") String token,
                   @Query("PageIndex")int pageIndex,
                   RequestCallback<ResponseData<List<PKListModel>>> callback);

    //获取个人PK列表
    @GET("/Challenged/GetBeChallenged")
    void getPKListForPersonal(@Header("token") String token,
                              @Query("PageIndex")int pageIndex,
                              @Query("AccountId")long accountId,
                              RequestCallback<ResponseData<List<PKListModel>>> callback);
    //查询pk详情
    @GET("/Challenged/GetChallengedDetails")
    void getPKDetail(@Header("token") String token,
                     @Query("PKId")long pkId,
                     RequestCallback<ResponseData<PKDetailMold>> callback);

    //检索PK对象
    @GET("/Challenged/GetRGMembers")
    void searchPKObj(@Header("token")String token,
                     @Query("Key")String key,
                     @Query("PageIndex")int pageIndex,
                     RequestCallback<ResponseData<PKObjRequest>> callback);

    //点赞
    @GET("/Challenged/ChallengedPrasie")
    void doZan(@Header("token")String token,
               @Query("PKId")long pkId,
               @Query("Chall")int chall,
               RequestCallback<ResponseData> callback);
    //加载当前用户的跑团成员
    @GET("/Challenged/GetOnwerRGMembers")
    void getCurrentPaoTuanMember(@Header("token")String token,
                                 RequestCallback<ResponseData<List<PKObjModel>>> callback);

    @POST("/Challenged/SavePK")
    void savePK(@Header("token")String token,
                @Body PKForm form,
                RequestCallback<ResponseData<SavePK>> callback);

    //应战或拒绝
    @GET("/Challenged/ChallengedRefused")
    void promiseOrRefuse(@Header("token")String token,
                         @Query("PKId")long pkId,
                         @Query("status")int status,
                         RequestCallback<ResponseData> callback);

    //取消pk赛
    @GET("/Challenged/ChallengedCancel")
    void cancelPK(@Header("token")String token,
                  @Query("PKId")long pkId,
                  RequestCallback<ResponseData> callback);

    //重启pk赛
    @GET("/Challenged/ChallengedReset")
    void resetPK(@Header("token")String token,
                  @Query("PKId")long pkId,
                  RequestCallback<ResponseData> callback);
}
