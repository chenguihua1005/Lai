package com.softtek.lai.module.bodygame2sr.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2sr.model.SRBodyGameInfo;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by jerry.guan on 7/15/2016.
 */
public interface BodyGameSRService {

    //获取SR首页信息
    @GET("/HerbNewUser/GetSRIndexInformation")
    void getSRIndexInformation(@Header("token")String token,
                               RequestCallback<ResponseData<SRBodyGameInfo>> callback);
}
