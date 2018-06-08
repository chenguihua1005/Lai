package com.softtek.lai.module.bodygame3.more.present;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.FuceAlbumModel;
import com.softtek.lai.module.bodygame3.more.net.StudentService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jessica.zhang on 3/9/2017.
 */

public class FuceAlbumManager {
    private StudentService studentService;
    private FuceAlbumManagerCallback cb;

    public FuceAlbumManager(FuceAlbumManagerCallback cb) {
        this.cb = cb;
        studentService = ZillaApi.NormalRestAdapter.create(StudentService.class);
    }

    public void getFuceAlbum(String token, long accountId, int pageIndex) {
        studentService.GetFucePhotos(token, accountId, pageIndex, new Callback<ResponseData<FuceAlbumModel>>() {
            @Override
            public void success(ResponseData<FuceAlbumModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (cb != null) {
                    cb.getFuceAlbum(responseData.getData());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (cb != null) {
                    cb.getFuceAlbum(null);
                }
            }
        });
    }


    public interface FuceAlbumManagerCallback {
        void getFuceAlbum(FuceAlbumModel fuceAlbumModel);
    }
}
