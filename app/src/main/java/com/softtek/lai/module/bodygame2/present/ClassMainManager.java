package com.softtek.lai.module.bodygame2.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class ClassMainManager {
    private BodyGameService service;
    private ClassMainCallback cb;

    public ClassMainManager(ClassMainCallback cb) {
        this.cb = cb;
        service = ZillaApi.NormalRestAdapter.create(BodyGameService.class);
    }

    public void doClassMainIndex(String accountid)
    {
        String token = UserInfoModel.getInstance().getToken();
        service.doClassMainIndex(token, accountid, new RequestCallback<ResponseData<ClassMainModel>>() {
            @Override
            public void success(ResponseData<ClassMainModel> classMainModelResponseData, Response response) {
                int status=classMainModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("班级主页列表" + classMainModelResponseData.getData());
                        if(cb!=null)
                        {
                            cb.getClassMain(classMainModelResponseData.getData());
                        }
                    default:
                        if (cb!=null)
                        {
                            cb.getClassMain(null);
                        }
                }
            }
        });

    }


    public interface ClassMainCallback {
        void getClassMain(ClassMainModel classMainModel);
    }
}
