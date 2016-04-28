package com.softtek.lai.module.tips.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public interface TipService {

    //获取健康咨询
    @GET("/HerbTips/GetTipsList")
    void getTipList(@Query("tipstype")String tipType,
                    @Query("pageIndex")int pageIndex,
                    RequestCallback<ResponseData<AskHealthyResponseModel>> callback);
}
