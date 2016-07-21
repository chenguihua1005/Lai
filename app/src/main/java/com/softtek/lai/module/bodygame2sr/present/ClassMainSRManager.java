package com.softtek.lai.module.bodygame2sr.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2sr.net.BodyGameSRService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ClassMainSRManager {
    private BodyGameSRService service;
    private ClassMainCallback cb;

    public ClassMainSRManager(ClassMainCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(BodyGameSRService.class);
    }

    public void doClMemberChange(String accountid,String classid, String type) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClMemberChange(token,accountid, classid, type, new RequestCallback<ResponseData<MemberChangeModel>>() {
            @Override
            public void success(ResponseData<MemberChangeModel> memberChangeModelResponseData, Response response) {
                int status = memberChangeModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        System.out.println("2222222");
                        Log.i("获取学员列,切换维度" + memberChangeModelResponseData.getData());
                        if (cb != null) {
                            cb.getStudentList(memberChangeModelResponseData.getData());
                        }
                        break;
                    default:
                        System.out.println("111111111");
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



    public interface ClassMainCallback {
        void getClassMain(String type,ClassMainModel classMainModel);

        void getStudentList(MemberChangeModel memberChangeModel);

        void getClassChange(ClassChangeModel classChangeModel);
    }
}
