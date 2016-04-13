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
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class StudentBaseDateImpl implements IStudentBaseDate{

    private StudentBaseDateService service;
    private String token;
    private StudentBaseDataCallback cb;
    public StudentBaseDateImpl(Context contex) {
        cb= (StudentBaseDataCallback) contex;
        service= ZillaApi.NormalRestAdapter.create(StudentBaseDateService.class);
        token=UserInfoModel.getInstance().getToken();
    }

    @Override
    public void getClassMemberInfoPC() {
        service.getClassMemberInfoPC(token, new RequestCallback<ResponseData<StudentBaseInfoModel>>() {
            @Override
            public void success(ResponseData<StudentBaseInfoModel> studentBaseInfoModelResponseData, Response response) {
                Log.i("学员信息："+studentBaseInfoModelResponseData.toString());
                if(studentBaseInfoModelResponseData.getStatus()==200){
                    if(cb!=null)cb.getClassMemberInfo(studentBaseInfoModelResponseData.getData());
                }else{
                    if(cb!=null)cb.getClassMemberInfo(null);
                }
            }

        });
    }

    @Override
    public void getClassMemberInfoCurvePC() {
        service.getClassMemberInfoCurvePC(token, new RequestCallback<ResponseData<List<StudentLinChartInfoModel>>>() {
            @Override
            public void success(ResponseData<List<StudentLinChartInfoModel>> listResponseData, Response response) {
                Log.i("学员曲线图："+listResponseData.toString());
            }

        });
    }

    @Override
    public void getClassDynamic(long classId) {
        service.getClassDynamic(token,classId, new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                Log.i("班级动态："+listResponseData.toString());
            }
        });
    }

    public interface StudentBaseDataCallback{
        void getClassMemberInfo(StudentBaseInfoModel studentBaseInfoModel);
    }
}
