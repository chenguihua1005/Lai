package com.softtek.lai.module.studetail.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.studetail.model.Member;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public interface MemberInfoService {
    @GET("/HerbrClass/GetClassMemberInfo")
    void getmemberInfo(@Body Member file,
                       Callback<ResponseData<File>> callback);

}
