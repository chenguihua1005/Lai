package com.softtek.lai.module.laicheng.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laicheng.model.GetVisitorModel;
import com.softtek.lai.module.laicheng.net.VisitorService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by shelly.xu on 4/14/2017.
 */

public class VisitGetPresenter extends BasePresenter<VisitGetPresenter.VisitGetView> {
    VisitorService service = ZillaApi.NormalRestAdapter.create(VisitorService.class);

    public VisitGetPresenter(VisitGetView baseView) {
        super(baseView);
    }

    public void GetData(String token,int type) {
        service.getData(token, type, new RequestCallback<ResponseData<GetVisitorModel>>() {
            @Override
            public void success(ResponseData<GetVisitorModel> data, Response response) {
                int status=data.getStatus();
                if(200==status){
                    Log.i("获取信息",data.getData().toString());
                    if(getView()!=null){
                     getView().getData(data.getData());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });

    }
    public interface VisitGetView extends BaseView{
        void getData(GetVisitorModel model);
    }
}
