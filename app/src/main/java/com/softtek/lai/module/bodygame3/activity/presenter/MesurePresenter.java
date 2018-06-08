package com.softtek.lai.module.bodygame3.activity.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.activity.model.MeasureListModel;
import com.softtek.lai.module.bodygame3.activity.model.MemberListModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 4/7/2017.
 */

public class MesurePresenter extends BasePresenter<MesurePresenter.MesureView> {

    private FuceSevice sevice;

    public MesurePresenter(MesureView baseView) {
        super(baseView);
        sevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
    }

    public void getMesureList(Long accountid, String classid, final int pageIndex, int pageSize) {
        sevice.getInitMeasureList(classid, UserInfoModel.getInstance().getToken(), accountid, classid, pageIndex, pageSize, new Callback<ResponseData<List<MeasureListModel>>>() {
            @Override
            public void success(ResponseData<List<MeasureListModel>> listResponseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                if (listResponseData.getStatus() == 200) {
                    if (getView() != null) {
                        getView().getMesureList(listResponseData.getData());
                    }
                } else {
                    Util.toastMsg(listResponseData.getMsg());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
            }
        });
    }

    public interface MesureView extends BaseView {
        void getMesureList(List<MeasureListModel> list);

        void hidenLoading();
    }
}
