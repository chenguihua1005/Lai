package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantListActivity;

import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

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
    public void getClassList(final ListView expand_lis, final LinearLayout lin_create_class) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.getClassList(token, new Callback<ResponseData<List<ClassInfo>>>() {

            @Override
            public void success(ResponseData<List<ClassInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<ClassInfo> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        CounselorClassAdapter adapter = new CounselorClassAdapter(context, list);
                        expand_lis.setAdapter(adapter);
//                        if(list.size()>0){
//                            expand_lis.setVisibility(View.VISIBLE);
//                        }else {
//                            expand_lis.setVisibility(View.GONE);
//                        }
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
                            ClassInfo classInfo = list.get(i);
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
                error.printStackTrace();

                Util.toastMsg("获取列表失败");
            }
        });
    }

    @Override
    public void createClass(String className, String startDate, String endDate, String managerId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.createClass(token, className, startDate, endDate, managerId, new Callback<ResponseData<ClassId>>() {
            @Override
            public void success(ResponseData<ClassId> classIdResponseData, Response response) {
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
                Util.toastMsg("创建班级失败");
            }
        });
    }

}
