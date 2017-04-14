package com.softtek.lai.module.laicheng.presenter;

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
                .getLastData(UserInfoModel.getInstance().getToken(),type, new RequestCallback<LastInfoData>() {
                    @Override
                    public void success(LastInfoData data, Response response) {
                        getView().getLastInfoSuccess(data);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        getView().getLastInfoFailed();
                    }
                });
    }

    public interface SelftestView extends BaseView {
        void getLastInfoSuccess(LastInfoData data);
        void getLastInfoFailed();
    }
}
