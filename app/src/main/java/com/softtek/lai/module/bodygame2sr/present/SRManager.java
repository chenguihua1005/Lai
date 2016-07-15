package com.softtek.lai.module.bodygame2sr.present;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2.view.BodyGameSPFragment;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRFragment;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class SRManager {

    private BodyGameService service;

    public SRManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    public void getSPHomeInfo(final BodyGameSRFragment fragment){
        service.getSPIndexInformation(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<SPBodyGameInfo>>() {
            @Override
            public void success(ResponseData<SPBodyGameInfo> spBodyGameInfoResponseData, Response response) {
                if(fragment!=null){
                    if(spBodyGameInfoResponseData.getStatus()==200){
                        fragment.onloadCompleted(spBodyGameInfoResponseData.getData());
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
