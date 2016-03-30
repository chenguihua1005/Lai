package com.softtek.lai.module.studetail.presenter;

import android.content.Context;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.studetail.model.Member;
import com.softtek.lai.module.studetail.net.MemberInfoService;
import com.softtek.lai.module.studetail.view.StudentDetailActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public class MemberInfoImpl implements IMemberInfopresenter {
    private MemberInfoService service;
    private Context context;

    public MemberInfoImpl(StudentDetailActivity studentDetailActivity){
        service=ZillaApi.NormalRestAdapter.create(MemberInfoService.class);
        context=studentDetailActivity;
    }

    @Override
    public void getMemberinfo(String classId,String userId) {
        String token= SharedPreferenceService.getInstance().get("token","");
        service.getmemberInfo(token,userId,classId, new Callback<ResponseData<Member>>() {
            @Override
            public void success(ResponseData<Member> fileResponseData, Response response) {
                int status=fileResponseData.getStatus();

            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg(R.string.neterror);
                error.printStackTrace();
            }
        });
    }
}
