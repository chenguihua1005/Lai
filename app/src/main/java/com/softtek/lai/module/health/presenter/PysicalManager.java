package com.softtek.lai.module.health.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.net.PhotoListService;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.net.HealthyService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 4/21/2016.
 */
public class PysicalManager {
    private String token;
    private HealthyService service;
    private Context context;
    private GetHealthCallBack cb;

    public PysicalManager(Context context) {
        this.context = context;
        cb= (GetHealthCallBack) context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(HealthyService.class);
    }


    public void doGetHealthPysicalRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthPysicalRecords(token, Startdate, Enddate, i, new Callback<ResponseData<PysicalModel>>() {
            @Override
            public void success(ResponseData<PysicalModel> pysicalModelResponseData, Response response) {
                cb.getPysicalList(pysicalModelResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getPysicalList(null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }



    public interface GetHealthCallBack{

        void getPysicalList(PysicalModel pysicalModel);
    }
}
