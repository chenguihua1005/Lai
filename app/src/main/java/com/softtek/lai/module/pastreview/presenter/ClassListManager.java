package com.softtek.lai.module.pastreview.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.module.pastreview.net.PCPastReview;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ClassListManager {
    private PCPastReview service;
    private ClassListCallback cb;

    public ClassListManager(ClassListCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(PCPastReview.class);
    }

    public void doGetHistoryClassList(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doGetHistoryClassList(token,accountid, new Callback<ResponseData<List<ClassListModel>>>() {
            @Override
            public void success(ResponseData<List<ClassListModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (cb != null) {
                            cb.getClassList(listResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getClassList(null);

                        }
                        Log.i("往期回顾：》》班级列表" + listResponseData.getData());
                        break;

                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getClassList(null);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    public interface ClassListCallback {
        void getClassList(List<ClassListModel> classListModels);
    }
}
