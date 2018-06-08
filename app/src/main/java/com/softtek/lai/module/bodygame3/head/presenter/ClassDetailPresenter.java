package com.softtek.lai.module.bodygame3.head.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.head.model.ClassDetailModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/24/2017.
 */

public class ClassDetailPresenter extends BasePresenter<ClassDetailPresenter.ClassDetailView> {

    HeadService headService;

    public ClassDetailPresenter(ClassDetailView baseView) {
        super(baseView);
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
    }

    public void getClassDate(String classId) {
        if (getView() != null) {
            getView().dialogShow("加载中");
        }
        headService.doGetClassDetial(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(),
                classId, new RequestCallback<ResponseData<ClassDetailModel>>() {
                    @Override
                    public void success(ResponseData<ClassDetailModel> classDetailModelResponseData, Response response) {
                        int status = classDetailModelResponseData.getStatus();
                        switch (status) {
                            case 200:
                                if (getView() != null) {
                                    getView().getClassDate(classDetailModelResponseData.getData());
                                }
                                break;
                            default:
                                Util.toastMsg(classDetailModelResponseData.getMsg());
                                break;
                        }
                        if (getView() != null) {
                            getView().dialogDissmiss();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getView() != null) {
                            getView().dialogDissmiss();
                        }
                        super.failure(error);
                    }
                });
    }

    public void doJoinClass(String classId, int target) {
        if (getView() != null) {
            getView().dialogShow("加入班级中");
        }
        headService.doPostClass(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classId, target, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().doFinish();
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
            }
        });
    }


    public interface ClassDetailView extends BaseView {

        void getClassDate(ClassDetailModel model);

        void doFinish();
    }
}
