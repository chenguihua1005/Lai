package com.softtek.lai.module.customermanagement.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.customermanagement.model.ClubCreateResponse;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.module.customermanagement.model.InviteModel;
import com.softtek.lai.module.customermanagement.model.PersonnelModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 11/29/2017.
 */

public interface ClubService {
    @FormUrlEncoded
    @POST("/v1/Club/InviteToBeWorker")
    void inviteClubPersonnel(
            @Header("token") String token,
            @Field("clubId") int clubId,
            @Field("userId") long userId,
            RequestCallback<ResponseData<CustomerListModel>> callback
    );

    @POST("/v1/Club/CreateClub")
    void createClub(@Header("token") String token,
                    @Query("name") String name,
                    RequestCallback<ResponseData<ClubCreateResponse>> callback);

    @POST("/v1/Club/CloseClub")
    void closeClub(@Header("token") String token,
                   @Query("clubId") String clubId,
                   RequestCallback<ResponseData> callback);

    @POST("/v1/Club/RemoveWorker")
    void deleteWorker(@Header("token") String token,
                      @Query("clubId") String clubId,
                      @Query("userId") long userId,
                      RequestCallback<ResponseData> callback);

    @GET("/v1/Club/ClubIndex")
    void getClubInfo(@Header("token")String token,
                     @Query("clubId") String id,
                     @Query("field") int field,
                     @Query("sort")int sort,
                     RequestCallback<ResponseData<PersonnelModel>> callback);

    @POST("/v1/Club/UpdateClubName")
    void changeClubName(@Header("token") String token,
                        @Query("clubId") String id,
                        @Query("name") String name,
                        RequestCallback<ResponseData> callback);

    @GET("/v1/Club/GetListOfInvitedMessage")
    void getListOfInvitedMessage(@Header("token") String token,
                                 @Query("clubId") String clubId,
                                 @Query("index") int index,
                                 @Query("size") int size,
                                 RequestCallback<ResponseData<InviteModel>> callback);

    @GET("/v1/Club/FindAccounts")
    void findAccounts(@Header("token") String token,
                      @Query("input") String input,
                      @Query("index") int index,
                      @Query("size") int size,
                      RequestCallback<ResponseData<InviteModel>> callback);

    @POST("/v1/Club/InviteToBeWorker")
    void invitetoBeWorker(@Header("token")String token,
                          @Query("clubId") String clubId,
                          @Query("userId") long userId,
                          RequestCallback<ResponseData> callback);

    @GET("/v1/MsgCenter/MakeSureJoin")
    void makeSureJoin(@Header("token")String token,
                      @Query("MsgId") String msdId,
                      @Query("status")int status,
                      @Query("IntroducerId")long IntroducerId,
                      RequestCallback<ResponseData> callback);
}
