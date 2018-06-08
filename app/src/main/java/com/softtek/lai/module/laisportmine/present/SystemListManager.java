package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.SystemNewsModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class SystemListManager {
    private MineService service;
    private SystemListCallback cb;

    public SystemListManager(SystemListCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doGetSysMsg(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doGetSysMsg(token, accountid, new Callback<ResponseData<List<SystemNewsModel>>>() {
            @Override
            public void success(ResponseData<List<SystemNewsModel>> listResponseData, Response response) {
                Log.i("系统消息列表");
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (cb != null) {
                            cb.getSystemList(listResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getSystemList(null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getSystemList(null);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface SystemListCallback {
        void getSystemList(List<SystemNewsModel> systemNewsModelList);
    }
}
