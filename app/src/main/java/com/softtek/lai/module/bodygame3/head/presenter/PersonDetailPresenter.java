package com.softtek.lai.module.bodygame3.head.presenter;

import android.text.TextUtils;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 3/30/2017.
 */

public class PersonDetailPresenter extends BasePresenter<PersonDetailPresenter.PersonDetail> {
    private HeadService headService;

    public PersonDetailPresenter(PersonDetail baseView) {
        super(baseView);
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
    }


    public void doGetClassMemberInfoByHx(String HXAccountId, String classid) {
        if (!TextUtils.isEmpty(HXAccountId)) {
            headService.doGetClassMemberInfoByHx(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), HXAccountId, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
                @Override
                public void success(ResponseData<MemberInfoModel> memberInfoModel, Response response) {
                    int status = memberInfoModel.getStatus();
                    if (200 == status) {
                        if (getView() != null) {
                            getView().getMemberInfo(memberInfoModel.getData());
                        }
                    } else {
                        Util.toastMsg(memberInfoModel.getMsg());
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
    }

    public void doGetClassMemberInfo(long AccountId, String classid) {
        headService.doGetClassMemberInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
            @Override
            public void success(ResponseData<MemberInfoModel> memberInfoModel, Response response) {
                int status = memberInfoModel.getStatus();

                if (200 == status) {
                    if (getView() != null) {
                        getView().getMemberInfo(memberInfoModel.getData());
                    }
                } else {
                    Util.toastMsg(memberInfoModel.getMsg());
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

    public void doFocusAccount(long AccountId) {
        headService.doFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().doFocusAccount(1);
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        if (getView() != null) {
                            getView().doFocusAccount(0);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().doFocusAccount(0);
                }
            }
        });
    }

    public void doCancleFocusAccount(long AccountId) {
        headService.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().doCancleFocusAccount(1);
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        if (getView() != null) {
                            getView().doCancleFocusAccount(0);
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().doCancleFocusAccount(0);
                }
            }
        });

    }

    public interface PersonDetail extends BaseView {
        void getMemberInfo(MemberInfoModel memberInfoModel);

        void doFocusAccount(int flag);//flag 1：成功  0：失败

        void doCancleFocusAccount(int flag);////flag 1：成功  0：失败
    }
}
