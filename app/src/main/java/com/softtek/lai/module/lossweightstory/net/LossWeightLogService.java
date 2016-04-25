package com.softtek.lai.module.lossweightstory.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.lossweightstory.model.LogList;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.model.LogStoryModel;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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
public interface LossWeightLogService {

    public static final String TOKEN="token";

    //PC（学员版）获取减重故事列表
    @GET("/CompetitionLog/GetCompetitionLogList")
    void getCompetitionLogList(@Header(TOKEN)String token,
                               @Query("accountId")long accountId,
                               @Query("pageIndex")int pageIndex,
                               Callback<ResponseData<LogList>> callback);

    //点赞
    @POST("/CompetitionLog/ClickLike")
    void clickLike(@Header(TOKEN)String token,
                   @Query("accountId") long accountId,
                   @Query("logId") long logId,
                   RequestCallback<ResponseData<Zan>> callback);

    //发布日志
    @POST("/CompetitionLog/SaveCompetitionLog")
    void sendLog(@Header(TOKEN)String token,
                 @Body LogStoryModel story,
                 RequestCallback<ResponseData> callback);

    @GET("/WeightLossLogDetails/WeightLossLogDetails")
    void getLogById(@Header("token")String token,
                    @Query("LossLogId")long logId,
                    RequestCallback<ResponseData<LogStoryDetailModel>> callback);
    //上传照片接口
    @Multipart
    @POST("/CompetitionLog/PostMultiImgs")
    void uploadMutilpartImage(@Header("token")String token,
                              @Part("photo")TypedFile file);
}
