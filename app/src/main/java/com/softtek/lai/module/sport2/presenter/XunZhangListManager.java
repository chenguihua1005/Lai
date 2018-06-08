package com.softtek.lai.module.sport2.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport2.model.XunZhangModel;
import com.softtek.lai.module.sport2.net.GradesService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class XunZhangListManager {
    private GradesService service;
    private XunZhangListCallback cb;

    public XunZhangListManager(XunZhangListCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(GradesService.class);
    }

    public void doGetXunZhang() {
        String token= UserInfoModel.getInstance().getToken();
        service.doGetXunZhang(token, new Callback<ResponseData<XunZhangModel>>() {
            @Override
            public void success(ResponseData<XunZhangModel> xunZhangModelResponseData, Response response) {
                int status=xunZhangModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("活动列表"+xunZhangModelResponseData.getData());
                        cb.getXunZhangList(xunZhangModelResponseData.getData());
                        break;
                    default:
                        cb.getXunZhangList(null);
                        Log.i("活动列表"+xunZhangModelResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getXunZhangList(null);
                ZillaApi.dealNetError(error);
            }
        });


    }

    public interface XunZhangListCallback{
        void getXunZhangList(XunZhangModel xunZhangModel);
    }
}
