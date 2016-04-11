package com.softtek.lai.module.community.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public interface CommunityService {

    //获取推荐健康圈内容
    @GET("/HealthyCircle/HealthRecommend")
    public void getrecommendHealthyContent(RequestCallback<ResponseData<List<HealthyCommunityModel>>> callback);
}
