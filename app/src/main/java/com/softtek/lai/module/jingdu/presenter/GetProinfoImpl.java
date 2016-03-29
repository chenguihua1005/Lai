package com.softtek.lai.module.jingdu.presenter;

import android.content.Context;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.jingdu.EventModel.RankEvent;
import com.softtek.lai.module.jingdu.model.Rank;
import com.softtek.lai.module.jingdu.net.JingduService;
import com.softtek.lai.module.jingdu.view.JingduActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public class GetProinfoImpl implements IGetProinfopresenter{
    private JingduService service;

    public GetProinfoImpl(){
        service= ZillaApi.NormalRestAdapter.create(JingduService.class);
    }

    @Override
    public void getproinfo(String classId, String ordertype) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getproinfo(token,classId,ordertype, new Callback<ResponseData<List<Rank>>>() {
            @Override
            public void success(ResponseData<List<Rank>>rankResponseData, Response response) {
                int status=rankResponseData.getStatus();
                switch (status){
                    case 200:
                        EventBus.getDefault().post(new RankEvent(rankResponseData.getData()));
                        Util.toastMsg("保存成功");
                        break;
                    case 100:
                        Util.toastMsg("保存失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }

}
