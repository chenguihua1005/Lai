/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.present;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.bodygamest.net.StudentService;
import com.softtek.lai.module.counselor.adapter.GameAdapter;
import com.softtek.lai.module.counselor.model.MarchInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.presenter.IGamePresenter;

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
public class StudentHonorImpl implements IStudentHonorPresenter {

    private StudentService studentService;
    private Context context;

    public StudentHonorImpl(Context context) {
        this.context = context;
        studentService = ZillaApi.NormalRestAdapter.create(StudentService.class);
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
}
