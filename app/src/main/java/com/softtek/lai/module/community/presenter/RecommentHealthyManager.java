package com.softtek.lai.module.community.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/15/2016.
 */
public class RecommentHealthyManager {

    private CommunityService service;
    private RecommentHealthyManagerCallback cb;

    public RecommentHealthyManager(RecommentHealthyManagerCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    public void getRecommendDynamic(long accountId,int pageIndex) {
        service.getDynamicList(accountId,pageIndex,10,new RequestCallback<ResponseData<HealthyRecommendModel>>() {
            @Override
            public void success(ResponseData<HealthyRecommendModel> listResponseData, Response response) {
                if(cb!=null){
                    cb.getRecommendDynamic(listResponseData.getData());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if(cb!=null){
                    cb.getRecommendDynamic(null);
                }
            }
        });
    }

    public interface RecommentHealthyManagerCallback{
        void getRecommendDynamic(HealthyRecommendModel model);
    }
}
