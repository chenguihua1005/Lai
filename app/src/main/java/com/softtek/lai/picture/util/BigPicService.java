package com.softtek.lai.picture.util;

import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Streaming;

/**
 * Created by jerry.guan on 2/24/2017.
 */

public interface BigPicService {

    @GET("/UpFiles/{path}")
    void downloadPic(@Path("path")String path,
                     Callback<Response> callback);
}
