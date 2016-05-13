package com.softtek.lai.module.act.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.sport.model.CityModel;
import com.softtek.lai.module.sport.model.DxqModel;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.model.SportMainModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface ActService {

    public static final String TOKEN = "token";

    //活动列表
    @GET("/Activity/ActivityList")
    void activityList(@Header(TOKEN) String token,
                      @Query("PageIndex") String pageIndex,
                      @Query("Accountid") String accountid,
                      RequestCallback<ResponseData<List<ActivityModel>>> callback);

}
