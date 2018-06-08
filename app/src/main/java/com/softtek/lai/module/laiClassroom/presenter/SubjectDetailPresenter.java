package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.net.CollectService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by shelly.xu on 3/14/2017.
 */

public class SubjectDetailPresenter extends BasePresenter<SubjectDetailPresenter.getSubjectdetail> {

    CollectService service;

    public SubjectDetailPresenter(getSubjectdetail baseView) {
        super(baseView);
    }

    public void UpdateSubjectData(String token, long userId, String topicId, int pageindex, int pagesize, final int from) {
        service = ZillaApi.NormalRestAdapter.create(CollectService.class);
        service.getSubjectdetail(token, userId, topicId, pageindex, pagesize, new RequestCallback<ResponseData<CollectModel>>() {
            @Override
            public void success(ResponseData<CollectModel> Data, Response response) {
                int status = Data.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().getSubjectData(Data.getData(),from);
                        getView().dialogDissmiss();
                        getView().dissmiss();
                    }
                } else {
                    Util.toastMsg(Data.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().dialogDissmiss();
                    getView().dissmiss();
                }
                super.failure(error);
            }
        });
    }

    public interface getSubjectdetail extends BaseView {
        void getSubjectData(CollectModel collectModel,int from);

        void dissmiss();
    }
}
