package com.softtek.lai.module.mygrades.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.eventModel.DayRankEvent;
import com.softtek.lai.module.mygrades.eventModel.GradesEvent;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.model.HonorModel;
import com.softtek.lai.module.mygrades.net.GradesService;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class GradesImpl implements IGradesPresenter{

    private GradesService gradesService;

    public  GradesImpl(){
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);
    }

    //2.19.1	我的成绩
    @Override
        public void getStepCount(String start, String end) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getStepCount(token,start,end,new Callback<ResponseData<List<GradesModel>>>() {
            @Override
            public void success(ResponseData<List<GradesModel>> listResponseData, Response response) {

                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        EventBus.getDefault().post(new GradesEvent(listResponseData.getData()));
                       // Util.toastMsg("我的成绩--查询正确");
                        break;
                    case 500:
                        Util.toastMsg("我的成绩--查询出bug");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }

//    @Override
//    public void getGradeHonor() {
//
//    }


}
