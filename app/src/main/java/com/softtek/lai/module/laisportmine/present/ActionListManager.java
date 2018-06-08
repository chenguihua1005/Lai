package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import java.util.ArrayList;
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
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void GetActiveMsg(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.GetActiveMsg(token, accountid, new Callback<ResponseData<List<ActionModel>>>() {
            @Override
            public void success(ResponseData<List<ActionModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("活动列表" + listResponseData.getData());
                        if (cb != null) {
                            cb.getActionList(listResponseData.getData());
                        }
                        break;
                    default:
                        List<ActionModel> actionModelLists = new ArrayList<ActionModel>();
                        ActionModel actionModel=new ActionModel();
                        actionModel.setActId("1");
                        actionModel.setActTitle("的方式打开链接");
                        actionModel.setContent("斯蒂芬斯蒂芬斯雕刻技法");
                        actionModelLists.add(actionModel);
                        if (cb != null) {
                            cb.getActionList(actionModelLists);
                        }
                        Log.i("活动列表" + listResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getActionList(null);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface ActionListCallback {
        void getActionList(List<ActionModel> actionModelList);
    }
}
