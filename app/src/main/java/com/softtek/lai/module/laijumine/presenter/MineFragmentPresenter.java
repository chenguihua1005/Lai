package com.softtek.lai.module.laijumine.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laijumine.model.MyInfoModel;
import com.softtek.lai.module.laijumine.net.MineSevice;
import com.softtek.lai.utils.RequestCallback;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 6/9/2017.
 */

public class MineFragmentPresenter extends BasePresenter<MineFragmentPresenter.MineFragmentCallback>{

    MineSevice mineSevice;

    public MineFragmentPresenter(MineFragmentCallback baseView) {
        super(baseView);
        mineSevice = ZillaApi.NormalRestAdapter.create(MineSevice.class);
    }

    public void getMyInfo(){
        mineSevice.GetMyInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<MyInfoModel>>() {
            @Override
            public void success(ResponseData<MyInfoModel> myInfoModelResponseData, Response response) {
                int status = myInfoModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        if(getView()!=null){
                            getView().myInfoCallback(myInfoModelResponseData.getData());
                        }
                        break;
                }
            }
        });
    }


    public interface MineFragmentCallback extends BaseView{

        void myInfoCallback(MyInfoModel model);
    }
}
