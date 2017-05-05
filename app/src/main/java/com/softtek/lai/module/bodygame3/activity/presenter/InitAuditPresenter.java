package com.softtek.lai.module.bodygame3.activity.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 4/11/2017.
 */

public class InitAuditPresenter extends BasePresenter<InitAuditPresenter.InitAuditView> {
    FuceSevice fuceSevice;

    public InitAuditPresenter(InitAuditView baseView) {
        super(baseView);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);

    }

    public void getInitAuditList(Long accountid, String classid, int pageIndex, int pageSize) {
        Log.i("InitAuditPresenter","accountid = " + accountid +" classid = " + classid );
        fuceSevice.dogetInitAuditList(classid, UserInfoModel.getInstance().getToken(), accountid, classid, pageIndex, pageSize, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                if (listResponseData.getStatus() == 200) {
                    if (getView() != null) {
                        getView().getInitAuditList(listResponseData.getData());
                    }
                } else {
                    Util.toastMsg(listResponseData.getMsg());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
            }
        });


    }


    public interface InitAuditView extends BaseView {
        void getInitAuditList(List<AuditListModel> list);

        void hidenLoading();
    }
}
