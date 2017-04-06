package com.softtek.lai.module.bodygame3.activity.presenter;

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
 * Created by jessica.zhang on 4/5/2017.
 */

public class InitAuditPresenter extends BasePresenter<InitAuditPresenter.InitAuditList> {
    FuceSevice fuceSevice;


    public InitAuditPresenter(InitAuditList baseView) {
        super(baseView);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);

    }

    //获取审核列表数据
    public void doGetData(Long accountid, String classid, final int pageIndex, int pageSize) {
        fuceSevice.dogetInitAuditList(classid, UserInfoModel.getInstance().getToken(), accountid, classid, pageIndex, pageSize, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200: {
                        if (getView() != null) {
                            getView().getInitAuditList(listResponseData.getData());
                        }
                    }
                    break;

                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                super.failure(error);
            }
        });
    }

    public interface InitAuditList extends BaseView {
         void getInitAuditList(List<AuditListModel> list);
         void hidenLoading();
    }
}
