package com.softtek.lai.module.grade.presenter;

import android.app.ProgressDialog;
import android.os.SystemClock;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.eventModel.LossWeightEvent;
import com.softtek.lai.module.grade.eventModel.SRInfoEvent;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.module.grade.model.Student;
import com.softtek.lai.module.grade.net.GradeService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
    public void sendDynamic(long classId, String dynamicTitle, String dyContent, int dyType, long accountId) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.senDynamic(token, classId, dynamicTitle, dyContent, dyType, accountId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Util.toastMsg(responseData.getMsg());
                Log.i(responseData.getMsg());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void getStudentList(String orderType, String classId, final PullToRefreshListView lv) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getStudentsList(token, classId, orderType, new Callback<ResponseData<List<Student>>>() {
            @Override
            public void success(ResponseData<List<Student>> studentResponseData, Response response) {
                lv.onRefreshComplete();
                switch (studentResponseData.getStatus()){
                    case 200:
                        Log.i(studentResponseData.toString());
                        EventBus.getDefault().post(new LossWeightEvent(studentResponseData.getData()));
                        break;
                    default:
                        Util.toastMsg(studentResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                lv.onRefreshComplete();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }

    @Override
    public void getTutorList(long classId,final PullToRefreshListView lv) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getTutorList(token, classId, new Callback<ResponseData<List<SRInfo>>>() {
            @Override
            public void success(ResponseData<List<SRInfo>> responseData, Response response) {
                lv.onRefreshComplete();
                switch (responseData.getStatus()){
                    case 200:
                        Log.i(responseData.toString());
                        EventBus.getDefault().post(new SRInfoEvent(responseData.getData()));
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                lv.onRefreshComplete();
                error.printStackTrace();
                Util.toastMsg(R.string.neterror);
            }
        });
    }


}
