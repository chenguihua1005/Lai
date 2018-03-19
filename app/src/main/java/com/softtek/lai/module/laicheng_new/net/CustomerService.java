package com.softtek.lai.module.laicheng_new.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laicheng_new.model.BleResponseData;
import com.softtek.lai.module.laicheng_new.model.CustomerData;
import com.softtek.lai.module.laicheng_new.model.GroupModel;
import com.softtek.lai.module.laicheng_new.model.PostQnData;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jia.lu on 11/29/2017.
 */

public interface CustomerService {
    @GET("/v1/Club/GetListOfCustomer")
    void getCustomer(@Header("token") String token,
                        @Query("index") int index,
                        @Query("size") int size,
                        RequestCallback<ResponseData<CustomerData>> callback);


    @GET("/v1/Club/GetListOfMarketingStaff")
    void getMarketPersonnel(@Header("token") String token,
                            @Query("index") int index,
                            @Query("size") int size,
                            RequestCallback<ResponseData<CustomerData>> callback);

    @GET("/v1/Club/GetListOfClassMember ")
    void getListOfClassMember(@Header("token")String token,
                              RequestCallback<ResponseData<List<GroupModel>>> callback);
}
