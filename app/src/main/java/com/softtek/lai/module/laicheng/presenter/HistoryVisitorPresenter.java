package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.net.VisitorService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class HistoryVisitorPresenter extends BasePresenter<HistoryVisitorPresenter.HistoryVisitorView> {

    VisitorService service;


    public HistoryVisitorPresenter(HistoryVisitorView baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(VisitorService.class);
    }

    public void GetData() {
        service.doGetVisitHistory(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<HistoryModel>>>() {
            @Override
            public void success(ResponseData<List<HistoryModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().getInfo(listResponseData.getData());
                            getView().dissmiss();
                        }
                        break;
                    default:
                        if(getView()!=null){
                            getView().dissmiss();
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().dissmiss();
                }
                super.failure(error);
            }
        });

    }

    public interface HistoryVisitorView extends BaseView {
        void getInfo(List<HistoryModel> historyModels);
        void dissmiss();
    }
}
