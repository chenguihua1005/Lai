package com.softtek.lai.module.grade.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.module.grade.net.GradeService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/28/2016.
 */
public class WaistManager {

    private GradeService service;
    private StudentListCallback cb;
    private String token;

    public WaistManager(StudentListCallback cb) {
        this.cb = cb;
        service= ZillaApi.NormalRestAdapter.create(GradeService.class);
        token=UserInfoModel.getInstance().getToken();
    }

    public void loadWaist(String classId){
        String userId= UserInfoModel.getInstance().getUser().getUserid();
        service.getStudentsList(token,Long.parseLong(userId),classId,"3", new Callback<ResponseData<List<StudentModel>>>() {
            @Override
            public void success(ResponseData<List<StudentModel>> studentResponseData, Response response) {
                switch (studentResponseData.getStatus()) {
                    case 200:
                        cb.updataData(studentResponseData.getData());
                        break;
                    default:
                        cb.updataData(null);
                        Util.toastMsg(studentResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.updataData(null);
                ZillaApi.dealNetError(error);
            }
        });
    }

}
