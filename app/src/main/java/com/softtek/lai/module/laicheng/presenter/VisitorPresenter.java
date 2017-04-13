package com.softtek.lai.module.laicheng.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.net.VisitorService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class VisitorPresenter extends BasePresenter<VisitorPresenter.VisitorView> {

    VisitorService service;


    public VisitorPresenter(VisitorView baseView) {
        super(baseView);
    }

    public void commitData(String token, final VisitorModel visitorModel) {
        service = ZillaApi.NormalRestAdapter.create(VisitorService.class);
        service.commitvisit(token, visitorModel, new RequestCallback<ResponseData<Visitsmodel>>() {
            @Override
            public void success(ResponseData<Visitsmodel> Data, Response response) {
                Log.i("成功",visitorModel.toString());
                int status = Data.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().commit(Data.getData(), visitorModel);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    public interface VisitorView extends BaseView {
        void commit(Visitsmodel visitsmodel, VisitorModel visitorModel);
    }
}
