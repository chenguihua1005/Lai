package com.softtek.lai.module.studentbasedate.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.net.StudentBaseDateService;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class StudentBaseDateImpl implements IStudentBaseDate{

    private Context contex;
    private StudentBaseDateService service;
    private String token;
    public StudentBaseDateImpl(Context contex) {
        this.contex = contex;
        service= ZillaApi.NormalRestAdapter.create(StudentBaseDateService.class);
        token=UserInfoModel.getInstance().getToken();
    }

    @Override
    public void getClassMemberInfoPC() {
        service.getClassMemberInfoPC(token, new Callback<ResponseData<StudentBaseInfoModel>>() {
            @Override
            public void success(ResponseData<StudentBaseInfoModel> studentBaseInfoModelResponseData, Response response) {
                Log.i("学员信息："+studentBaseInfoModelResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getClassMemberInfoCurvePC() {
        service.getClassMemberInfoCurvePC(token, new Callback<ResponseData<List<StudentLinChartInfoModel>>>() {
            @Override
            public void success(ResponseData<List<StudentLinChartInfoModel>> listResponseData, Response response) {
                Log.i("学员曲线图："+listResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getClassDynamic(long classId) {
        service.getClassDynamic(token,classId, new Callback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                Log.i("班级动态："+listResponseData.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });
    }
}
