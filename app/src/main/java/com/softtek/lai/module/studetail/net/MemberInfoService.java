/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.studetail.model.MemberModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public interface MemberInfoService {

    @GET("/HerbrClass/GetClassMemberInfo")
    void getmemberInfo(@Header("token") String token,
                       @Query("userId") String userId,
                       @Query("classId") String classId,
                       Callback<ResponseData<MemberModel>> callback);

}
