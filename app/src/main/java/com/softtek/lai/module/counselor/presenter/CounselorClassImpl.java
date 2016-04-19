/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassIdModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.login.model.UserModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class CounselorClassImpl implements ICounselorClassPresenter {

    private CounselorService counselorService;
    private Context context;

    public CounselorClassImpl(Context context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }


    @Override
    public void getClassList(String classtype,final ListView expand_lis, final LinearLayout lin_create_class, final ImageView img_mo_message) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getClassList(token, classtype,new Callback<ResponseData<List<ClassInfoModel>>>() {

            @Override
            public void success(ResponseData<List<ClassInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<ClassInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        CounselorClassAdapter adapter = new CounselorClassAdapter(context, list);
                        expand_lis.setAdapter(adapter);
                        if (list.size() > 0) {
                            expand_lis.setVisibility(View.VISIBLE);
                            img_mo_message.setVisibility(View.GONE);
                        } else {
                            expand_lis.setVisibility(View.GONE);
                            img_mo_message.setVisibility(View.VISIBLE);
                        }
                        UserModel user=UserInfoModel.getInstance().getUser();
                        if(String.valueOf(Constants.SR).equals(user.getUserrole()))break;
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
                        System.out.println("list--------------" + list);
                        int count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            ClassInfoModel classInfo = list.get(i);
                            String str[] = classInfo.getStartDate().toString().split("-");
                            if (str[1].equals(monthOfYear + "") || str[1].equals("0" + monthOfYear)) {
                                System.out.println("当前月已开班" + str[1]);
                                count++;
                            } else {
                                System.out.println("当前月未开班" + str[1]);
                            }
                            if (str[1].equals(nextMonth + "") || str[1].equals("0" + (nextMonth))) {
                                System.out.println("次月已开班" + str[1]);
                                count++;
                            } else {
                                System.out.println("次月未开班" + str[1]);
                            }
                        }
                        System.out.println("count:" + count);

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
            public void success(ResponseData<ClassIdModel> classIdResponseData, Response response) {
                Log.e("jarvis", classIdResponseData.toString());
                int status = classIdResponseData.getStatus();
                switch (status) {
                    case 200:
                        SharedPreferenceService.getInstance().put("classId", classIdResponseData.getData().getClassId());
                        Intent intent = new Intent(context, AssistantListActivity.class);
                        intent.putExtra("classId", classIdResponseData.getData().getClassId());
                        context.startActivity(intent);
                        break;
                    default:
                        Util.toastMsg(classIdResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

}
