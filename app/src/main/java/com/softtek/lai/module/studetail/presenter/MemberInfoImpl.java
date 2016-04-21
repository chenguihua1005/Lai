/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.eventModel.LineChartEvent;
import com.softtek.lai.module.studetail.model.LogList;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;
import com.softtek.lai.module.studetail.net.MemberInfoService;
import com.softtek.lai.utils.ACache;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class MemberInfoImpl implements IMemberInfopresenter {

    private static final String LOG_CACHE_DIR="logCache";
    private static final String LOG_CACHE_KEY="logKey";

    private MemberInfoService service;
    private UserInfoModel infoModel;
    private Context context;
    private ACache aCache;
    private MemberInfoImplCallback cb;

    public MemberInfoImpl(Context context,MemberInfoImplCallback cb) {
        service = ZillaApi.NormalRestAdapter.create(MemberInfoService.class);
        this.context = context;
        this.cb= cb;
        infoModel=UserInfoModel.getInstance();
        aCache=ACache.get(context,LOG_CACHE_DIR);
    }

    @Override
    public void getMemberinfo(String classId, String userId, final ProgressDialog progressDialog) {
        String token = infoModel.getToken();
        service.getmemberInfo(token, userId, classId, new Callback<ResponseData<MemberModel>>() {
            @Override
            public void success(ResponseData<MemberModel> memberResponseData, Response response) {
                progressDialog.dismiss();
                int status = memberResponseData.getStatus();
                if (status == 200) {
                    EventBus.getDefault().post(memberResponseData.getData());
                } else {
                    Util.toastMsg(memberResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getLossWeightChatData(String userId,String classId) {
        String token=infoModel.getToken();
        service.getLineChartData(token, userId, classId, new Callback<ResponseData<List<StudentLinChartInfoModel>>>() {
            @Override
            public void success(ResponseData<List<StudentLinChartInfoModel>> listResponseData, Response response) {
                if(listResponseData.getStatus()==200){
                    EventBus.getDefault().post(new LineChartEvent(listResponseData.getData()));
                }else{
                    Util.toastMsg(listResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                ZillaApi.dealNetError(error);
            }
        });

    }

    @Override
    public void getLossWeigthLogList(long accountId,int pageIndex) {

        service.getCompetitionLog(infoModel.getToken(),
                accountId,
                pageIndex,
                new Callback<ResponseData<LogList>>() {
                    @Override
                    public void success(ResponseData<LogList> listResponseData, Response response) {
                        if(cb!=null){
                            cb.getLogList(listResponseData.getData());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(cb!=null){
                            cb.getLogList(null);
                        }
                        ZillaApi.dealNetError(error);
                    }
                });
    }

    @Override
    public void doZan(long accountId, long logId,Callback<ResponseData<Zan>> callback) {
        service.clickLike(infoModel.getToken(), accountId, logId, callback);
    }

    public interface MemberInfoImplCallback{
        void getLogList(LogList logs);
    }
}
