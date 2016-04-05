/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.adapter.AssistantAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantApplyAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantClassAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantClassListAdapter;
import com.softtek.lai.module.counselor.model.*;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantDetailActivity;

import org.greenrobot.eventbus.EventBus;
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
public class AssistantImpl implements IAssistantPresenter {

    private CounselorService counselorService;
    private Context context;

    public AssistantImpl(Context context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }

    @Override
    public void showAllApplyAssistants(String accountId, final ListView listView) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.showAllApplyAssistants(token, accountId, new Callback<ResponseData<List<AssistantApplyInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantApplyInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantApplyInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        AssistantApplyAdapter adapter = new AssistantApplyAdapter(context, list);
                        listView.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取助教申请列表失败");
            }
        });
    }

    @Override
    public void showAssistantDetails(String assistantId, String classId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.showAssistantDetails(token, assistantId, classId, new Callback<ResponseData<AssistantDetailInfoModel>>() {
            @Override
            public void success(ResponseData<AssistantDetailInfoModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();

                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error:" + error);
                Util.toastMsg("获取助教详情失败");
            }
        });
    }

    @Override
    public void removeAssistantRoleByClass(String assistantId, String classId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.removeAssistantRoleByClass(token, assistantId, classId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();

                switch (status) {
                    case 200:
                        Util.toastMsg("移除成功");
                        ((AssistantDetailActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error:" + error);
                Util.toastMsg("移除失败");
            }
        });
    }

    @Override
    public void showAssistantByClass(String accountId, String classId, final ListView list_assistant) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.showAssistantByClass(token, accountId, classId, new Callback<ResponseData<List<AssistantInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        AssistantClassListAdapter adapter = new AssistantClassListAdapter(context, list);
                        list_assistant.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取助教列表失败");
            }
        });
    }

    @Override
    public void showAllClassList(String managerId, final ListView list_class) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.showAllClassList(token, managerId, new Callback<ResponseData<List<AssistantClassInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantClassInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantClassInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        AssistantClassAdapter adapter = new AssistantClassAdapter(context, list);
                        list_class.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取班级列表失败");
            }
        });
    }

    @Override
    public void reviewAssistantApplyList(long applyId, final int status, final LinearLayout lin_buttons, final TextView text_state) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.reviewAssistantApplyList(token, applyId, status, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response
                    response) {
                Log.e("jarvis", listResponseData.toString());
                int statusId = listResponseData.getStatus();

                switch (statusId) {
                    case 200:
                        lin_buttons.setVisibility(View.GONE);
                        text_state.setVisibility(View.VISIBLE);
                        if (status == 1) {
                            text_state.setText("已通过");
                        } else {
                            text_state.setText("已拒绝");
                        }
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("助教审批失败");
            }
        });
    }

    @Override
    public void getAssistantList(String classId, final ListView list_assistant) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.getAssistantList(token, classId, new Callback<ResponseData<List<AssistantModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        AssistantAdapter adapter = new AssistantAdapter(context, list);
                        list_assistant.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取助教列表失败");
            }
        });
    }

    @Override
    public void sendInviterSR(String classId, String Inviters, final ImageView img_invite) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.sendInviterSR(token, classId, Inviters, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response
                    response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();

                switch (status) {
                    case 200:
                        img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invited));
                        Util.toastMsg("邀请成功");
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("邀请助教失败");
            }
        });

    }
}
