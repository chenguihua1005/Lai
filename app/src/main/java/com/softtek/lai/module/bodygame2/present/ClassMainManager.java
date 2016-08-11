package com.softtek.lai.module.bodygame2.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.group.model.DxqModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ClassMainManager {
    private BodyGameService service;
    private ClassMainCallback cb;

    public ClassMainManager(ClassMainCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    public void doClMemberChange(String asscountid,String classid, String type) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClMemberChange(token,asscountid, classid, type, new RequestCallback<ResponseData<MemberChangeModel>>() {
            @Override
            public void success(ResponseData<MemberChangeModel> memberChangeModelResponseData, Response response) {
                int status = memberChangeModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("获取学员列,切换维度" + memberChangeModelResponseData.getData());
                        if (cb != null) {
                            cb.getStudentList(memberChangeModelResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getStudentList(null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getStudentList(null);
                }
                ZillaApi.dealNetError(error);
            }
        });

    }

    public void doClassMainIndex(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClassMainIndex(token, accountid, new RequestCallback<ResponseData<ClassMainModel>>() {
            @Override
            public void success(ResponseData<ClassMainModel> classMainModelResponseData, Response response) {
                int status = classMainModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("班级主页列表" + classMainModelResponseData.getData());
                        if (cb != null) {
                            cb.getClassMain("200",classMainModelResponseData.getData());
                        }
                        break;
                    case 100:
                        if (cb != null) {
                            cb.getClassMain("100",null);
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getClassMain("0",null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getClassMain("0",null);
                }
                ZillaApi.dealNetError(error);
            }
        });

    }

    public void doClassChangeById(String classid, String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClassChangeById(token, classid, accountid, new RequestCallback<ResponseData<ClassChangeModel>>() {
            @Override
            public void success(ResponseData<ClassChangeModel> classMainModelResponseData, Response response) {
                int status = classMainModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("班级主页列表" + classMainModelResponseData.getData());
                        if (cb != null) {
                            cb.getClassChange(classMainModelResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getClassChange(null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getClassChange(null);
                }
                ZillaApi.dealNetError(error);
            }
        });

    }

    public void doGetClasslist(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doGetClasslist(token, accountid, new RequestCallback<ResponseData<ClassModel>>() {
            @Override
            public void success(ResponseData<ClassModel> classMainModelResponseData, Response response) {
                int status = classMainModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("班级主页列表" + classMainModelResponseData.getData());
                        if (cb != null) {
                            cb.getClasslist(classMainModelResponseData.getData());
                        }
                        break;
                    default:
                        if (cb != null) {
                            cb.getClasslist(null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getClasslist(null);
                }
                ZillaApi.dealNetError(error);
            }
        });

    }


    public interface ClassMainCallback {
        void getClassMain(String type,ClassMainModel classMainModel);

        void getStudentList(MemberChangeModel memberChangeModel);

        void getClassChange(ClassChangeModel classChangeModel);

        void getClasslist(ClassModel classModel);
    }
}
