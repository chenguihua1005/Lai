package com.softtek.lai.module.laiClassroom.net;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by jia.lu on 2017/3/10.
 */

public interface SerchService {

    @POST("")
    void getChaosInfo(@Header("token") String token);
}
