package com.softtek.lai.module.retest.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.StudentEvent;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.model.Student;
import com.softtek.lai.module.retest.net.RestService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
                       Log.i("全部班级加载成功");
                   }
                   break;
                   case 201:{
                       Log.i("未分配班级");
                   }
                   break;
               }
           }

           @Override
           public void failure(RetrofitError error) {
               error.printStackTrace();
               Util.toastMsg(R.string.neterror);
           }
       });

    }

    @Override
    public void doGetqueryResult(String str) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetqueryResult(token, str, new Callback<ResponseData<List<Student>>>() {

            @Override
            public void success(ResponseData<List<Student>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(new StudentEvent(listResponseData.getData()));
                        Log.i("查询成功");
                        break;
                    case 201:
                        Log.i("未查询到结果");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void doGetAudit(long accountId, long classId, String typeDate) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetAudit(token, accountId, classId, typeDate, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("保存成功");
                        break;
                    case 500:
                        Util.toastMsg("数据保存异常");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");

            }
        });
    }

}
