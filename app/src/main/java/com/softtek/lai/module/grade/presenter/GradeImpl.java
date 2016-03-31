/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.eventModel.LossWeightEvent;
import com.softtek.lai.module.grade.eventModel.SRInfoEvent;
import com.softtek.lai.module.grade.model.*;
import com.softtek.lai.module.grade.net.GradeService;
import org.greenrobot.eventbus.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class GradeImpl implements IGrade {

    private GradeService service;
    private BannerUpdateCallBack callBack;

    public GradeImpl() {
        this(null);
    }

    public GradeImpl(BannerUpdateCallBack callBack) {
        this.callBack = callBack;
        service = ZillaApi.NormalRestAdapter.create(GradeService.class);
    }

    @Override
    public void getGradeInfos(final long classId, final ProgressDialog loadingDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        System.out.println("班级主页信息请求 token?" + token + "------");
        service.getGradeInfo(token, String.valueOf(classId), new Callback<ResponseData<Grade>>() {
            @Override
            public void success(ResponseData<Grade> gradeResponseData, Response response) {
                loadingDialog.dismiss();
                int status = gradeResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(gradeResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(R.string.neterror);
                        break;
                }
                Log.i(gradeResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void sendDynamic(long classId, String dynamicTitle, final String dyContent, int dyType, long accountId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.senDynamic(token, classId, dynamicTitle, dyContent, dyType, accountId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                DynamicInfo info = new DynamicInfo();
                info.setCreateDate(SimpleDateFormat.getDateTimeInstance().format(new Date()));
                info.setDyContent(dyContent);
                EventBus.getDefault().post(info);
                Util.toastMsg(responseData.getMsg());
                Log.i(responseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void getStudentList(String orderType, String classId, final PullToRefreshListView lv) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getStudentsList(token, classId, orderType, new Callback<ResponseData<List<Student>>>() {
            @Override
            public void success(ResponseData<List<Student>> studentResponseData, Response response) {
                lv.onRefreshComplete();
                switch (studentResponseData.getStatus()) {
                    case 200:
                        Log.i(studentResponseData.toString());
                        EventBus.getDefault().post(new LossWeightEvent(studentResponseData.getData()));
                        break;
                    default:
                        Util.toastMsg(studentResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                lv.onRefreshComplete();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void getTutorList(long classId, final PullToRefreshListView lv) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getTutorList(token, classId, new Callback<ResponseData<List<SRInfo>>>() {
            @Override
            public void success(ResponseData<List<SRInfo>> responseData, Response response) {
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
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void updateClassBanner(long classId, String type, final File image) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.updateClassBanner(token,
                classId,
                type,
                new TypedFile("image/png", image),
                new Callback<ResponseData<Banner>>() {
                    @Override
                    public void success(ResponseData<Banner> responseData, Response response) {
                        Util.toastMsg("上传成功");
                        if (callBack != null) {
                            callBack.onSuccess(responseData.getData().getPath(), image);
                        }
                        System.out.println(responseData.toString());

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (callBack != null) {
                            callBack.onFailed();
                        }
                        Util.toastMsg(R.string.neterror);
                        error.printStackTrace();

                    }
                });
    }


}
