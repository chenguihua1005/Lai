package com.softtek.lai.module.sport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.model.LogStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.utils.RequestCallback;

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
 * Created by jerry.guan on 4/8/2016.
 */
public interface SportGroupService {

    public static final String TOKEN = "token";

    //是否已加入跑团
    @GET("/HerbSports/IsJoinRunGroup")
    void isJoinRunGroup(@Header(TOKEN) String token,
                        @Query("accountid") String accountid,
                        RequestCallback<ResponseData> callback);
}
