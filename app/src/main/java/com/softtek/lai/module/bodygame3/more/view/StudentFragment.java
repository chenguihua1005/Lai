package com.softtek.lai.module.bodygame3.more.view;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.view.HonorActivity;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;

import org.greenrobot.eventbus.EventBus;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.id.rl_exit;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x0001: {
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .existClass(UserInfoModel.getInstance().getToken(),
                                    UserInfoModel.getInstance().getUserId(),
                                    model.getClassId(),
                                    new retrofit.Callback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            dialogDissmiss();
                                            if (responseData.getStatus() == 200) {
                                                Util.toastMsg("退出班级成功！");
                                                EventBus.getDefault().post(new UpdateClass(2, model));
                                            } else {
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            dialogDissmiss();
                                            ZillaApi.dealNetError(error);
                                            Util.toastMsg("退出班级失败！");
                                        }
                                    });


                    break;
                }
                case 0x0002:
                    Util.toastMsg("退出班级失败！");
                    dialogDissmiss();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    ClassModel model;


    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = getArguments().getParcelable("class");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout rl_level = (RelativeLayout) view.findViewById(R.id.rl_level);
        rl_level.setOnClickListener(this);
        RelativeLayout rl_media = (RelativeLayout) view.findViewById(R.id.rl_media);
        rl_media.setOnClickListener(this);
        RelativeLayout rl_exit = (RelativeLayout) view.findViewById(R.id.rl_exit);
        rl_exit.setOnClickListener(this);
        RelativeLayout rl_honor = (RelativeLayout) view.findViewById(R.id.rl_honor);
        rl_honor.setOnClickListener(this);

        RelativeLayout rl_love_student = (RelativeLayout) view.findViewById(R.id.rl_love_student);////爱心学员
        rl_love_student.setOnClickListener(this);
        RelativeLayout rl_support_team = (RelativeLayout) view.findViewById(R.id.rl_support_team);//// 服务团队
        rl_support_team.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_level: {
                Intent intent = new Intent(getContext(), LossWeightAndFatActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.rl_media: {
                startActivity(new Intent(getContext(), StudentHonorGridActivity.class));
            }
            break;
            case R.id.rl_honor: {
                HonorActivity.startHonorActivity(getContext(), model.getClassId());
            }
            break;
            case R.id.rl_love_student: {
                Intent intent = new Intent(getContext(), LoveStudentActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;

            case R.id.rl_support_team: {
                Intent intent = new Intent(getContext(), SupportTeamActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;

            case rl_exit: {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("您确定退出当前班级？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogShow("退出班级");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            EMClient.getInstance().groupManager().leaveGroup(model.getHXGroupId());
                                            Message msg = new Message();
                                            msg.what = 0x0001;
                                            handler.sendMessage(msg);
                                        } catch (Exception e) {
//                                            Util.toastMsg("退出班级失败！");
//                                            Looper.prepare();
//                                            dialogDissmiss();
//                                            Looper.loop();
                                            Message msg = new Message();
                                            msg.what = 0x0002;
                                            handler.sendMessage(msg);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }).create().show();
            }
            break;
        }
    }

    private ProgressDialog progressDialogs;

    public void dialogShow(String value) {
        if (progressDialogs == null) {
            progressDialogs = new ProgressDialog(getContext());
            progressDialogs.setCanceledOnTouchOutside(false);
            progressDialogs.setCancelable(false);
            progressDialogs.setMessage(value);
            progressDialogs.show();
        }
    }

    public void dialogDissmiss() {
        if (progressDialogs != null) {
            progressDialogs.dismiss();
            progressDialogs = null;
        }
    }
}
