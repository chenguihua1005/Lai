/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.present;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.Adapter.StudentScoreAdapter;
import com.softtek.lai.module.bodygamest.model.HasClass;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.model.StudentScripInfo;
import com.softtek.lai.module.bodygamest.net.StudentService;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class StudentImpl implements IStudentPresenter {

    private StudentService studentService;
    private Context context;

    public StudentImpl(Context context) {
        this.context = context;
        studentService = ZillaApi.NormalRestAdapter.create(StudentService.class);
    }


    @Override
    public void getTranscrip(String classid, final ListView list_student_score) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        studentService.getTranscrip(token, classid, new Callback<ResponseData<List<StudentScripInfo>>>() {
            @Override
            public void success(ResponseData<List<StudentScripInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<StudentScripInfo> list=listResponseData.getData();
                switch (status) {
                    case 200:
                        StudentScoreAdapter adapter=new StudentScoreAdapter(context,list);
                        list_student_score.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取龙虎榜列表失败");
            }
        });
    }

    @Override
    public void getStudentHonor() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        studentService.getStudentHonor(token, new Callback<ResponseData<List<StudentHonorInfo>>>() {
            @Override
            public void success(ResponseData<List<StudentHonorInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取龙虎榜列表失败");
            }
        });
    }

    public void hasClass(RequestCallback<ResponseData<HasClass>> callback){
        String token= UserInfoModel.getInstance().getToken();
        studentService.hasClass(token,callback);
    }
}
