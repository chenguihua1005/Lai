package com.softtek.lai.module.bodygame2pc.present;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.bodygame2pc.model.StumemberDetialModel;
import com.softtek.lai.module.bodygame2pc.net.BodyGamePcService;
import com.softtek.lai.module.bodygame2pc.view.StuPersonDateActivity;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 7/11/2016.
 */
public class StuPersonDateManager {

    private BodyGamePcService service;

    public StuPersonDateManager() {
        service= ZillaApi.NormalRestAdapter.create(BodyGamePcService.class);
    }

    public void doGetStuClmemberDetial(final StuPersonDateActivity stuPersonDateActivity, int roletype, String accountid, String classid)
    {
        service.doGetStuClmemberDetial(UserInfoModel.getInstance().getToken(), roletype, accountid, classid, new RequestCallback<ResponseData<StumemberDetialModel>>() {
            @Override
            public void success(ResponseData<StumemberDetialModel> stumemberDetialModelResponseData, Response response) {
                if (stuPersonDateActivity!=null)
                {
                    if (stumemberDetialModelResponseData.getStatus()==200)
                    {
                        stuPersonDateActivity.onloadCompleted(stumemberDetialModelResponseData.getData());
                    }
                    else
                    {
                        stuPersonDateActivity.onloadCompleted(null);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if(stuPersonDateActivity!=null){
                    stuPersonDateActivity.onloadCompleted(null);
                }
                super.failure(error);
            }
        });

    }

}
