package com.softtek.lai.module.sport2.presenter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.sport2.model.SportMineModel;
import com.softtek.lai.module.sport2.model.Unread;
import com.softtek.lai.module.sport2.net.SportService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 10/17/2016.
 */
public class SportManager extends BasePresenter<SportManager.SportManagerCallback>{

    private SportService service;

    public SportManager(SportManagerCallback baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public void getMineData(long accountId, String stepAndDate, final boolean isDown){
        if(!isDown){
            if (getView()!=null){
                getView().dialogShow("正在加载");
            }
        }
        service.getSportIndex(UserInfoModel.getInstance().getToken(), accountId, stepAndDate,
                new RequestCallback<ResponseData<SportMineModel>>() {
                    @Override
                    public void success(ResponseData<SportMineModel> data, Response response) {
                        if(getView()!=null){
                            if(!isDown){
                                getView().dialogDissmiss();
                            }else {
                                getView().hidden();
                            }
                            if(data.getStatus()==200){
                                if(getView()!=null){
                                    getView().getResult(data.getData());
                                }
                            }else {
                                Util.toastMsg(data.getMsg());}

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if(getView()!=null){
                            if(!isDown){
                                getView().dialogDissmiss();
                            }else {
                                getView().hidden();
                            }

                        }
                    }
                });
    }

    public void getNewMsgRemind(){
        service.getNewMsgRemind(UserInfoModel.getInstance().getToken(),
                String.valueOf(UserInfoModel.getInstance().getUserId()),
                new RequestCallback<ResponseData<Unread>>() {
                    @Override
                    public void success(ResponseData<Unread> responseData, Response response) {
                        if(responseData.getStatus()==200||responseData.getStatus()==201){
                            if(getView()!=null){
                                getView().setUnReadMsg(responseData.getData().getUnreadCount());
                            }
                        }
                    }
                });
    }

    public interface SportManagerCallback extends BaseView{
        void getResult(SportMineModel result);
        void hidden();
        void setUnReadMsg(String unRead);
    }
}
