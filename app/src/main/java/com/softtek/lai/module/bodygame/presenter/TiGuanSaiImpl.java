package com.softtek.lai.module.bodygame.presenter;

import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.model.FuceNum;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.bodygame.net.BodyGameService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/17/2016.
 */
public class TiGuanSaiImpl implements ITiGuanSai {

    private BodyGameService service;

    public TiGuanSaiImpl() {
        service = ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    @Override
    public void getTiGuanSai() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doGetTiGuanSaiClickw(token, new Callback<ResponseData<TiGuanSai>>() {
            @Override
            public void success(ResponseData<TiGuanSai> tiGuanSaiResponseData, Response response) {
                int status = tiGuanSaiResponseData.getStatus();
                switch (status) {
                    case 200:
                        System.out.println(tiGuanSaiResponseData);
                        EventBus.getDefault().post(tiGuanSaiResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(tiGuanSaiResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void doGetFuceNum(long id) {
        String token = SharedPreferenceService.getInstance().get("token", "");
     service.doGetFuceNum(token, id, new Callback<ResponseData<FuceNum>>() {
         @Override
         public void success(ResponseData<FuceNum> fuceNumResponseData, Response response) {
             int status = fuceNumResponseData.getStatus();
             switch (status) {
                 case 200:
                     System.out.println(fuceNumResponseData);
                     EventBus.getDefault().post(fuceNumResponseData.getData());
                     break;
                 default:
                     Util.toastMsg(fuceNumResponseData.getMsg());
                     break;
             }
         }

         @Override
         public void failure(RetrofitError error) {
             error.printStackTrace();
             Util.toastMsg(R.string.neterror);
         }
     });

    }
}
