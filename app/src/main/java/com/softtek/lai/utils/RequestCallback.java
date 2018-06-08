package com.softtek.lai.utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/11/2016.
 */
public abstract class RequestCallback<T> implements Callback<T>{

    @Override
    public abstract void success(T t, Response response);

    @Override
    public void failure(RetrofitError error) {
        ZillaApi.dealNetError(error);
    }
}
