package com.softtek.lai.module.healthchart.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthchart.model.HealthCircrumModel;
import com.softtek.lai.module.healthchart.model.HealthFatModel;
import com.softtek.lai.module.healthchart.model.HealthHiplieModel;
import com.softtek.lai.module.healthchart.model.HealthUpArmGirthModel;
import com.softtek.lai.module.healthchart.model.HealthWaistlineModel;
import com.softtek.lai.module.healthchart.model.HealthWeightModel;
import com.softtek.lai.module.healthchart.model.HealthdoLegGirthModel;
import com.softtek.lai.module.healthchart.model.HealthupLegGirthModel;
import com.softtek.lai.module.healthchart.model.PysicalModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by John on 2016/4/12.
 */
public interface HealthyService {

        //获取体脂
        @GET("/HealthRecords/GetHealthPysicalRecords")
        void doGetHealthPysicalRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                Callback<ResponseData<PysicalModel>> callback
        );
        //获取体重
        @GET("/HealthRecords/GetHealthWeightRecords")
        void GetHealthWeightRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthWeightModel>> callback
        );
        //获取内脂
        @GET("/HealthRecords/GetHealthfatRecords")
        void doGetHealthfatRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthFatModel>> callback
        );
        //获取胸围
        @GET("/HealthRecords/GetHealthcircumRecords")
        void doGetHealthcircumRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthCircrumModel>> callback
        );
        //获取腰围
        @GET("/HealthRecords/GetHealthwaistlineRecords")
        void doGetHealthwaistlineRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthWaistlineModel>> callback
        );
        //获取臀围
        @GET("/HealthRecords/GetHealthhiplieRecords")
        void doGetHealthhiplieRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthHiplieModel>> callback
        );
        //获取上臂围
        @GET("/HealthRecords/GetHealthupArmGirthRecords")
        void doGetHealthupArmGirthRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthUpArmGirthModel>> callback
        );
        //获取大腿围
        @GET("/HealthRecords/GetHealthupLegGirthRecords")
        void doGetHealthupLegGirthRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthupLegGirthModel>> callback
        );
        //获取小腿围
        @GET("/HealthRecords/GetHealthdoLegGirthRecords")
        void doGetHealthdoLegGirthRecords(
                @Header("token") String token,
                @Query("Startdate")String Startdate,
                @Query("Enddate")String Enddate,
                @Query("i")int i,
                RequestCallback<ResponseData<HealthdoLegGirthModel>> callback
        );

}
