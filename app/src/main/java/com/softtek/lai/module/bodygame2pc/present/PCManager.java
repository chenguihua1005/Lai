package com.softtek.lai.module.bodygame2pc.present;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2pc.model.PCBodyGameInfo;
import com.softtek.lai.module.bodygame2pc.net.BodyGamePcService;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCFragment;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class PCManager {

    private BodyGamePcService service;

    public PCManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGamePcService.class);
    }

    public void getSPHomeInfo(final BodyGamePCFragment fragment){
        service.getPCBodyGameInfo(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<PCBodyGameInfo>>() {
            @Override
            public void success(ResponseData<PCBodyGameInfo> pcBodyGameInfoResponseData, Response response) {
                if(fragment!=null){
                    if(pcBodyGameInfoResponseData.getStatus()==200){
                        fragment.onLoadCompleted(pcBodyGameInfoResponseData.getData());
                    }else{
                        fragment.onLoadCompleted(null);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if(fragment!=null){
                    fragment.onLoadCompleted(null);
                }
                super.failure(error);
            }
        });

    }
}
