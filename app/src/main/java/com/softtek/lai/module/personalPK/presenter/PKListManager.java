package com.softtek.lai.module.personalPK.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.net.PKService;
import com.softtek.lai.module.personalPK.view.PKListActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PKListManager {

    private PKService service;
    private String token;

    public PKListManager() {
        this.service = ZillaApi.NormalRestAdapter.create(PKService.class);
        token= UserInfoModel.getInstance().getToken();
    }

    public void getPKList(final PKListActivity activity, int pageIndex){
        service.getPKList(pageIndex, new RequestCallback<ResponseData<List<PKListModel>>>() {
            @Override
            public void success(ResponseData<List<PKListModel>> listResponseData, Response response) {
                activity.getModels(listResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                activity.getModels(null);
                super.failure(error);

            }
        });
    }

    public void getPKListByPersonal(final PKListActivity activity, int pageIndex){
        service.getPKListForPersonal(pageIndex, Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                new RequestCallback<ResponseData<List<PKListModel>>>() {
                    @Override
                    public void success(ResponseData<List<PKListModel>> listResponseData, Response response) {
                        activity.getModels(listResponseData);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        activity.getModels(null);
                        super.failure(error);

                    }
                });
    }
}
