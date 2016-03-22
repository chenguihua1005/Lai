package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;
import android.os.SystemClock;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.net.GradeService;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;
import zilla.libzilla.dialog.LoadingDialog;

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
    public void getGradeInfos(final long classId, final ProgressDialog loadingDialog) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getGradeInfo(token, String.valueOf(classId), new Callback<ResponseData<Grade>>() {
            @Override
            public void success(ResponseData<Grade> gradeResponseData, Response response) {
                loadingDialog.dismiss();
                int status=gradeResponseData.getStatus();
                switch(status){
                    case 200:
                        EventBus.getDefault().post(gradeResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(R.string.neterror);
                        break;
                }
                Log.i(gradeResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void sendDynamic() {

    }
}
