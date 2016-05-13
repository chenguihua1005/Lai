package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ActionListManager {
    private MineService service;
    private ActionListCallback cb;

    public ActionListManager(ActionListCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void GetActiveMsg(String accountid) {
        String token= UserInfoModel.getInstance().getToken();
        service.GetActiveMsg(token, accountid, new Callback<ResponseData<List<ActionModel>>>() {
            @Override
            public void success(ResponseData<List<ActionModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("活动列表"+listResponseData.getData());
                        cb.getActionList(listResponseData.getData());
                        break;
                    default:
                        Log.i("活动列表"+listResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getActionList(null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface ActionListCallback{
        void getActionList(List<ActionModel> actionModelList);
    }
}
