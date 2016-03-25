package com.softtek.lai.module.newmemberentry.view.presenter;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.EntryActivity;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.newmemberentry.view.net.NewstudentsService;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class NewStudentInputImpl implements INewStudentpresenter{

    private NewstudentsService newstudentsService;
    private Context context;

    public NewStudentInputImpl(EntryActivity entryActivity){
        newstudentsService=ZillaApi.NormalRestAdapter.create(NewstudentsService.class);
        context=entryActivity;
    }

    @Override
    public void input(Newstudents newstudents) {
        Log.i("NewstudentsService>>>>>>>>>>>>>>"+newstudentsService);
        String token= SharedPreferenceService.getInstance().get("token","");
        newstudentsService.memberentry(token,newstudents, new Callback<ResponseData<Newstudents>>() {
            @Override
            public void success(ResponseData<Newstudents> newstudentsResponseData, Response response) {
                int status=newstudentsResponseData.getStatus();
                switch (status) {
                    case 200:
                       Intent intent=new Intent(context,Cursor.class);
                       context.startActivity(intent);
                        Util.toastMsg("录入成功");
                        break;
                    case 500:
                        Util.toastMsg("录入失败");
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

    @Override
    public void upload(final String upimg) {
        String token=SharedPreferenceService.getInstance().get("token","");
       newstudentsService.upimg(token,new TypedFile("image/png",new File(upimg)), new Callback<ResponseData<Img>>() {
           @Override
           public void success(ResponseData upimgResponseData, Response response) {
//               EventBus.getDefault().post(upimg);
               int status=upimgResponseData.getStatus();
               switch (status) {
                   case 200:
                       Util.toastMsg("获取成功");
                       break;
                   case 500:
                       Util.toastMsg("上传图片异常");
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
