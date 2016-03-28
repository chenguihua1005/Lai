package com.softtek.lai.module.retest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.Phot;
import com.softtek.lai.module.retest.Write;
import com.softtek.lai.module.retest.model.Banji;
import com.softtek.lai.module.retest.model.BanjiStudent;
import com.softtek.lai.module.retest.model.RetestAudit;
import com.softtek.lai.module.retest.model.RetestWrite;
import com.softtek.lai.module.retest.model.Student;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 3/21/2016.
 */
public interface RestService {
    @GET("/MeasuredRecordLog/ShowAllClassListBySP")
    void doGetRetestclass(@Header("token")String token,
                          @Query("id")long id,
                          Callback<ResponseData<List<Banji>>> callback);
    @GET("/MeasuredRecordLog/SearchMeasuredInfoByKeyword")
    void doGetqueryResult(
            @Header("token")String token,
            @Query("str")String str,
            Callback<ResponseData<List<Student>>> callback
    );
    @GET("/MeasuredRecordLog/SearchMeasuredInfoByClassId")
    void doGetBanjiStudent(
            @Header("token")String token,
            @Query("classId")long classId,
            Callback<ResponseData<List<BanjiStudent>>>callback
    );
    @GET("/MeasuredRecordLog/GetMeasuredRecord")
    void doGetAudit(
            @Header("token")String token,
            @Query("accountId")long accountId,
            @Query("classId")long classId,
            @Query("typeDate")String typeDate,
            Callback<ResponseData<List<RetestAudit>>>callback
    );
    @POST("/MeasuredRecordLog/SaveMeasuredRecord")
    void doGetWrite(
            @Header("token")String token,
            @Query("accountId") long accountId,
            @Query("loginId") long loginId,
            @Body RetestWrite retestWrite,
            Callback<ResponseData<List<RetestWrite>>>callback
    );
    @GET("/MeasuredRecordLog/AddMeasuredPhoto")
    void goGetPicture(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<Phot>> callback
    );

}
