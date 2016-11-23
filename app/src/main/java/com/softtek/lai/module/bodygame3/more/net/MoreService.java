package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.ClassInvitater;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.Contact;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.model.SendInvitation;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 11/18/2016.
 * 更多界面接口
 */

public interface MoreService {


    //获取小区列表
    @GET("/V1/MoreFunction/GetRegionalAndCitys")
    void getRegionalAndCitys(@Header("token")String token,
                             Callback<ResponseData<List<SmallRegion>>> callback);
    //创建班级
    @POST("/V1/MoreFunction/EstablishClass")
    void creatClass(@Header("token")String token,
                    @Body LaiClass clazz,
                    Callback<ResponseData<LaiClass>> callback);

    //获取更多页面的班级数据
    @GET("/V1/MoreFunction/GetMoreInfo")
    void getMoreInfo(@Header("token")String token,
                     @Query("AccountId")long accountId,
                     Callback<ResponseData<List<ClassModel>>> callback);

    //获取邀请人列表
    @GET("/V1/MoreFunction/GetInvitedContactList")
    void getInvitatedContactList(@Header("token")String token,
                                 @Query("AccountId")long accountId,
                                 @Query("ClassId")String classId,
                                 @Query("PageSize")int pageSize,
                                 @Query("PageIndex")int pageIndex,
                                 Callback<ResponseData<List<InvitatedContact>>> callback);
    //获取联系人列表
    @GET("/V1/MoreFunction/GetHerbalifeContact")
    void getContactsList(@Header("token")String token,
                         @Query("AccountId")long accountId,
                         @Query("PageSize")int pageSize,
                         @Query("PageIndex")int pageIndex,
                         Callback<ResponseData<List<Contact>>> callback);

    //邀请设置
    @GET("/V1/MoreFunction/GetClassInfoForInvite")
    void getClassInfoForInvite(@Header("token")String token,
                               @Query("ClassId")String classId,
                               @Query("SenderId")long senderId,
                               @Query("InviterId")long inviterId,
                               Callback<ResponseData<ClassInvitater>> callback);

    //发送邀请
    @POST("/V1/MoreFunction/SendInviter")
    void sendInviter(@Header("token")String token,
                     @Body SendInvitation invitation,
                     Callback<ResponseData> callback);

}
