package com.softtek.lai.module.studentbasedate.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface StudentBaseDateService {


    //获取PC基础信息
    @GET("/HerbrClass/GetClassMemberInfoPC")
    void getClassMemberInfoPC(@Header("token")String token,
                              RequestCallback<ResponseData<StudentBaseInfoModel>> callback);
    //获取PC曲线图信息
    @GET("/HerbrClass/GetClassMemberInfoCurvePC")
    void getClassMemberInfoCurvePC(@Header("token")String token,
                                   RequestCallback<ResponseData<List<StudentLinChartInfoModel>>> callback);

    //PC版获取班级动态
    @GET("/BasicData/GetClassDynamic")
    void getClassDynamic(@Header("token")String token,
                         @Query("classid")long classId,
                         @Query("pageIndex")int pageIndex,
                         RequestCallback<ResponseData<List<DynamicInfoModel>>> callback);
}
