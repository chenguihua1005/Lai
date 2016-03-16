package com.softtek.lai.module.File.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.File.net.FileService;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public class CreateFileImpl implements ICreateFilepresenter {

    private FileService service;
    private Context context;

    public CreateFileImpl(CreatFlleActivity creatFlleActivity){
        service=ZillaApi.NormalRestAdapter.create(FileService.class);
        context=creatFlleActivity;
    }


    @Override
    public void createFile(String token, File file) {

        service.doFile(token, file, new Callback<ResponseData<File>>() {
            @Override
            public void success(ResponseData<File> fileResponseData, Response response) {
                int status=fileResponseData.getStatus();
                switch (status){
                    case 200: {
                        Util.toastMsg("创建档案成功");
                        Intent intent=new Intent(context,CreatFlleActivity.class);
                        context.startActivity(intent);
                        break;
                    }

                    case 100:
                        Util.toastMsg("创建档案失败");
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


//    public interface ICreateFileView {
//        void toActivity();
//    }
}
