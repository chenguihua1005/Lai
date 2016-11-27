package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup2;
import com.softtek.lai.module.bodygame3.more.model.ClassInvitater;
import com.softtek.lai.module.bodygame3.more.model.ClassMember;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.Contact;
import com.softtek.lai.module.bodygame3.more.model.FuceDate;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.module.bodygame3.more.model.LaiClass;
import com.softtek.lai.module.bodygame3.more.model.LossWeightAndFat;
import com.softtek.lai.module.bodygame3.more.model.SendInvitation;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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

    @GET("/V1/MoreFunction/GetAllUserByKeyWord")
    void sendSearch(@Header("token")String token,
                    @Query("KeyWord")String key,
                    Callback<ResponseData<List<Contact>>> callback);

    //获取班级组别信息
    @GET("/V1/MoreFunction/GetClassGroup")
    void getClassGroupsInfo(@Header("token")String token,
                            @Query("ClassId")String classId,
                            Callback<ResponseData<List<ClassGroup2>>> callback);
    //修改班级名称
    @GET("/V1/MoreFunction/UpdateClass")
    void updateClassName(@Header("token")String token,
                         @Query("ClassId")String classId,
                         @Query("ClassName")String className,
                         Callback<ResponseData> callback);
    //修改组名
    @GET("/V1/MoreFunction/UpdateClassGroupName")
    void updateGroupName(@Header("token")String token,
                         @Query("CGId")String groupId,
                         @Query("CGName")String groupName,
                         Callback<ResponseData> callback);
    //删除小组
    @GET("/V1/MoreFunction/DeletClassGroup")
    void deleteGroup(@Header("token")String token,
                         @Query("CGId")String groupId,
                         Callback<ResponseData> callback);
    //添加小组
    @FormUrlEncoded
    @POST("/V1/MoreFunction/AddClassGroup")
    void addGroup(@Header("token")String token,
                  @Field("ClassId")String classId,
                  @Field("GroupName")String groupName,
                  @Field("AccountId")long accountId,
                  Callback<ResponseData<ClassGroup>> callback);

    //获取班级复测日
    @GET("/v1/MeasuredRecordLog/GetMeasureDateListByClassId")
    void getMeasureDateList(@Header("token")String token,
                            @Query("classId")String classId,
                            Callback<ResponseData<List<FuceDate>>> callback);
    //修改复测日期
    @GET("/V1/MoreFunction/UpdateMeasureDate")
    void updateMeasureDate(@Header("token")String token,
                           @Query("ClassId")String classId,
                           @Query("WeekNum")int weekNum,
                           @Query("MeasureDate")String date,
                           Callback<ResponseData> callback);

    //获取班级人员管理
    @GET("/V1/MoreFunction/GetClassesMembers")
    void getClassesMembers(@Header("token")String token,
                           @Query("ClassId")String classId,
                           Callback<ResponseData<ClassMember>> callback);

    //转租
    @GET("/V1/MoreFunction/TurnToAnotherGroup")
    void turnToAnotherGroup(@Header("token")String token,
                            @Query("TAccountId")long tAccountId,
                            @Query("ClassId")String classId,
                            @Query("CGId")String groupId,
                            Callback<ResponseData> callback);
    //移除
    @GET("/V1/MoreFunction/RemoveGroup")
    void removeFromGroup(@Header("token")String token,
                     @Query("RAccountId")long rAccountId,
                     @Query("ClassId")String classId,
                     @Query("CGId")String groupId,
                     Callback<ResponseData> callback);

    //减重减脂等级
    @GET("/V1/MoreFunction/GetLossLevel")
    void getLossLevel(@Header("token")String token,
                      @Query("AccountId")long accountId,
                      Callback<ResponseData<LossWeightAndFat>> callback);

}
