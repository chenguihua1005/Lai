package com.softtek.lai.module.customermanagement.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

public interface CustomerService {

    /**
     *获取意向客户列表
     * @param token
     * @param callback
     */
    @GET("/v1/Club/FindCustomer")
    void getIntentCustomerList(
            @Header("token") String token,
            @Query("index") int index,//当前页码，必填，可以填0
            @Query("size") int size,//每页显示的记录数量，必填，可以填0
            RequestCallback<ResponseData<CustomerListModel>> callback
    );

}
