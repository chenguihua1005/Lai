package com.softtek.lai.module.home.File.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.File.model.File;
import com.softtek.lai.module.home.File.net.FileService;
import com.squareup.okhttp.Callback;

import retrofit.RetrofitError;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;

/**
 * Created by julie.zhu on 3/7/2016.
 */
public class CreateFile implements CreateFilepresenter {

    private FileService service;

    public CreateFile(){
        service=ZillaApi.NormalRestAdapter.create(FileService.class);
    }

    @Override
    public void CreateFile(String token, String appid, String nickname, String brithday, int height, int weight, int gender) {
        service.doFile(token, PropertiesManager.get("appid"), nickname, brithday, height, weight, gender, new retrofit.Callback<ResponseData<File>>() {
            @Override
            public void success(ResponseData<File> fileResponseData, retrofit.client.Response response) {
                Log.i("创建成功");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("创建失败");
            }
        });
    }
}
