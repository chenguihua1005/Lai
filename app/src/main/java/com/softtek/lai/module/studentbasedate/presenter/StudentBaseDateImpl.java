package com.softtek.lai.module.studentbasedate.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.net.StudentBaseDateService;
import com.softtek.lai.utils.RequestCallback;

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

    public interface StudentBaseDataCallback{
        void getClassMemberInfo(StudentBaseInfoModel studentBaseInfoModel);

    }
}
