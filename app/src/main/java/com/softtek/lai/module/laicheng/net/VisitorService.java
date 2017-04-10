package com.softtek.lai.module.laicheng.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public interface VisitorService {


    @POST("/v1/LaiBalanceSync/SaveLBVisitor")
    void commitvisit(
            @Header("token") String token,
            @Body VisitorModel visitorModel,
            Callback<ResponseData<Visitsmodel>> callback
            );
}
