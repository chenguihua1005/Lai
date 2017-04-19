package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 4/19/2017.
 */

public class UnInputPresenter extends BasePresenter<UnInputPresenter.UnInputView> {
    FuceSevice fuceSevice;

    public UnInputPresenter(UnInputView baseView) {
        super(baseView);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
    }

    public void getStudentBasicalInfo(String classId, long userId, String typeDate, String type) {
        fuceSevice.doGetPreMeasureData(classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
            @Override
            public void success(ResponseData<FcStDataModel> fcStDataModelResponseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }

                int status = fcStDataModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().getStudentBasicalInfo(fcStDataModelResponseData.getData());
                        }
                        break;
                    default:
                        Util.toastMsg(fcStDataModelResponseData.getMsg());
                        break;
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

    public interface UnInputView extends BaseView {
        void getStudentBasicalInfo(FcStDataModel model);

        void hidenLoading();
    }
}
