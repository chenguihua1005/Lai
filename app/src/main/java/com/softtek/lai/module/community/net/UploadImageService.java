package com.softtek.lai.module.community.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.model.ImageResponse;
import com.softtek.lai.module.community.model.ImageResponse2;
import com.softtek.lai.utils.RequestCallback;

import java.io.File;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;

/**
 * Created by John on 2016/4/24.
 * 图片上传线程
 */
public class UploadImageService implements Runnable{

    private File image;
    private CommunityService service;
    private UploadImageCallback cb;

    public UploadImageService(UploadImageCallback cb,File image) {
        this.cb=cb;
        this.image=image;
        service= ZillaApi.NormalRestAdapter.create(CommunityService.class);
    }

    @Override
    public void run() {
        String token= UserInfoModel.getInstance().getToken();
        service.uploadSingleImage(token, new TypedFile("image/*", image),
                new RequestCallback<ResponseData<ImageResponse2>>() {
                    @Override
                    public void success(ResponseData<ImageResponse2> data, Response response) {
                        cb.getImageName(data.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        cb.getImageName(null);
                        super.failure(error);
                    }
                });
    }

    public interface UploadImageCallback{
        void getImageName(ImageResponse2 imageName);
    }
}
