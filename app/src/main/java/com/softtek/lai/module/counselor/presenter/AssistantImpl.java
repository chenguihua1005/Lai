/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.adapter.ApplyAssistantAdapter;
import com.softtek.lai.module.counselor.adapter.AssistantAdapter;
import com.softtek.lai.module.counselor.model.ApplyAssistantModel;
import com.softtek.lai.module.counselor.model.ApplySuccessModel;
import com.softtek.lai.module.counselor.model.AssistantApplyEvent;
import com.softtek.lai.module.counselor.model.AssistantApplyInfoModel;
import com.softtek.lai.module.counselor.model.AssistantClassEvent;
import com.softtek.lai.module.counselor.model.AssistantClassInfoModel;
import com.softtek.lai.module.counselor.model.AssistantDetailInfoModel;
import com.softtek.lai.module.counselor.model.AssistantInfoEvent;
import com.softtek.lai.module.counselor.model.AssistantInfoModel;
import com.softtek.lai.module.counselor.model.AssistantModel;
import com.softtek.lai.module.counselor.model.ReviewAssistantApplyEvent;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.view.AssistantDetailActivity;
import com.softtek.lai.module.message.view.MessageActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantImpl implements IAssistantPresenter {

    private CounselorService counselorService;
    private BaseActivity context;

    public AssistantImpl(BaseActivity context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }

    @Override
    public void showAllApplyAssistants(String accountId, final ListView listView) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.showAllApplyAssistants(token, accountId, new Callback<ResponseData<List<AssistantApplyInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantApplyInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantApplyInfoModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new AssistantApplyEvent(list));
                        break;
                    case 201:

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
            }
        });
    }

    @Override
    public void srApplyAssistant(String applyerId, String classManagerId, String classId, String comments, final TextView text_apply, final TextView text_state) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.srApplyAssistant(token, applyerId, classManagerId, classId, comments, new Callback<ResponseData<ApplySuccessModel>>() {
            @Override
            public void success(ResponseData<ApplySuccessModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                ApplySuccessModel applySuccessModel = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        text_apply.setVisibility(View.GONE);
                        text_state.setVisibility(View.VISIBLE);
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
            }
        });
    }

    @Override
    public void showAssistantDetails(String assistantId, String classId) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.showAssistantDetails(token, assistantId, classId, new Callback<ResponseData<AssistantDetailInfoModel>>() {
            @Override
            public void success(ResponseData<AssistantDetailInfoModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
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
            }
        });
    }

    @Override
    public void removeAssistantRoleByClass(String assistantId, final String classId, final String type) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.removeAssistantRoleByClass(token, assistantId, classId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        if ("message".equals(type)) {
                            Intent intent =context.getIntent();
                            //把返回数据存入Intent
                            intent.putExtra("type", "xzs");
                            //设置返回数据
                            context.setResult(context.RESULT_OK, intent);
                            context.finish();
                        } else {
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("classId", classId);
                            ((AssistantDetailActivity) context).setResult(((AssistantDetailActivity) context).RESULT_OK, intent);
                            ((AssistantDetailActivity) context).finish();
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
            }
        });
    }

    @Override
    public void showSRApplyList(String assistantId, final ListView list_apply_assistant) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.showSRApplyList(token, assistantId, new Callback<ResponseData<List<ApplyAssistantModel>>>() {
            @Override
            public void success(ResponseData<List<ApplyAssistantModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<ApplyAssistantModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        ApplyAssistantAdapter applyAssistantAdapter = new ApplyAssistantAdapter(context, list);
                        list_apply_assistant.setAdapter(applyAssistantAdapter);
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
            }
        });
    }

    @Override
    public void showAssistantByClass(String accountId, String classId, final ListView list_assistant) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.showAssistantByClass(token, accountId, classId, new Callback<ResponseData<List<AssistantInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantInfoModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        list_assistant.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post(new AssistantInfoEvent(list));
                        break;
                    case 100:
                        list_assistant.setVisibility(View.GONE);
                        break;
                    default:
                        list_assistant.setVisibility(View.GONE);
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                context.dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void showAllClassList(String managerId, final ListView list_class) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.showAllClassList(token, managerId, new Callback<ResponseData<List<AssistantClassInfoModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantClassInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantClassInfoModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new AssistantClassEvent(list));
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
            }
        });
    }

    @Override
    public void reviewAssistantApplyList(long applyId, final int status, final int posion, final String type) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.reviewAssistantApplyList(token, applyId, status, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response
                    response) {
                Log.e("jarvis", listResponseData.toString());
                int statusId = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (statusId) {
                    case 200:
                        if ("manage".equals(type)) {
                            EventBus.getDefault().post(new ReviewAssistantApplyEvent(posion));
                        } else {
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("type", "xzs");
                            //设置返回数据
                            context.setResult(context.RESULT_OK, intent);
                            context.finish();
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
            }
        });
    }

    @Override
    public void getAssistantList(String classId, final ListView list_assistant) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getAssistantList(token, classId, new Callback<ResponseData<List<AssistantModel>>>() {
            @Override
            public void success(ResponseData<List<AssistantModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantModel> list = listResponseData.getData();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        AssistantAdapter adapter = new AssistantAdapter(context, list);
                        list_assistant.setAdapter(adapter);
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
            }
        });
    }

    @Override
    public void sendInviterSR(String classId, String Inviters, final ImageView img_invite) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.sendInviterSR(token, classId, Inviters, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response
                    response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                context.dialogDissmiss();
                switch (status) {
                    case 200:
                        img_invite.setImageDrawable(context.getResources().getDrawable(R.drawable.img_invited));
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
            }
        });

    }
}
