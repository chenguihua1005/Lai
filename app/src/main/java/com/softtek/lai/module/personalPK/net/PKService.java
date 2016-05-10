package com.softtek.lai.module.personalPK.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.model.PKObjModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
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
                     RequestCallback<ResponseData<List<PKObjModel>>> callback);
}
