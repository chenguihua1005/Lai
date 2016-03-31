package com.softtek.lai.module.counselor.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.adapter.InviteStudentAdapter;
import com.softtek.lai.module.counselor.model.InviteStudentInfo;
import com.softtek.lai.module.counselor.net.CounselorService;

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
public class StudentImpl implements IStudentPresenter {

    private CounselorService counselorService;
    private Context context;

    public StudentImpl(Context context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }


    @Override
    public void sendInviterMsg(String inviters, String classId, final ImageView img_invite) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        counselorService.sendInviterMsg(token, inviters, classId, new Callback<ResponseData>() {
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
        counselorService.getNotInvitePC(token, classid, spaccid, new Callback<ResponseData<List<InviteStudentInfo>>>() {
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

}
