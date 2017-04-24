package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 4/14/2017.
 */

public class FuceCheckPresenter extends BasePresenter<FuceCheckPresenter.FuceCheckView> {

    FuceSevice fuceSevice;

    public FuceCheckPresenter(FuceCheckView baseView) {
        super(baseView);
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
    }


    public void getFuceCheckData(String acmId) {
        fuceSevice.doGetMeasuredDetails(UserInfoModel.getInstance().getToken(), acmId, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
            @Override
            public void success(ResponseData<MeasuredDetailsModel> measuredDetailsModelResponseData, Response response) {
                int status = measuredDetailsModelResponseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                if (200 == status) {
                    if (getView() != null) {
                        getView().getFuceCheckData(measuredDetailsModelResponseData.getData());
                    }
                } else {
                    Util.toastMsg(measuredDetailsModelResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
            }
        });
    }

    private void doPostInitData(FcAuditPostModel model) {
//        fuceSevice.doReviewMeasuredRecord(UserInfoModel.getInstance().getToken(), fcAuditPostModel, new RequestCallback<ResponseData>() {
//            @Override
//            public void success(ResponseData responseData, Response response) {
//                try {
//                    int status = responseData.getStatus();
//                    switch (status) {
//                        case 200:
//                            Intent intent = new Intent();
//                            intent.putExtra("ACMID", ACMID);
//                            setResult(RESULT_OK, intent);
//                            finish();
//                            break;
//                        default:
//                            Util.toastMsg(responseData.getMsg());
//                            break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                super.failure(error);
//                progressDialog.dismiss();
//            }
//        });

    }


    public interface FuceCheckView extends BaseView {
        void getFuceCheckData(MeasuredDetailsModel model);
    }
}
