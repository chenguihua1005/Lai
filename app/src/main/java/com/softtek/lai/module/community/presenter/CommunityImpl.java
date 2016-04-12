package com.softtek.lai.module.community.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public class CommunityImpl implements ICommunity{

    private CommunityService service;


    public CommunityImpl() {
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    @Override
    public void getRecommendDynamic() {
        service.getrecommendHealthyContent(new RequestCallback<ResponseData<List<HealthyCommunityModel>>>() {
            @Override
            public void success(ResponseData<List<HealthyCommunityModel>> listResponseData, Response response) {
                Log.i("健康圈推荐"+listResponseData.toString());
            }
        });
    }

    @Override
    public void getHealthyMine() {
        String token= UserInfoModel.getInstance().getToken();
        service.getHealthyMine(token, new RequestCallback<ResponseData<List<HealthyCommunityModel>>>() {
            @Override
            public void success(ResponseData<List<HealthyCommunityModel>> listResponseData, Response response) {
                Log.i("健康圈 我的"+listResponseData.toString());
            }
        });
    }
}
