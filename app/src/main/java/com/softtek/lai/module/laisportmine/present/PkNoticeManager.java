package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class PkNoticeManager {
    private MineService service;
    private PkNoticeCallback cb;

    public PkNoticeManager(PkNoticeCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doGetPKINotice() {
        String token= UserInfoModel.getInstance().getToken();
        service.doGetPKINotice(token, new Callback<ResponseData<List<PkNoticeModel>>>() {
            @Override
            public void success(ResponseData<List<PkNoticeModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("成功"+listResponseData.getData());
                        cb.getPkNotice(listResponseData.getData());
                        break;
                    case 100:
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getPkNotice(null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface PkNoticeCallback{
        void getPkNotice(List<PkNoticeModel> pkNoticeModels);
    }
}
