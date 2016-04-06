/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.presenter;

import android.content.Context;
import android.content.Intent;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.FileModel;
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

    public CreateFileImpl(CreatFlleActivity creatFlleActivity) {
        service = ZillaApi.NormalRestAdapter.create(FileService.class);
        context = creatFlleActivity;
    }


    @Override
    public void createFile(String token, FileModel file) {
        Log.i(file.toString());
        service.doFile(token, file, new Callback<ResponseData<FileModel>>() {
            @Override
            public void success(ResponseData<FileModel> fileResponseData, Response response) {
                int status = fileResponseData.getStatus();
                switch (status) {
                    case 200: {
                        Util.toastMsg("创建档案成功");
                        Intent intent = new Intent(context, HomeActviity.class);
                        context.startActivity(intent);
                        break;
                    }

                    case 100:
                        Util.toastMsg("创建档案失败");
                        break;
                    case 101:
                        Util.toastMsg("该昵称不合法");
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
