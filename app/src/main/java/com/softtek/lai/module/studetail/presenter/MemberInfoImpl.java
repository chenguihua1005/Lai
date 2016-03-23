package com.softtek.lai.module.studetail.presenter;

import android.content.Context;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.studetail.model.Member;
import com.softtek.lai.module.studetail.net.MemberInfoService;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class MemberInfoImpl implements IMemberInfopresenter {
    private MemberInfoService service;
    private Context context;

    public MemberInfoImpl(StudentDetailActivity studentDetailActivity){
        service= (MemberInfoService) ZillaApi.NormalRestAdapter.create(MemberInfoImpl.class);
        context=studentDetailActivity;
    }

    @Override
    public void getmemberinfo(Member member) {
        service.getmemberInfo(member, new Callback<ResponseData<File>>() {
            @Override
            public void success(ResponseData<File> fileResponseData, Response response) {
                int status=fileResponseData.getStatus();
                switch (status){
                    case 200: {
                        Util.toastMsg("保存成功");
                        break;
                    }

                    case 300:
                        Util.toastMsg("参数错误");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
