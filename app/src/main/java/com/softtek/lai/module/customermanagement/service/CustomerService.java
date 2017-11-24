package com.softtek.lai.module.customermanagement.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.customermanagement.model.CustomerInfoModel;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.module.customermanagement.model.FindCustomerModel;
import com.softtek.lai.module.login.model.IdentifyModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

public interface CustomerService {

    /**
     * 获取意向客户列表 /v1/Club/FindCustomer
     *
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


    /**
     * 获取市场人员列表 /v1/Club/FindMarketingStaff
     *
     * @param token
     * @param callback
     */
    @GET("/v1/Club/FindMarketingStaff")
    void getMarketingStaffList(
            @Header("token") String token,
            @Query("index") int index,//当前页码，必填，可以填0
            @Query("size") int size,//每页显示的记录数量，必填，可以填0
            RequestCallback<ResponseData<CustomerListModel>> callback
    );

    /**
     * 通过手机查找客户
     *
     * @param token
     * @param mobile
     * @param callback
     */
    @GET("/v1/Club/FindCustomerByMobile")
    void findCustomerByMobile(
            @Header("token") String token,
            @Query("mobile") String mobile,
            RequestCallback<ResponseData<FindCustomerModel>> callback

    );


    /**
     * 名称	添加/修改客户
     *
     * @param token
     * @param model
     * @param callback
     */
    @POST("/v1/Club/SaveCustomer")
    void saveCustomer(
            @Header("token") String token,
            @Body CustomerInfoModel model,
            RequestCallback<ResponseData> callback
    );

    /**
     * 获取验证码
     *
     * @param phone
     * @param status
     * @param callback
     */
    @FormUrlEncoded
    @POST("/HerbUser/GetVerificationNum")
    void getIdentify(
            @Field("phone") String phone,
            @Field("status") String status,
            Callback<ResponseData<IdentifyModel>> callback);

}
