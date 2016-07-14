package com.softtek.lai.module.bodygame2.present;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2.view.BodyGameSPFragment;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class PersonDateManager {

    private BodyGameService service;

    public PersonDateManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    public void doGetClmemberDetial(final PersonalDataActivity personalDataActivity,int roletype,String accountid, String classid)
    {
        service.doGetClmemberDetial(UserInfoModel.getInstance().getToken(),roletype, accountid, classid, new RequestCallback<ResponseData<memberDetialModel>>() {
            @Override
            public void success(ResponseData<memberDetialModel> memberDetialModelResponseData, Response response) {
                if (personalDataActivity!=null)
                {
                    if (memberDetialModelResponseData.getStatus()==200)
                    {
                        personalDataActivity.onloadCompleted(memberDetialModelResponseData.getData());
                    }
                    else
                    {
                        personalDataActivity.onloadCompleted(null);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if(personalDataActivity!=null){
                    personalDataActivity.onloadCompleted(null);
                }
                super.failure(error);
            }
        });
    }

}
