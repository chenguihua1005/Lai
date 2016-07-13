package com.softtek.lai.module.bodygame2.present;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class ClemeberExitManager {

    private BodyGameService service;

    public ClemeberExitManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    public void doClmemberExit(final PersonalDataActivity personalDataActivity,String accountid, String classid)
    {
        service.doClmemberExit(UserInfoModel.getInstance().getToken(), accountid, classid, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (personalDataActivity!=null)
                {
                    if (responseData.getStatus()==200)
                    {
                        personalDataActivity.onExitompleted("true");
                        Log.i("移出成功",responseData.getMsg());
                    }
                    else
                    {
                        personalDataActivity.onExitompleted("false");
                        Log.i("移除失败",responseData.getMsg());
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if(personalDataActivity!=null){
                    personalDataActivity.onExitompleted("false");
                }
                super.failure(error);
            }
        });

    }

}
