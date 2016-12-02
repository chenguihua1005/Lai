package com.softtek.lai.module.bodygame3.head.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.model.HeadModel2;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.model.PantnerpageModel;
import com.softtek.lai.module.bodygame3.head.model.PartnertotalModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public interface HeadService {
    //    首页版本1接口
    @GET("/V1/HerbalifeClass/GetClassInfoDefaultHome")
    void getfirst(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<ClassinfoModel>> callback
    );

    //首页版本2
    @GET("/V1/HerbalifeClass/GetTotalPCandLoss")
    void getsecond(
            @Header("token") String token,
            Callback<ResponseData<HeadModel2>> callback
    );

    //检索班级请求路径:Api/V1/HerbalifeClass/GetSearchClass
    @GET("/V1/HerbalifeClass/GetSearchClass")
    void getclass(
            @Header("token") String token,
            @Query("keyword") String keyword,
            Callback<ResponseData<List<ClasslistModel>>> callback
    );

//    classid
//            sorttype
//    pagesize
//            pageindex
//    keyword

//    请求路径:Api/V1/ HerbalifeClass / GetSearchClassPartner
    //检索小伙伴:Api/V1/HerbalifeClass /GetSearchClassPartner
    @GET("/V1/HerbalifeClass/GetSearchClassPartner")
    void getpartner(
            @Header("token") String token,
            @Query("keyword") String keyword,
            @Query("classid") String classid,
            @Query("pagesize") int pagesize,
            @Query("pageindex") int pageindex,
            Callback<ResponseData<PantnerpageModel>> callback
    );
//按类型分页加载小伙伴

//    classid	String
//    sorttype	Int
//    pagesize	Int
//    pageindex	Int

    @GET("/V1/HerbalifeClass/GetClassPartner")
//    请求路径:Api/V1/ HerbalifeClass / GetClassPartner
    void getpartnertype(
            @Header("token") String token,
            @Query("classid")  String classid,
            @Query("sorttype") int sorttype,
            @Query("pagesize") int pagesize,
            @Query("pageindex") int pageindex,
            Callback<ResponseData<PartnertotalModel>> callback
    );
//    请求路径:Api/V1/MsgCenter/UnReadTiMsgCnt
    @GET("/V1/MsgCenter/UnReadTiMsgCnt")
    void hasemail(
            @Header("token") String token,
            @Query("accountid") long accountid,
            Callback<ResponseData<NewsModel>> callback
    );
    //个人详情
    @GET("/V1/HerbalifeClass/GetClassMemberInfo")
    void doGetClassMemberInfo(
            @Header("token") String token,
            @Query("loginuserid") long loginuserid,//登录id
            @Query("accountid") long accountid,//学员id
            @Query("classid") String classid,//班级id
            Callback<ResponseData<MemberInfoModel>> callback
    );


}
