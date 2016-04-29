package com.softtek.lai.module.confirmInfo.presenter;


import android.content.Context;
import android.content.Intent;

import retrofit.mime.TypedFile;
import zilla.libcore.util.Util;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame.view.CounselorActivity;
import com.softtek.lai.module.confirmInfo.EventModel.ConinfoEvent;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.net.ConfirmInfoService;
import com.softtek.lai.module.confirmInfo.view.CansaiActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.message.model.PhotosModel;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by zcy on 2016/4/13.
 */
public class UpConfirmInfoImpl implements IUpConfirmInfopresenter {

    private ConfirmInfoService confirmInfoService;
    private Context context;

    public UpConfirmInfoImpl(Context context) {
        confirmInfoService = ZillaApi.NormalRestAdapter.create(ConfirmInfoService.class);
        this.context = context;
    }

    //获取参赛确认信息
    @Override
    public void getConfirmInfo(long accountid, long classid) {
        Log.i("confirmInfoService>>>>>>>>>>>>>>>>>>>>>>>>>>" + confirmInfoService);
        confirmInfoService = ZillaApi.NormalRestAdapter.create(ConfirmInfoService.class);
        String token = SharedPreferenceService.getInstance().get("token", "");
        confirmInfoService.doGetConfirmInfo(token, accountid, classid, new Callback<ResponseData<GetConfirmInfoModel>>() {
            @Override
            public void success(ResponseData<GetConfirmInfoModel> getConfirmInfoModelResponseData, Response response) {
                int status = getConfirmInfoModelResponseData.getStatus();
                ((JoinGameDetailActivity) context).dialogDissmiss();
                Log.i("--------获取参赛确认信息--------------getConfirmInfoModelResponseData:" + getConfirmInfoModelResponseData);
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new ConinfoEvent(getConfirmInfoModelResponseData.getData()));
                        System.out.println("getConfirmInfoModelResponseData:" + getConfirmInfoModelResponseData);
                        break;
                    case 100:
                        Util.toastMsg("暂无数据");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((JoinGameDetailActivity) context).dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    //修改参赛数据
    @Override
    public void changeUpConfirmInfo(String token, ConinfoModel coninfoModel) {
        Log.i("ConfirmInfoService>>>>>>>>>>>>>>" + confirmInfoService);
        //String token = SharedPreferenceService.getInstance().get("token", "");
        confirmInfoService.changeUpConfirmInfo(token, coninfoModel, new Callback<ResponseData<ConinfoModel>>() {
            @Override
            public void success(ResponseData<ConinfoModel> coninfoModelResponseData, Response response) {
                int status = coninfoModelResponseData.getStatus();
                ((JoinGameDetailActivity) context).dialogDissmiss();
                switch (status) {
                    case 200:
                        Intent intent = new Intent(context, HomeActviity.class);
                        context.startActivity(intent);
                        ((JoinGameDetailActivity) context).finish();
                        break;
                    case 500:
                        Util.toastMsg(coninfoModelResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((JoinGameDetailActivity) context).dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    //上传图片文件
    @Override
    public void upload(final String upimg) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        confirmInfoService.upimg(token, new TypedFile("image/png", new File(upimg)), new Callback<ResponseData<PhotosModel>>() {
            @Override
            public void success(ResponseData<PhotosModel> upimgResponseData, Response response) {
                ((JoinGameDetailActivity) context).dialogDissmiss();
                System.out.println("upimgResponseData:"+upimgResponseData);
                int status = upimgResponseData.getStatus();
                switch (status) {
                    case 200:
                        PhotosModel photModel = upimgResponseData.getData();
                        EventBus.getDefault().post(photModel);
                        break;
                    case 500:
                        Util.toastMsg(upimgResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((JoinGameDetailActivity) context).dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }


}
