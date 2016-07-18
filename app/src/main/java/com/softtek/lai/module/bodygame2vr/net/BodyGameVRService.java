package com.softtek.lai.module.bodygame2vr.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame2vr.model.BodyGameVrInfo;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by jerry.guan on 7/16/2016.
 */
public interface BodyGameVRService {

    @GET("/HerbNewUser/GetVisitIndexInformation")
    void getBodyGameVr(RequestCallback<ResponseData<BodyGameVrInfo>> callback);
}
