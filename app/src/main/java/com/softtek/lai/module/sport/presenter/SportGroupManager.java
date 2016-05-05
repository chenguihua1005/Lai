package com.softtek.lai.module.sport.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport.model.CityModel;
import com.softtek.lai.module.sport.model.DxqModel;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.net.SportGroupService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class SportGroupManager {

    private String token;
    private SportGroupService service;
    private IsJoinRunGroupManagerCallBack isJoinRunGroupManagerCallBack;
    private GetGroupListCallBack getGroupListCallBack;

    public SportGroupManager(IsJoinRunGroupManagerCallBack isJoinRunGroupManagerCallBack) {
        this.isJoinRunGroupManagerCallBack = isJoinRunGroupManagerCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public SportGroupManager(GetGroupListCallBack getGroupListCallBack) {
        this.getGroupListCallBack = getGroupListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public void isJoinRunGroup(String accoundId) {
        service.isJoinRunGroup(token, accoundId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(true);
                        break;
                    case 100:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        break;
                    default:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (isJoinRunGroupManagerCallBack != null) {
                    isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getBregionList() {
        service.getBregionList(token, new RequestCallback<ResponseData<List<DxqModel>>>() {
            @Override
            public void success(ResponseData<List<DxqModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getGroupListCallBack.getBregionList("success", listResponseData.getData());
                        break;
                    default:
                        getGroupListCallBack.getBregionList("fail", new ArrayList<DxqModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getGroupListCallBack != null) {
                    getGroupListCallBack.getBregionList("fail", new ArrayList<DxqModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getSregionList(String bregionId) {
        service.getSregionList(token, bregionId, new RequestCallback<ResponseData<List<DxqModel>>>() {
            @Override
            public void success(ResponseData<List<DxqModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getGroupListCallBack.getSregionList("success", listResponseData.getData());
                        break;
                    default:
                        getGroupListCallBack.getSregionList("fail", new ArrayList<DxqModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getGroupListCallBack != null) {
                    getGroupListCallBack.getSregionList("fail", new ArrayList<DxqModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getCityList(String sregionId) {
        service.getCityList(token, sregionId, new RequestCallback<ResponseData<List<CityModel>>>() {
            @Override
            public void success(ResponseData<List<CityModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getGroupListCallBack.getCityList("success", listResponseData.getData());
                        break;
                    default:
                        getGroupListCallBack.getCityList("fail", new ArrayList<CityModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getGroupListCallBack != null) {
                    getGroupListCallBack.getCityList("fail", new ArrayList<CityModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getRGListByCity(String cityId) {
        service.getRGListByCity(token, cityId, new RequestCallback<ResponseData<List<GroupModel>>>() {
            @Override
            public void success(ResponseData<List<GroupModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getGroupListCallBack.getRGListByCity("success", listResponseData.getData());
                        break;
                    default:
                        getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getGroupListCallBack != null) {
                    getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface IsJoinRunGroupManagerCallBack {

        void isJoinRunGroup(boolean b);
    }

    public interface GetGroupListCallBack {

        void getBregionList(String type, List<DxqModel> list);

        void getSregionList(String type, List<DxqModel> list);

        void getCityList(String type, List<CityModel> list);

        void getRGListByCity(String type, List<GroupModel> list);
    }
}
