package com.softtek.lai.module.pastreview.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.pastreview.model.ClassListModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 6/28/2016.
 * 学员班往期回顾网络接口
 */
public interface PCPastReview {
@GET("/Review/GetHistoryClassList")
    void doGetHistoryClassList(
        @Header("token")String token,
        @Query("accountid")String accountid,
        Callback<ResponseData<List<ClassListModel>>>callback
);
}
