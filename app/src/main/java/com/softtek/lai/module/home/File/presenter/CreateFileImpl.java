package com.softtek.lai.module.home.File.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.File.model.File;
import com.softtek.lai.module.home.File.net.FileService;
import com.softtek.lai.module.home.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.tab.TabMainActivity;

import retrofit.RetrofitError;
import zilla.libcore.api.ZillaApi;

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
    public void CreateFile(String token, String nickname, String brithday, int height, int weight, int gender) {

        service.doFile(token, nickname, brithday, height, weight, gender, new retrofit.Callback<ResponseData<File>>() {
            @Override
            public void success(ResponseData<File> fileResponseData, retrofit.client.Response response) {
                Log.i("档案创建成功");
                context.startActivity(new Intent(context, TabMainActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("档案创建失败");
            }
        });
    }

//    public interface ICreateFileView {
//        void toActivity();
//    }
}
