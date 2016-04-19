package com.softtek.lai.module.community.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.HealthyCommunityModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/11/2016.
 *
 */
public class CommunityManager{

    private CommunityService service;
    private CommunityManagerCallback cb;

    public CommunityManager(CommunityManagerCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }


    public void getHealthyMine(int pageIndex) {
        String token= UserInfoModel.getInstance().getToken();
        int accountId=Integer.parseInt(UserInfoModel.getInstance().getUser().getUserid());
        service.getHealthyMine(token,accountId,pageIndex, new RequestCallback<ResponseData<HealthyRecommendModel>>() {
            @Override
            public void success(ResponseData<HealthyRecommendModel> listResponseData, Response response) {
                if(cb!=null)cb.getMineDynamic(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                if(cb!=null)cb.getMineDynamic(null);
                super.failure(error);
            }
        });
    }

    public interface CommunityManagerCallback{
        void getMineDynamic(HealthyRecommendModel model);
    }

}
