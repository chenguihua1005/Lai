package com.softtek.lai.module.bodygame2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 7/11/2016.
 */
public interface BodyGameService {
    //班级主页
    @GET("/NewClass/ClassMainIndex")
    void doClassMainIndex(
        @Header("token")String token,
        @Query("accountid")String accountid,
        RequestCallback<ResponseData<List<ClassMainModel>>> callback
    );
}
