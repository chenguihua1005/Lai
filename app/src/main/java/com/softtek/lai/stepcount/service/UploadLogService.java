package com.softtek.lai.stepcount.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.community.model.ImageResponse;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.ZipUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;

public class UploadLogService extends IntentService {


    public UploadLogService() {
        super("UploadLogService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        //检查日志是否存在
        File file=Environment.getExternalStorageDirectory();
        //日志目录
        final File log=new File(file,"com_softtek_lai");
        final File zip=new File(file,"com_softtek_lai.zip");
        if(zip.exists()){
            zip.delete();
        }
        if(log.exists()&&log.isDirectory()){
            try {
                ZipUtils.zipFolder(log, zip);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //上传
            if(StringUtils.isNotEmpty(UserInfoModel.getInstance().getToken())&&zip.exists()){
                if("0".equals(Constants.IS_UPLOAD)){
                    deleteFile(log);
                    zip.delete();
                } else {
                    ZillaApi.NormalRestAdapter.create(CommunityService.class)
                            .uploadMutilpartImage(UserInfoModel.getInstance().getToken(), new TypedFile("*/*", zip), new RequestCallback<ResponseData<ImageResponse>>() {
                                @Override
                                public void success(ResponseData<ImageResponse> imageResponseResponseData, Response response) {
                                    deleteFile(log);
                                    zip.delete();
                                    Log.i("上传日志成功");

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    zip.delete();
                                }
                            });
                }
            }
        }
    }

    private void deleteFile(File file){
        if(file.isFile()){
            file.delete();
        }else {
            File[] files=file.listFiles();
            for (File file1:files){
                if(file1.isFile()){
                    file1.delete();
                }else {
                    deleteFile(file1);
                }
            }
        }
    }

}
