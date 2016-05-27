package com.softtek.lai.module.group.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.group.model.CityModel;
import com.softtek.lai.module.group.model.DxqModel;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.model.SportMainModel;
import com.softtek.lai.module.group.model.StepResponseModel;
import com.softtek.lai.module.group.net.SportGroupService;
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
    private GetRGListCallBack getRGListCallBack;
    private GetRGByNameOrCodeCallBack getRGByNameOrCodeCallBack;
    private GetSportIndexCallBack getSportIndexCallBack;

    public SportGroupManager() {
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public SportGroupManager(GetSportIndexCallBack getSportIndexCallBack) {
        this.getSportIndexCallBack = getSportIndexCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public SportGroupManager(GetRGByNameOrCodeCallBack getRGByNameOrCodeCallBack) {
        this.getRGByNameOrCodeCallBack = getRGByNameOrCodeCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

    public SportGroupManager(GetRGListCallBack getRGListCallBack) {
        this.getRGListCallBack = getRGListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportGroupService.class);
    }

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
        service.isJoinRunGroup(token, accoundId, new RequestCallback<ResponseData<StepResponseModel>>() {
            @Override
            public void success(ResponseData<StepResponseModel> stepResponseModelResponseData, Response response) {
                Log.e("jarvis", stepResponseModelResponseData.toString());
                int status = stepResponseModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(true);
                        break;
                    case 100:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        break;
                    default:
                        isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        Util.toastMsg(stepResponseModelResponseData.getMsg());
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
                    case 100:
                        getGroupListCallBack.getBregionList("fail", new ArrayList<DxqModel>());
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
                    case 100:
                        getGroupListCallBack.getSregionList("fail", new ArrayList<DxqModel>());
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

                    case 100:
                        getGroupListCallBack.getCityList("fail", new ArrayList<CityModel>());
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
                    case 100:
                        getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
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

    public void getRGListByPId(String rgId) {
        service.getRGListByPId(token, rgId, new RequestCallback<ResponseData<List<GroupModel>>>() {
            @Override
            public void success(ResponseData<List<GroupModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getRGListCallBack.getRGList("success", listResponseData.getData());
                        break;
                    case 100:
                        getRGListCallBack.getRGList("fail", new ArrayList<GroupModel>());
                        break;
                    default:
                        getRGListCallBack.getRGList("fail", new ArrayList<GroupModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getRGListCallBack != null) {
                    getRGListCallBack.getRGList("fail", new ArrayList<GroupModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getRGByNameOrCode(String str) {
        service.getRGByNameOrCode(token, str, new RequestCallback<ResponseData<List<GroupModel>>>() {
            @Override
            public void success(ResponseData<List<GroupModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getRGByNameOrCodeCallBack.getRGByNameOrCode("success", listResponseData.getData());
                        break;
                    case 100:
                        getRGByNameOrCodeCallBack.getRGByNameOrCode("fail", new ArrayList<GroupModel>());
                        break;
                    default:
                        getRGByNameOrCodeCallBack.getRGByNameOrCode("fail", new ArrayList<GroupModel>());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getRGByNameOrCodeCallBack != null) {
                    getRGByNameOrCodeCallBack.getRGByNameOrCode("fail", new ArrayList<GroupModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getSportIndex(String str) {
        service.getSportIndex(token, str, new RequestCallback<ResponseData<SportMainModel>>() {
            @Override
            public void success(ResponseData<SportMainModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        getSportIndexCallBack.getSportIndex("success", listResponseData.getData());
                        break;
                    case 100:
                        getSportIndexCallBack.getSportIndex("fail", new SportMainModel());
                        break;
                    default:
                        getSportIndexCallBack.getSportIndex("fail", new SportMainModel());
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getSportIndexCallBack != null) {
                    getSportIndexCallBack.getSportIndex("fail", new SportMainModel());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void joinRunGroup(String rGId, String rGAccId, final JoinRunGroupCallBack joinRunGroupCallBack) {
        service.joinRunGroup(token, rGId, rGAccId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        joinRunGroupCallBack.joinRunGroup(true);
                        break;
                    case 100:
                        joinRunGroupCallBack.joinRunGroup(false);
                        break;
                    default:
                        joinRunGroupCallBack.joinRunGroup(false);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (joinRunGroupCallBack != null) {
                    joinRunGroupCallBack.joinRunGroup(false);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface JoinRunGroupCallBack {

        void joinRunGroup(boolean b);
    }

    public interface IsJoinRunGroupManagerCallBack {

        void isJoinRunGroup(boolean b);
    }

    public interface GetRGListCallBack {

        void getRGList(String type, List<GroupModel> list);
    }

    public interface GetRGByNameOrCodeCallBack {

        void getRGByNameOrCode(String type, List<GroupModel> list);
    }

    public interface GetSportIndexCallBack {

        void getSportIndex(String type, SportMainModel sportMainModel);
    }

    public interface GetGroupListCallBack {

        void getBregionList(String type, List<DxqModel> list);

        void getSregionList(String type, List<DxqModel> list);

        void getCityList(String type, List<CityModel> list);

        void getRGListByCity(String type, List<GroupModel> list);
    }
}
