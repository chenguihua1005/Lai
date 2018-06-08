package com.softtek.lai.module.community.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.HealthyRecommendModel;
import com.softtek.lai.module.community.model.PersonalRecommendModel;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.RequestCallback;

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


    public void getHealthyMine(long selectorUserId,int pageIndex) {
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model==null){
            return;
        }
        int accountId=Integer.parseInt(model.getUserid());
        service.getHealthyMine(accountId,selectorUserId,pageIndex,10, new RequestCallback<ResponseData<PersonalRecommendModel>>() {
            @Override
            public void success(ResponseData<PersonalRecommendModel> listResponseData, Response response) {

                if(cb!=null)cb.getMineDynamic(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                if(cb!=null)cb.getMineDynamic(null);
                super.failure(error);
            }
        });
    }
    public void getHealthyFocus(int pageIndex) {
        String token= UserInfoModel.getInstance().getToken();
        service.healthyFocus(token,UserInfoModel.getInstance().getUserId(),pageIndex,10, new RequestCallback<ResponseData<HealthyRecommendModel>>() {
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

    public interface CommunityManagerCallback<T>{
        void getMineDynamic(T model);
    }

}
