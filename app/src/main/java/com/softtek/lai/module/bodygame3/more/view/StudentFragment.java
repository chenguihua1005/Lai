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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hyphenate.EMError;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
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
                            .existClass(model.getClassId(), UserInfoModel.getInstance().getToken(),
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
//                    Util.toastMsg("退出班级失败！");
                    dialogDissmiss();
                    int error_code = msg.arg1;
                    Log.i("StudentFragment", "error_code = " + error_code + " EMError.GROUP_NOT_JOINED = " + EMError.GROUP_NOT_JOINED);
                    if (EMError.GROUP_NOT_JOINED == error_code) {
                        Log.i("StudentFragment", "执行后台。。。。。。。");
                        ZillaApi.NormalRestAdapter.create(MoreService.class)
                                .existClass(model.getClassId(), UserInfoModel.getInstance().getToken(),
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
                    }
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
        RelativeLayout rl_level = (RelativeLayout) view.findViewById(R.id.rl_level);//减重等级
        rl_level.setOnClickListener(this);
        RelativeLayout rl_media = (RelativeLayout) view.findViewById(R.id.rl_media);//我的勋章 暂时不显示
        rl_media.setOnClickListener(this);
        RelativeLayout rl_exit = (RelativeLayout) view.findViewById(R.id.rl_exit);//退赛
        rl_exit.setOnClickListener(this);
//        int role = model.getClassRole();
//        if (role == 2 | role == 3 || role == 4) {
//            rl_exit.setVisibility(View.VISIBLE);
//        } else {
//            rl_exit.setVisibility(View.GONE);
//        }

        RelativeLayout rl_love_student = (RelativeLayout) view.findViewById(R.id.rl_love_student);//爱心学员
        rl_love_student.setOnClickListener(this);
        RelativeLayout rl_support_team = (RelativeLayout) view.findViewById(R.id.rl_support_team);// 服务团队
        rl_support_team.setOnClickListener(this);

        RelativeLayout rl_fuce_album = (RelativeLayout) view.findViewById(R.id.rl_fuce_album);//复测相册
        rl_fuce_album.setOnClickListener(this);
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
            case R.id.rl_fuce_album: {
                Intent intent = new Intent(getContext(), FuceAlbumActivity.class);
                intent.putExtra("account", String.valueOf(UserInfoModel.getInstance().getUserId()));
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

                                ZillaApi.NormalRestAdapter.create(MoreService.class)
                                        .existClass(model.getClassId(), UserInfoModel.getInstance().getToken(),
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
