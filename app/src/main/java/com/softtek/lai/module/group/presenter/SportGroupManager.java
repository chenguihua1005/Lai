package com.softtek.lai.module.group.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.group.model.CityModel;
import com.softtek.lai.module.group.model.DxqModel;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.model.MineResultModel;
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
 *
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
                int status = stepResponseModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (isJoinRunGroupManagerCallBack != null) {
                            isJoinRunGroupManagerCallBack.isJoinRunGroup(true);
                        }
                        break;
                    case 100:
                        if (isJoinRunGroupManagerCallBack != null) {
                            isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        }
                        break;
                    default:
                        if (isJoinRunGroupManagerCallBack != null) {
                            isJoinRunGroupManagerCallBack.isJoinRunGroup(false);
                        }
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
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getBregionList("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getBregionList("fail", new ArrayList<DxqModel>());
                        }
                        break;
                    default:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getBregionList("fail", new ArrayList<DxqModel>());
                        }
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
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getSregionList("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getSregionList("fail", new ArrayList<DxqModel>());
                        }
                        break;
                    default:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getSregionList("fail", new ArrayList<DxqModel>());
                        }
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
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getCityList("success", listResponseData.getData());
                        }
                        break;

                    case 100:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getCityList("fail", new ArrayList<CityModel>());
                        }
                        break;
                    default:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getCityList("fail", new ArrayList<CityModel>());
                        }
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

    public void getHQRGlist(String bregionId) {
        service.getHQRGlist(token, bregionId, new RequestCallback<ResponseData<List<GroupModel>>>() {
            @Override
            public void success(ResponseData<List<GroupModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                        }
                        break;
                    default:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                        }
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

    public void getRGListByCity(String cityId) {
        service.getRGListByCity(token, cityId, new RequestCallback<ResponseData<List<GroupModel>>>() {
            @Override
            public void success(ResponseData<List<GroupModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                        }
                        break;
                    default:
                        if (getGroupListCallBack != null) {
                            getGroupListCallBack.getRGListByCity("fail", new ArrayList<GroupModel>());
                        }
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
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getRGListCallBack != null) {
                            getRGListCallBack.getRGList("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getRGListCallBack != null) {
                            getRGListCallBack.getRGList("fail", new ArrayList<GroupModel>());
                        }
                        break;
                    default:
                        if (getRGListCallBack != null) {
                            getRGListCallBack.getRGList("fail", new ArrayList<GroupModel>());
                        }
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
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getRGByNameOrCodeCallBack != null) {
                            getRGByNameOrCodeCallBack.getRGByNameOrCode("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getRGByNameOrCodeCallBack != null) {
                            getRGByNameOrCodeCallBack.getRGByNameOrCode("fail", new ArrayList<GroupModel>());
                        }
                        break;
                    default:
                        if (getRGByNameOrCodeCallBack != null) {
                            getRGByNameOrCodeCallBack.getRGByNameOrCode("fail", new ArrayList<GroupModel>());
                        }
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

    public void getSportIndex(String str, String todaystep) {
        service.getSportIndex(token, str, todaystep, new RequestCallback<ResponseData<SportMainModel>>() {
            @Override
            public void success(ResponseData<SportMainModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getSportIndex("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getSportIndex("fail", new SportMainModel());
                        }
                        break;
                    default:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getSportIndex("fail", new SportMainModel());
                        }
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

    public void getMineResult(String accountid, String todaystep) {
        service.getMineResult(token, accountid, todaystep, new RequestCallback<ResponseData<MineResultModel>>() {
            @Override
            public void success(ResponseData<MineResultModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getMineResult("success", listResponseData.getData());
                        }
                        break;
                    case 100:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getMineResult("fail", null);
                        }
                        break;
                    default:
                        if (getSportIndexCallBack != null) {
                            getSportIndexCallBack.getMineResult("fail", null);
                        }
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getSportIndexCallBack != null) {
                    getSportIndexCallBack.getMineResult("fail", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void joinRunGroup(String rGId, String rGAccId, final JoinRunGroupCallBack joinRunGroupCallBack) {
        service.joinRunGroup(token, rGId, rGAccId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (joinRunGroupCallBack != null) {
                            joinRunGroupCallBack.joinRunGroup(true);
                        }
                        break;
                    case 100:
                        if (joinRunGroupCallBack != null) {
                            joinRunGroupCallBack.joinRunGroup(false);
                        }
                        break;
                    default:
                        if (joinRunGroupCallBack != null) {
                            joinRunGroupCallBack.joinRunGroup(false);
                        }
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

        void getMineResult(String type, MineResultModel model);

        void getNewMsgRemind(String type);
    }

    public interface GetGroupListCallBack {

        void getBregionList(String type, List<DxqModel> list);

        void getSregionList(String type, List<DxqModel> list);

        void getCityList(String type, List<CityModel> list);

        void getRGListByCity(String type, List<GroupModel> list);

        void getHQRGlist(String type, List<GroupModel> list);
    }
}
