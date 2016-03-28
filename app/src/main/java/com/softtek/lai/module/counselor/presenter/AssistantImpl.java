package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.counselor.adapter.AssistantAdapter;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.Assistant;
import com.softtek.lai.module.counselor.model.ClassId;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.net.AssistantService;
import com.softtek.lai.module.counselor.net.CounselorClassService;
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
public class AssistantImpl implements IAssistantPresenter {

    private AssistantService assistantService;
    private Context context;

    public AssistantImpl(Context context) {
        this.context = context;
        assistantService = ZillaApi.NormalRestAdapter.create(AssistantService.class);
    }


    @Override
    public void getAssistantList(String classId, final ListView list_assistant) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        assistantService.getAssistantList(token, classId, new Callback<ResponseData<List<Assistant>>>() {
            @Override
            public void success(ResponseData<List<Assistant>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<Assistant> list = listResponseData.getData();
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
        assistantService.sendInviterSR(token, classId, Inviters, new Callback<ResponseData>() {
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
