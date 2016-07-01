package com.softtek.lai.module.pastreview.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.module.pastreview.model.HistoryHonorInfo;
import com.softtek.lai.module.pastreview.net.PCPastReview;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class HistoryHonorListManager {
    private PCPastReview service;
    private HistoryHonorCallback cb;

    public HistoryHonorListManager(HistoryHonorCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(PCPastReview.class);
    }
    public void getHistoryStudentHonor(String accountId,String classid){
        String token = UserInfoModel.getInstance().getToken();
        service.getHistoryStudentHonor(token, accountId, classid, new RequestCallback<ResponseData<List<HistoryHonorInfo>>>() {
            @Override
            public void success(ResponseData<List<HistoryHonorInfo>> listResponseData, Response response) {
                Log.i(listResponseData.toString());
                int status = listResponseData.getStatus();
                if (cb != null) {
                    switch (status) {
                        case 200:
                            cb.getHistoryStudentHonorCallback("true", listResponseData.getData());
                            break;
                        case 100:
//                            List<HistoryHonorInfo> historyHonorInfos=new ArrayList<HistoryHonorInfo>();
//                            HistoryHonorInfo historyHonorInfo=new HistoryHonorInfo();
//                            historyHonorInfo.setCreateDate("2016-06-30-15:36:23");
//                            historyHonorInfo.setHonorName("减重20斤");
//                            historyHonorInfo.setHonorType("0");
//                            historyHonorInfo.setValue("5斤");

                            cb.getHistoryStudentHonorCallback("false", null);
                            break;
                        default:
                            cb.getHistoryStudentHonorCallback("false", null);
                            Util.toastMsg(listResponseData.getMsg());
                            break;
                    }
                }

            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (cb != null) {
                    cb.getHistoryStudentHonorCallback("false", null);
                }
                ZillaApi.dealNetError(error);
            }

        });
    }


    public interface HistoryHonorCallback {
        void getHistoryStudentHonorCallback(String type,List<HistoryHonorInfo> table1);
    }
}
