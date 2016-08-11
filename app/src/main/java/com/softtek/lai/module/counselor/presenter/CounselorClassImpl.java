/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassIdModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.counselor.view.CounselorClassListActivity;
import com.softtek.lai.module.login.view.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class CounselorClassImpl implements ICounselorClassPresenter {

    private CounselorService counselorService;
    private BaseActivity context;

    public CounselorClassImpl(BaseActivity context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }


    @Override
    public void getClassList(String classtype, final ListView expand_lis, final LinearLayout lin_create_class, final ImageView img_mo_message) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getClassList(token, classtype, new Callback<ResponseData<List<ClassInfoModel>>>() {

            @Override
            public void success(ResponseData<List<ClassInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                context.dialogDissmiss();
                int status = listResponseData.getStatus();
                List<ClassInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 100:

                        break;
                    case 200:
                        List<ClassInfoModel> lists = new ArrayList<ClassInfoModel>();
                        for (int i = 0; i < list.size(); i++) {
                            ClassInfoModel classInfoModel = list.get(i);
                            String type = classInfoModel.getClassStatus();
                            if (!"2".equals(type)) {
                                lists.add(classInfoModel);
                            }
                        }
                        CounselorClassAdapter adapter = new CounselorClassAdapter(context, lists);
                        expand_lis.setAdapter(adapter);
                        if (lists.size() > 0) {
                            expand_lis.setVisibility(View.VISIBLE);
                            img_mo_message.setVisibility(View.GONE);
                        } else {
                            expand_lis.setVisibility(View.GONE);
                            img_mo_message.setVisibility(View.VISIBLE);
                        }
                        if (Integer.parseInt(UserInfoModel.getInstance().getUser().getUserrole()) == Constants.SR) {
                            lin_create_class.setVisibility(View.GONE);
                            break;
                        }
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
                        int nextMonth = 1;
                        if (monthOfYear == 12) {
                            nextMonth = 1;
                        } else {
                            nextMonth = monthOfYear + 1;
                        }
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                        int count = 0;
                        for (int i = 0; i < lists.size(); i++) {
                            ClassInfoModel classInfo = lists.get(i);
                            String str[] = classInfo.getStartDate().toString().split("-");
                            if (str[1].equals(monthOfYear + "") || str[1].equals("0" + monthOfYear)) {
                                count++;
                            } else {
                            }
                            if (str[1].equals(nextMonth + "") || str[1].equals("0" + (nextMonth))) {
                                count++;
                            } else {

                            }
                        }
                        if (count == 2) {
                            lin_create_class.setVisibility(View.GONE);
                        } else {
                            lin_create_class.setVisibility(View.VISIBLE);
                        }
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
    public void createClass(String className, String startDate, String endDate, String managerId) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.createClass(token, className, startDate, endDate, managerId, new Callback<ResponseData<ClassIdModel>>() {
            @Override
            public void success(final ResponseData<ClassIdModel> classIdResponseData, Response response) {
                Log.e("jarvis", classIdResponseData.toString());
                int status = classIdResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        //EventBus.getDefault().post(classIdResponseData.getData());
                        Intent msgIntent = new Intent(Constants.MESSAGE_CREATE_CLASS_ACTION);
                        context.sendBroadcast(msgIntent);

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                                .setTitle(context.getString(R.string.login_out_title))
                                .setMessage("创建班级成功！")
                                .setPositiveButton(context.getString(R.string.app_sure), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferenceService.getInstance().put("classId", classIdResponseData.getData().getClassId());
                                        Intent intent = new Intent(context, AssistantListActivity.class);
                                        intent.putExtra("classId", classIdResponseData.getData().getClassId());
                                        intent.putExtra("type", "0");
                                        context.startActivity(intent);
                                        context.finish();
                                    }
                                });
                        Dialog dialog = dialogBuilder.create();
                        dialog.setCancelable(false);
                        dialog.show();
                        break;
                    case 100:
                        break;
                    default:
                        Util.toastMsg(classIdResponseData.getMsg());
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
