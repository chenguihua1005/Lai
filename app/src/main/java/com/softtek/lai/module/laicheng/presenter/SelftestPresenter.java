package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.net.BleService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 2017/4/10.
 */

public class SelftestPresenter extends BasePresenter<SelftestPresenter.SelftestView> {

    public SelftestPresenter(SelftestPresenter.SelftestView view){
        super(view);
    }

    public void getLastInfo(int type){
        ZillaApi.NormalRestAdapter.create(BleService.class)
                .getLastData(UserInfoModel.getInstance().getToken(),type, new RequestCallback<ResponseData<LastInfoData>>() {
                    @Override
                    public void success(ResponseData<LastInfoData> data, Response response) {
                        if (getView() != null) {
                            getView().getLastInfoSuccess(data.getData());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if (getView() != null) {
                            getView().getLastInfoFailed();
                        }
                    }
                });
    }

    public interface SelftestView extends BaseView {
        void getLastInfoSuccess(LastInfoData data);
        void getLastInfoFailed();
    }
}
