package com.softtek.lai.module.bodygame2sr.present;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2sr.model.SRBodyGameInfo;
import com.softtek.lai.module.bodygame2sr.net.BodyGameSRService;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRFragment;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class SRManager {

    private BodyGameSRService service;

    public SRManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGameSRService.class);
    }

    public void getSPHomeInfo(final BodyGameSRFragment fragment){
        service.getSRIndexInformation(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<SRBodyGameInfo>>() {
            @Override
            public void success(ResponseData<SRBodyGameInfo> srBodyGameInfoResponseData, Response response) {
                if(fragment!=null){
                    if(srBodyGameInfoResponseData.getStatus()==200){
                        fragment.onloadCompleted(srBodyGameInfoResponseData.getData());
                    }else{
                        fragment.onloadCompleted(null);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if(fragment!=null){
                    fragment.onloadCompleted(null);
                }
                super.failure(error);
            }
        });
    }
}
