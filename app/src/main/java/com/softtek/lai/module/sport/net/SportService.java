package com.softtek.lai.module.sport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface SportService {

    public static final String TOKEN = "token";

    //运动列表
    @GET("/StepCount/GetMovementList")
    void getMovementList(@Header(TOKEN) String token,
                         RequestCallback<ResponseData<List<HistorySportModel>>> callback);

}
