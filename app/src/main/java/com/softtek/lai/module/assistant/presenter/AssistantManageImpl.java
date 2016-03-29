package com.softtek.lai.module.assistant.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.assistant.adapter.AssistantApplyAdapter;
import com.softtek.lai.module.assistant.adapter.AssistantClassAdapter;
import com.softtek.lai.module.assistant.adapter.AssistantClassListAdapter;
import com.softtek.lai.module.assistant.adapter.InviteStudentAdapter;
import com.softtek.lai.module.assistant.model.AssistantApplyInfo;
import com.softtek.lai.module.assistant.model.AssistantClassInfo;
import com.softtek.lai.module.assistant.model.AssistantDetailInfo;
import com.softtek.lai.module.assistant.model.AssistantInfo;
import com.softtek.lai.module.assistant.model.InviteStudentInfo;
import com.softtek.lai.module.assistant.net.AssistantManageService;
import com.softtek.lai.module.counselor.adapter.AssistantAdapter;
import com.softtek.lai.module.counselor.model.Assistant;
import com.softtek.lai.module.counselor.net.AssistantService;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;

import org.greenrobot.eventbus.EventBus;

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
public class AssistantManageImpl implements IAssistantManagePresenter {

    private AssistantManageService assistantManageService;
    private Context context;

    public AssistantManageImpl(Context context) {
        this.context = context;
        assistantManageService = ZillaApi.NormalRestAdapter.create(AssistantManageService.class);
    }


    @Override
    public void showAllApplyAssistants(String accountId, final ListView listView) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantManageService.showAllApplyAssistants(token, accountId, new Callback<ResponseData<List<AssistantApplyInfo>>>() {
            @Override
            public void success(ResponseData<List<AssistantApplyInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantApplyInfo> list = listResponseData.getData();
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
    public void sendInviterMsg(String inviters, String classId, final ImageView img_invite) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantManageService.sendInviterMsg(token, inviters, classId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
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
                Util.toastMsg("邀请失败");
            }
        });
    }

    @Override
    public void getNotInvitePC(String classid, String spaccid, final ListView list_student) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantManageService.getNotInvitePC(token, classid, spaccid, new Callback<ResponseData<List<InviteStudentInfo>>>() {
            @Override
            public void success(ResponseData<List<InviteStudentInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<InviteStudentInfo> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        InviteStudentAdapter adapter = new InviteStudentAdapter(context, list);
                        list_student.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取学员列表失败");
            }
        });
    }

    @Override
    public void showAssistantDetails(String assistantId, String classId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantManageService.showAssistantDetails(token, assistantId, classId, new Callback<ResponseData<AssistantDetailInfo>>() {
            @Override
            public void success(ResponseData<AssistantDetailInfo> listResponseData, Response response) {
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
                Util.toastMsg("获取助教申请列表失败");
            }
        });
    }

    @Override
    public void showAssistantByClass(String accountId, String classId, final ListView list_assistant) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantManageService.showAssistantByClass(token, accountId, classId, new Callback<ResponseData<List<AssistantInfo>>>() {
            @Override
            public void success(ResponseData<List<AssistantInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantInfo> list = listResponseData.getData();
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
        assistantManageService.showAllClassList(token, managerId, new Callback<ResponseData<List<AssistantClassInfo>>>() {
            @Override
            public void success(ResponseData<List<AssistantClassInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<AssistantClassInfo> list = listResponseData.getData();
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
        assistantManageService.reviewAssistantApplyList(token, applyId, status, new Callback<ResponseData>() {
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
}
