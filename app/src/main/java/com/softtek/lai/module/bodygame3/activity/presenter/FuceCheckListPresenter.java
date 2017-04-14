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
 * Created by jessica.zhang on 4/13/2017.
 */

public class FuceCheckListPresenter extends BasePresenter<FuceCheckListPresenter.FuceCheckListView> {

    FuceSevice fuceSevice;

    public FuceCheckListPresenter(FuceCheckListView baseView) {
        super(baseView);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
    }


    public void getMeasureReviewedList(String classid, String typeDate, int pageIndex, int pageSize) {
        fuceSevice.dogetAuditList(classid, UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classid, typeDate, pageIndex, 100, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                if (listResponseData.getStatus() == 200) {
                    if (getView() != null) {
                        getView().getMeasureReviewedList(listResponseData.getData());
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


    public interface FuceCheckListView extends BaseView {//List<AuditListModel>

        void getMeasureReviewedList(List<AuditListModel> list);

        void hidenLoading();
    }
}
