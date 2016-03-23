package com.softtek.lai.module.newmemberentry.view.presenter;


import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.newmemberentry.view.EntryActivity;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.newmemberentry.view.net.NewstudentsService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class NewStudentInputImpl implements INewStudentpresenter{

    private NewstudentsService service;
    private Context context;

    public NewStudentInputImpl(EntryActivity entryActivity){
        service=ZillaApi.NormalRestAdapter.create(NewstudentsService.class);
        context=entryActivity;
    }


    @Override
    public void input(Newstudents newstudents) {
        Log.i("NewstudentsService>>>>>>>>>>>>>>"+service);
        String token= SharedPreferenceService.getInstance().get("token","");
        service.memberentry(token,newstudents, new Callback<ResponseData<Newstudents>>() {
            @Override
            public void success(ResponseData<Newstudents> newstudentsResponseData, Response response) {
                int status=newstudentsResponseData.getStatus();
                switch (status) {
                    case 200:
//                        Intent intent=new Intent(context,HomeActviity.class);
//                        context.startActivity(intent);
                        Util.toastMsg("新学员录入成功");
                        break;
                    case 100:
                        Util.toastMsg("新学员录入失败");
                        break;
                    case 500:
                        Util.toastMsg("图片录入失败");
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
