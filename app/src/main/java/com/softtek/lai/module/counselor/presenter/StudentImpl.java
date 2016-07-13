/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.adapter.InviteStudentAdapter;
import com.softtek.lai.module.counselor.model.InviteStudentInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class StudentImpl implements IStudentPresenter {

    private CounselorService counselorService;
    private BaseActivity context;

    public StudentImpl(BaseActivity context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }

    @Override
    public void sendInviterMsg(String inviters, String classId, final ImageView img_invite) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.sendInviterMsg(token, inviters, classId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invited));
                        Util.toastMsg("邀请成功");
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getNotInvitePC(String classid, String spaccid, final ListView list_student) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getNotInvitePC(token, classid, spaccid, new Callback<ResponseData<List<InviteStudentInfoModel>>>() {
            @Override
            public void success(ResponseData<List<InviteStudentInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<InviteStudentInfoModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        InviteStudentAdapter adapter = new InviteStudentAdapter(context, list);
                        list_student.setAdapter(adapter);
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void classInvitePCISOK(final String classid, final String type) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.classInvitePCISOK(token, classid, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        if ("0".equals(type)) {
                            Intent intent = new Intent(context, AssistantListActivity.class);
                            intent.putExtra("classId", Long.parseLong(classid));
                            intent.putExtra("type", "1");
                            context.startActivity(intent);
                        } else {
                            Intent intents = new Intent(context, InviteStudentActivity.class);
                            intents.putExtra("classId", Long.parseLong(classid));
                            context.startActivity(intents);
                        }
                        break;
                    case 100:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

}
