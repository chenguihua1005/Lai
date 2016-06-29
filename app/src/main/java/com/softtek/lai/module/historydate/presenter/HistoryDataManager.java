package com.softtek.lai.module.historydate.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.historydate.model.HistoryData;
import com.softtek.lai.module.historydate.model.HistoryDataModel;
import com.softtek.lai.module.historydate.model.HistoryHonorInfo;
import com.softtek.lai.module.historydate.model.ID;
import com.softtek.lai.module.historydate.net.HistoryDataService;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataManager {

    private HistoryDataService service;
    private String token;
    private HistoryDataManagerCallback cb;
    private GetHistoryStudentHonorCallback getHistoryStudentHonorCallback;

    public HistoryDataManager(HistoryDataManagerCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(HistoryDataService.class);
        token = UserInfoModel.getInstance().getToken();
    }

    public HistoryDataManager(GetHistoryStudentHonorCallback getHistoryStudentHonorCallback) {
        this.getHistoryStudentHonorCallback = getHistoryStudentHonorCallback;
        service = ZillaApi.NormalRestAdapter.create(HistoryDataService.class);
        token = UserInfoModel.getInstance().getToken();
    }

    //获取历史数据
    public void getHistoryDataList(int pageIndex) {
        String userId = UserInfoModel.getInstance().getUser().getUserid();
        service.getHistoryDataList(token, Long.parseLong(userId), pageIndex,
                new RequestCallback<ResponseData<HistoryDataModel>>() {
                    @Override
                    public void success(ResponseData<HistoryDataModel> historyDataModelResponseData, Response response) {
                        Log.i("历史数据" + historyDataModelResponseData);
                        if (historyDataModelResponseData.getStatus() == 200) {
                            cb.historyDataCallback(historyDataModelResponseData.getData());
                        } else if (historyDataModelResponseData.getStatus() == 100) {
                            cb.historyDataCallback(new HistoryDataModel("0", new ArrayList<HistoryData>()));
                        } else {
                            cb.historyDataCallback(null);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        cb.historyDataCallback(null);
                        super.failure(error);

                    }
                });
    }

    //删除历史数据
    public void deleteHistoryData(String ids) {
        service.deleteHistoryData(token, new ID(ids), new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i(responseData.toString());
                if (responseData.getStatus() == 200) {
                    cb.deleteResult(true);
                } else {
                    Util.toastMsg(responseData.getMsg());
                    cb.deleteResult(false);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                cb.deleteResult(false);
            }
        });
    }

    //学员历史荣誉榜
    public void getHistoryStudentHonor(String accountId, String classid) {
        service.getHistoryStudentHonor(token, accountId, classid, new RequestCallback<ResponseData<List<HistoryHonorInfo>>>() {
            @Override
            public void success(ResponseData<List<HistoryHonorInfo>> responseData, Response response) {
                Log.i(responseData.toString());
                int status = responseData.getStatus();
                if (getHistoryStudentHonorCallback != null) {
                    switch (status) {
                        case 200:
                            getHistoryStudentHonorCallback.getHistoryStudentHonorCallback("true", responseData.getData());
                            break;
                        case 100:
                            getHistoryStudentHonorCallback.getHistoryStudentHonorCallback("false", null);
                            break;
                        default:
                            getHistoryStudentHonorCallback.getHistoryStudentHonorCallback("false", null);
                            Util.toastMsg(responseData.getMsg());
                            break;
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getHistoryStudentHonorCallback != null) {
                    getHistoryStudentHonorCallback.getHistoryStudentHonorCallback("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface HistoryDataManagerCallback {
        void historyDataCallback(HistoryDataModel model);

        void deleteResult(boolean result);
    }

    public interface GetHistoryStudentHonorCallback {
        void getHistoryStudentHonorCallback(String type, List<HistoryHonorInfo> list);
    }
}
