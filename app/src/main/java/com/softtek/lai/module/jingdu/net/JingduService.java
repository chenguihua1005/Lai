/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.jingdu.model.RankModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by julie.zhu on 3/28/2016.
 */
public interface JingduService {
    @GET("/Index/GetCurrentProgress")
    void getproinfo(@Header("token") String token,
                    Callback<ResponseData<RankModel>> callback);
}
