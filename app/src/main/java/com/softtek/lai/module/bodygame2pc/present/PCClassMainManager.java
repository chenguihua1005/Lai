package com.softtek.lai.module.bodygame2pc.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2pc.model.PCClassMainModel;
import com.softtek.lai.module.bodygame2pc.net.BodyGamePcService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class PCClassMainManager {
    private BodyGamePcService service;
    private ClassMainCallback cb;

    public PCClassMainManager(ClassMainCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(BodyGamePcService.class);
    }

    public void doClassMainIndex(String accountid) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClassMainIndex(token, accountid, new RequestCallback<ResponseData<PCClassMainModel>>() {
            @Override
            public void success(ResponseData<PCClassMainModel> classMainModelResponseData, Response response) {
                int status = classMainModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        Log.i("班级主页列表" + classMainModelResponseData.getData());
                        if (cb != null) {
                            cb.getClassMain(classMainModelResponseData.getData());
                        }
                        break;
                    default:
                        Util.toastMsg(classMainModelResponseData.getMsg());
                        if (cb != null) {
                            cb.getClassMain(null);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getClassMain(null);
                }
                ZillaApi.dealNetError(error);
            }
        });

    }
    public void doClMemberChange(String accountid,String classid, String type) {
        String token = UserInfoModel.getInstance().getToken();
        service.doClMemberChange(token,accountid, classid, type, new RequestCallback<ResponseData<MemberChangeModel>>() {
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
                        Util.toastMsg(memberChangeModelResponseData.getMsg());
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
    public interface ClassMainCallback {
        void getClassMain(PCClassMainModel classMainModel);

        void getStudentList(MemberChangeModel memberChangeModel);
    }
}
