package com.softtek.lai.module.bodygame3.head.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jessica.zhang on 6/21/2017.
 */

public class HonorPresenter extends BasePresenter<HonorPresenter.HonorView> {
    private HeadService service;

    public HonorPresenter(HonorView baseView) {
        super(baseView);
        this.service = ZillaApi.NormalRestAdapter.create(HeadService.class);
    }

    public void getHonorData(Long UID, String classId, String byWhichRatio, String sortTimeType, int whichTime, Boolean isFirst) {
        Log.i("HonorFragment", "接口中....UID= " + UID + " ClassId = " + classId + " ByWhichRatio= " + byWhichRatio + " SortTimeType= " + sortTimeType + " whichTime = " + whichTime + " isFirst = " + isFirst);
        String token = UserInfoModel.getInstance().getToken();
        service.getHonorRoll(classId, token, UID, classId, byWhichRatio, sortTimeType, whichTime,
                new Callback<ResponseData<HonorRankModel>>() {
                    @Override
                    public void success(ResponseData<HonorRankModel> honorRankModelResponseData, Response response) {
                        if (getView() != null) {
                            getView().dialogDissmiss();
                            getView().hidenLoading();
                        }
                        int status = honorRankModelResponseData.getStatus();
                        switch (status) {
                            case 200:
                                HonorRankModel honorRankModel = honorRankModelResponseData.getData();
//                                Log.i("honor", "data = " + new Gson().toJson(honorRankModel));
                                if (getView() != null) {
                                    getView().getHonorModel(honorRankModel);
                                }
                                break;
                            default:
                                if (getView() != null)
                                    getView().getHonorModel(null);
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getView() != null)
                            getView().getHonorModel(null);
                        if (getView() != null) {
                            getView().dialogDissmiss();
                            getView().hidenLoading();
                        }
                        ZillaApi.dealNetError(error);
                    }
                });

    }


    public interface HonorView extends BaseView {
        void getHonorModel(HonorRankModel honorRankModel);

        void hidenLoading();
    }
}
