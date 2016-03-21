package com.softtek.lai.module.grade.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.net.GradeService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/21/2016.
 *
 */
public class GradeImpl implements IGrade{

    private GradeService service;

    public GradeImpl(){
        service= ZillaApi.NormalRestAdapter.create(GradeService.class);
    }

    @Override
    public void getGradeInfos(long classId) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getGradeInfo(token, String.valueOf(classId), new Callback<ResponseData<Grade>>() {
            @Override
            public void success(ResponseData<Grade> gradeResponseData, Response response) {
                Log.i(gradeResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void sendDynamic() {

    }
}
