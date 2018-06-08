package com.softtek.lai.module.bodygame3.head.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.HonorGroupRankModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by curry.zhang on 12/12/2016.
 */

public class GroupRankingManager {
    private GroupRankingCallback callback;
    private final HeadService service;


    public GroupRankingManager(GroupRankingManager.GroupRankingCallback callback) {
        this.callback = callback;
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
    }

    public void getWeekHonnorInfo(String classId, String byWhichRatio, String sortTimeType, int whichTime, String GroupId) {
        final String token = UserInfoModel.getInstance().getToken();
        service.doGetHonorGroup(classId,token, classId, byWhichRatio, sortTimeType, whichTime, GroupId,
                new Callback<ResponseData<HonorGroupRankModel>>() {
                    @Override
                    public void success(ResponseData<HonorGroupRankModel> honorGroupRankModelData, Response response) {
                        int status = honorGroupRankModelData.getStatus();
                        switch (status) {
                            case 200:
                                HonorGroupRankModel honorRankModel = honorGroupRankModelData.getData();
                                Log.e("curry", honorRankModel.toString());
                                if (callback != null)
                                    callback.getModel(honorRankModel);
                                break;
                            default:
                                if (callback != null)
                                    callback.getModel(null);
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (callback != null)
                            callback.getModel(null);
                        ZillaApi.dealNetError(error);
                    }
                });


    }

    public interface GroupRankingCallback {
        void getModel(HonorGroupRankModel model);
    }
}
