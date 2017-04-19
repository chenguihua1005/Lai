package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
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
//     service.getPreMeasureDataVs1(classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<FcStDataModel>>() {
//        @Override
//        public void success(ResponseData<FcStDataModel> fcStDataModelResponseData, Response response) {
//            int status = fcStDataModelResponseData.getStatus();
//            try {
//                switch (status) {
//                    case 200:
//                        fcStDataModel = fcStDataModelResponseData.getData();
//                        doSetData();
//                        break;
//                    default:
//                        Util.toastMsg(fcStDataModelResponseData.getMsg());
//                        break;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    });

    public void getStudentBasicalInfo(String classId, long userId, String typeDate, String type) {//classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type
        fuceSevice.getPreMeasureData(classId, UserInfoModel.getInstance().getToken(), userId, classId, typeDate, type, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
            @Override
            public void success(ResponseData<MeasuredDetailsModel> fcStDataModelResponseData, Response response) {
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
        void getStudentBasicalInfo(MeasuredDetailsModel model);

        void hidenLoading();
    }
}
