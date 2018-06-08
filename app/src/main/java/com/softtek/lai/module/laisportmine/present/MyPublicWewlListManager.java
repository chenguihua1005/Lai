package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.message2.model.NoticeModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/11/2016.
 */
public class MyPublicWewlListManager {
    private MineService service;
    private MyPublicWewlListCallback cb;

    public MyPublicWewlListManager(MyPublicWewlListCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doGetDonateMsg(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doGetDonateMsg(token, accountid, new Callback<ResponseData<List<NoticeModel>>>() {

            @Override
            public void success(ResponseData<List<NoticeModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("成功读取" + listResponseData.getData());
                        if (cb != null) {
                            cb.getMyPublicWewlList(listResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getMyPublicWewlList(null);
                        }
                        Log.i("读取失败" + listResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getMyPublicWewlList(null);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface MyPublicWewlListCallback {
        void getMyPublicWewlList(List<NoticeModel> publicWewlfModel);
    }
}
