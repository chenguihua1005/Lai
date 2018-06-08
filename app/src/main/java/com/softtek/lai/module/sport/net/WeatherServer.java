package com.softtek.lai.module.sport.net;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 6/23/2016.
 */
public interface WeatherServer {

    @GET("/WeatherApi")
    void getWeather(@Query("city")String city,
                    Callback<Response> callback);
}
