package com.softtek.lai.module.bodygame3.more.view;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

public class HeadCoachFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rl_invitation;
    private RelativeLayout rl_class_manager;
    private RelativeLayout rl_honor;

    private RelativeLayout rl_love_student;//爱心学员
    private RelativeLayout rl_support_team;// 服务团队


    public HeadCoachFragment() {

    }

    ClassModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        model = getArguments().getParcelable("class");
        return inflater.inflate(R.layout.fragment_head_coach, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout rl_level = (RelativeLayout) view.findViewById(R.id.rl_level);//减重等级
        rl_level.setOnClickListener(this);


        rl_invitation = (RelativeLayout) view.findViewById(R.id.rl_invitation);//邀请小伙伴
        rl_class_manager = (RelativeLayout) view.findViewById(R.id.rl_class_manager);//班级管理
//        rl_honor = (RelativeLayout) view.findViewById(R.id.rl_honor);
        rl_love_student = (RelativeLayout) view.findViewById(R.id.rl_love_student);
        rl_support_team = (RelativeLayout) view.findViewById(R.id.rl_support_team);

        RelativeLayout rl_fuce_album = (RelativeLayout) view.findViewById(R.id.rl_fuce_album);//复测相册
        rl_fuce_album.setOnClickListener(this);
        RelativeLayout rl_exit = (RelativeLayout) view.findViewById(R.id.rl_exit);//退赛  (非总教练 在班级中有角色)

        int role = model.getClassRole();
        if (role == 2 | role == 3 || role == 4) {
            rl_exit.setVisibility(View.VISIBLE);
        } else {
            rl_exit.setVisibility(View.GONE);
        }

        rl_exit.setOnClickListener(this);

        rl_invitation.setOnClickListener(this);
        rl_class_manager.setOnClickListener(this);
//        rl_honor.setOnClickListener(this);
        rl_love_student.setOnClickListener(this);
        rl_support_team.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//         model = getArguments().getParcelable("class");
        switch (view.getId()) {
            case R.id.rl_level: {//减重等级
                Intent intent = new Intent(getContext(), LossWeightAndFatActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.rl_invitation: {
                Intent intent = new Intent(getContext(), InvitationListActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;
            case R.id.rl_class_manager: {
                Intent intent = new Intent(getContext(), ClassManagerActivity.class);
                intent.putExtra("class", model);
                startActivity(intent);
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
