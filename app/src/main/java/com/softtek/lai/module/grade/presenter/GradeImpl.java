/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.grade.eventModel.SRInfoEvent;
import com.softtek.lai.module.grade.model.BannerModel;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.model.GradeModel;
import com.softtek.lai.module.grade.model.SRInfoModel;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.module.grade.net.GradeService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class GradeImpl implements IGrade {

    private GradeService service;
    private BannerUpdateCallBack callBack;
    private GradeCalllback cb;
    private String token="";

    public GradeImpl() {
        this(null);
    }

    public GradeImpl(Context callBack) {
        this.callBack = (BannerUpdateCallBack) callBack;
        this.cb= (GradeCalllback) callBack;
        service = ZillaApi.NormalRestAdapter.create(GradeService.class);
        token= UserInfoModel.getInstance().getToken();
    }
    public GradeImpl(BannerUpdateCallBack callBack,String type) {
        this.callBack = callBack;
        service = ZillaApi.NormalRestAdapter.create(GradeService.class);
        token= UserInfoModel.getInstance().getToken();
    }

    @Override
    public void getGradeInfos(final long classId, final ProgressDialog loadingDialog) {
        service.getGradeInfo(token, String.valueOf(classId), new Callback<ResponseData<GradeModel>>() {
            @Override
            public void success(ResponseData<GradeModel> gradeResponseData, Response response) {
                loadingDialog.dismiss();
                Log.i("班级信息"+gradeResponseData.getData());
                int status = gradeResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(gradeResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(gradeResponseData.getMsg());
                        break;
                }
                Log.i(gradeResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void sendDynamic(long classId, String dynamicTitle, final String dyContent, int dyType, long accountId) {
        service.senDynamic(token, classId, dynamicTitle, dyContent, dyType, accountId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                DynamicInfoModel info = new DynamicInfoModel();
                info.setCreateDate(DateUtil.getInstance(DateUtil.yyyy_MM_dd_HH_mm_ss).getCurrentDate());
                info.setDyContent(dyContent);
                info.setDyType("2");
                info.setPhoto(UserInfoModel.getInstance().getUser().getPhoto());
                EventBus.getDefault().post(info);
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getStudentList(String orderType, String classId, final PullToRefreshListView lv) {
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        Log.i("accountid="+userId+";classid="+classId);
        service.getStudentsList(token,Long.parseLong(userId),classId,orderType, new Callback<ResponseData<List<StudentModel>>>() {
            @Override
            public void success(ResponseData<List<StudentModel>> studentResponseData, Response response) {
                lv.onRefreshComplete();
                switch (studentResponseData.getStatus()) {
                    case 200:
                        Log.i(studentResponseData.toString());
                        //EventBus.getDefault().post(new LossWeightEvent(studentResponseData.getData()));
                        break;
                    default:
                        Util.toastMsg(studentResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                lv.onRefreshComplete();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getTutorList(long classId, final PullToRefreshListView lv) {
        service.getTutorList(token, classId, new Callback<ResponseData<List<SRInfoModel>>>() {
            @Override
            public void success(ResponseData<List<SRInfoModel>> responseData, Response response) {
                lv.onRefreshComplete();
                switch (responseData.getStatus()) {
                    case 200:
                        Log.i(responseData.toString());
                        EventBus.getDefault().post(new SRInfoEvent(responseData.getData()));
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                lv.onRefreshComplete();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void updateClassBanner(long classId, String type, final File image) {
        service.updateClassBanner(token,
                classId,
                type,
                new TypedFile("image/png", image),
                new Callback<ResponseData<BannerModel>>() {
                    @Override
                    public void success(ResponseData<BannerModel> responseData, Response response) {
                        if (callBack != null) {
                            callBack.onSuccess(responseData.getData().getPath(), image);
                        }
                        Log.i(responseData.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (callBack != null) {
                            callBack.onFailed();
                        }
                        ZillaApi.dealNetError(error);

                    }
                });
    }

    @Override
    public void removeTutorRole(long classId, long tutorId,Callback<ResponseData> callback) {
        service.removeTutorRole(token, tutorId, classId,0, callback);
    }

    @Override
    public void getClassDynamic(long classId, int pageIndex) {
        service.getClassDynamic(token, classId, pageIndex, new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                if(cb!=null)cb.getDynamicCallback(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                if(cb!=null)cb.getDynamicCallback(null);
                super.failure(error);
            }
        });
    }

    public interface GradeCalllback{
        void getDynamicCallback(List<DynamicInfoModel> dynamicInfoModels);
    }
}
