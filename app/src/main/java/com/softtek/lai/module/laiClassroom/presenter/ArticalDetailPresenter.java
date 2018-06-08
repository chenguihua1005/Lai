package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.net.CollectService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by shelly.xu on 3/14/2017.
 */

public class ArticalDetailPresenter extends BasePresenter<ArticalDetailPresenter.getAddHotAndHistory> {

    CollectService service;

    public ArticalDetailPresenter(getAddHotAndHistory baseView) {
        super(baseView);
    }

    public void UpdateAddHot(String token, long userId, String articalId) {
        service = ZillaApi.NormalRestAdapter.create(CollectService.class);
        service.getAddHotAndHistory(token, userId, articalId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData Data, Response response) {
                int status = Data.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().getData();
                    }
                }else{
                    Util.toastMsg(Data.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public interface getAddHotAndHistory extends BaseView {
        void getData();
    }
}
