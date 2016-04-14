package com.softtek.lai.module.lossweightstory.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface LossWeightLogService {

    public static final String TOKEN="token";

    //PC（学员版）获取减重故事列表
    @GET("/CompetitionLog/GetCompetitionLogList")
    void getCompetitionLogList(@Header(TOKEN)String token,
                               @Query("accountId")long accountId,
                               Callback<ResponseData<List<LossWeightStoryModel>>> callback);

    //点赞
    @POST("/CompetitionLog/ClickLike")
    void clickLike(@Header(TOKEN)String token,
                   @Field("accountId")long accountId,
                   @Field("logId")long logId,
                   Callback<ResponseData<Zan>> callback);
}
