package com.softtek.lai.module.bodygame3.head.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.EditSignaModel;
import com.softtek.lai.module.bodygame3.head.model.ChooseModel;
import com.softtek.lai.module.bodygame3.head.model.ClassDetailModel;
import com.softtek.lai.module.bodygame3.head.model.ClassdataModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.model.HeadModel2;
import com.softtek.lai.module.bodygame3.head.model.HonorGroupRankModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsModel;
import com.softtek.lai.module.bodygame3.head.model.PantnerpageModel;
import com.softtek.lai.module.bodygame3.head.model.PartnertotalModel;
import com.softtek.lai.module.bodygame3.photowall.model.PublicDyModel;
import com.softtek.lai.module.bodygame3.photowall.model.TopicModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public interface HeadService {
    //    首页版本1接口
    @GET("/V1/HerbalifeClass/GetClassInfoDefaultHome")
    void getfirst(
            @Header("classid") String classid,
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("pagesize") int pagesize,
            @Query("classid") String classId,
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


    //检索小伙伴
    @GET("/V1/HerbalifeClass/GetSearchClassPartner")
    void getpartner(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("keyword") String keyword,
            @Query("classid") String classid,
            @Query("pagesize") int pagesize,
            @Query("pageindex") int pageindex,
            RequestCallback<ResponseData<PantnerpageModel>> callback
    );
//按类型分页加载小伙伴

//    classid	String
//    sorttype	Int
//    pagesize	Int
//    pageindex	Int

    @GET("/V1/HerbalifeClass/GetClassPartner")
//    请求路径:Api/V1/ HerbalifeClass / GetClassPartner
    void getpartnertype(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("classid") String classid,
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
//            @Header("classid") String CId,
            @Header("token") String token,
            @Query("loginuserid") long loginuserid,//登录id
            @Query("accountid") long accountid,//学员id
            @Query("classid") String classid,//班级id
            Callback<ResponseData<MemberInfoModel>> callback
    );

    //选择班级加载数据请求路径:请求路径:Api/V1/ HerbalifeClass / GetClassInfo130ed197-17ea-4125-8643-09f9c8ec377
    @GET("/V1/HerbalifeClass/GetClassInfo")
    void choose(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("classid") String classid,
            @Query("classweeknum") String classweeknum,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<ChooseModel>> callback
    );

    //请求路径:Api/V1/ ClassHonor/ GetHonorRoll(荣誉榜)
    @GET("/V1/ClassHonor/GetHonorRoll")
    void doGetHonorRoll(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("UID") Long UID,
            @Query("ClassId") String ClassId,
            @Query("ByWhichRatio") String ByWhichRatio,//ByFatRatio按减脂比，ByWeightRatio按减重比
            @Query("SortTimeType") String SortTimeType,//ByWeek周排序，ByMonth月排序，ByTotal总排名
            @Query("WhichTime") int WhichTime,
            @Query("IsFirst") boolean IsFirst,
            Callback<ResponseData<HonorRankModel>> callback
    );

    //请求路径:/api/v1/club/GetHonorRoll荣誉榜)
    @GET("/v1/club/GetHonorRoll")
    void getHonorRoll(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("UID") Long UID,
            @Query("ClassId") String ClassId,
            @Query("ByWhichRatio") String ByWhichRatio,//ByFatRatio按减脂比，ByWeightRatio按减重比
            @Query("SortTimeType") String SortTimeType,//ByWeek周排序，ByMonth月排序，ByTotal总排名
            @Query("WhichTime") int WhichTime,
            Callback<ResponseData<HonorRankModel>> callback
    );

    //请求路径:Api/V1/ ClassHonor/ GetHonorGroupList(荣誉榜——小组排名接口)
    @GET("/V1/ClassHonor/GetHonorGroupList")
    void doGetHonorGroup(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("ClassId") String ClassId,
            @Query("ByWhichRatio") String ByWhichRatio,//ByFatRatio按减脂比，ByWeightRatio按减重比
            @Query("SortTimeType") String SortTimeType,//ByWeek周排序，ByMonth月排序，ByTotal总排名
            @Query("WhichTime") int WhichTime,
            @Query("GroupId") String GroupId,
            Callback<ResponseData<HonorGroupRankModel>> callback
    );

    //请求路径:Api/V1/ MsgCenter/ ApplyJoinClass
    //申请加入班级
    @GET("/V1/MsgCenter/ApplyJoinClass")
    void doPostClass(
            @Header("token") String token,
            @Query("Applyer") Long Applyer,//申请人id
            @Query("ClassId") String ClassId,//班级id
            Callback<ResponseData> callback
    );


    //请求路径:Api/V1/HealthyCircle/GetPhWallTheme
    //照片墙主题列表
    @GET("/V1/HealthyCircle/GetPhWallTheme")
    void doGetPhWallTheme(
            @Header("token") String token

    );


    //请求路径:Api/V1/HerbalifeClass/AddMineLovePC
    @GET("/V1/HerbalifeClass/AddMineLovePC")
    void doPostAddMineLovePC(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("classid") String classid,
            @Query("mobile") String mobile,
            Callback<ResponseData> callback
    );

    //个人详情（环信id）
    //请求路径:Api/V1/HerbalifeClass/GetClassMemberInfoByHx
    @GET("/V1/HerbalifeClass/GetClassMemberInfoByHx")
    void doGetClassMemberInfoByHx(
            @Header("token") String token,
            @Query("loginuserid") long loginuserid,
            @Query("hxaccountid") String hxaccountid,
            @Query("classid") String classid,
            Callback<ResponseData<MemberInfoModel>> callback
    );

    //关注接口
    @POST("/HealthyCircle/FocusAccount")
    void doFocusAccount(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("focusaccid") long focusaccid,
            Callback<ResponseData> callback
    );

    //取消关注接口
    @POST("/HealthyCircle/CancleFocusAccount")
    void doCancleFocusAccount(
            @Header("token") String token,
            @Query("accountid") long accountid,
            @Query("focusaccid") long focusaccid,
            Callback<ResponseData> callback
    );

    //发表照片墙动态
    //请求路径:Api/V1/HealthyCircle/CreatePhotoWall
    @POST("/V1/HealthyCircle/CreatePhotoWall")
    void doCreatePhotoWall(
            @Header("token") String token,
            @Body PublicDyModel publicDyModel,
            Callback<ResponseData> callback
    );

    //照片墙主题列表
    //请求路径:Api/V1/HealthyCircle/GetPhWallTheme
    @GET("/V1/HealthyCircle/GetPhWallTheme")
    void doGetPhWallTheme(
            @Header("token") String token,
            Callback<ResponseData<List<TopicModel>>> callback
    );

    //请求路径:Api/V1/HerbalifeClass/CommitPersonalityName
    @POST("/V1/HerbalifeClass/CommitPersonalityName")
    void doCommitSina(
            @Header("token") String token,
            @Body EditSignaModel editSignaModel,
            Callback<ResponseData> callback
    );

    //请求路径:Api/V1/HerbalifeClass/GetClassDetial
    //获取班级详情数据(用户学员获取复测、初始数据信息)
    @GET("/V1/HerbalifeClass/GetClassDetial")
    void doGetClassDetial(
            @Header("token") String token,
            @Query("accountid") Long accountid,
            @Query("classid") String classid,
            Callback<ResponseData<ClassDetailModel>> callback
    );

    //请求路径:Api/V1/ MineCustomer/ MineClass
    @GET("/V1/MineCustomer/MineClass")
    void getclass(@Header("token") String token,
                  @Query("AccountId") Long AccountId,
                  Callback<ResponseData<ClassdataModel>> callback
    );


    //请求路径:Api/V1/LaiClassRoom/AddVideoClickNum
    @GET("/V1/LaiClassRoom/AddVideoClickNum")
    void getcount(
            @Header("token") String token,
            @Query("LaiClassroomId") Long LaiClassroomId,
            Callback<ResponseData> callback
    );
}
