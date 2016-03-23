package com.softtek.lai.module.retest.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.net.RestService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public class RetestclassImp implements RetestPre{
    private RestService service;

    public  RetestclassImp(){
        service= ZillaApi.NormalRestAdapter.create(RestService.class);
    }

    @Override
    public void doGetRetestclass(long id) {
        Log.i("service>>>>>>>>>>>>>>>>>>>>>>>>>>"+service);
        String token=SharedPreferenceService.getInstance().get("token","0");
       service.doGetRetestclass(token,id, new Callback<ResponseData<List<Banji>>>() {
           @Override
           public void success(ResponseData<List<Banji>> banjiResponseData, retrofit.client.Response response) {
               int status=banjiResponseData.getStatus();
               switch (status){
                   case 200:{
                       EventBus.getDefault().post(new BanJiEvent(banjiResponseData.getData()));
                       System.out.println(banjiResponseData);
                       Util.toastMsg("全部班级加载成功");
                   }
                   break;
                   case 201:{
                       Util.toastMsg("未分配班级");
                   }
                   break;
               }
           }

           @Override
           public void failure(RetrofitError error) {

           }
       });

    }

}
