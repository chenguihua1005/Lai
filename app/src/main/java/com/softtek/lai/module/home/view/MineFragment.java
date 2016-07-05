/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_my)
public class MineFragment extends BaseFragment implements View.OnClickListener, Validator.ValidationListener {
    @InjectView(R.id.tv_title)
    TextView title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.but_login_out)
    Button but_login_out;

    @InjectView(R.id.lin_validate_certification)
    LinearLayout lin_validate_certification;

    @InjectView(R.id.rel_nodify_person)
    RelativeLayout rel_nodify_person;

    @InjectView(R.id.lin_setting)
    LinearLayout lin_setting;

    @InjectView(R.id.lin_reset_password)
    LinearLayout lin_reset_password;

    @InjectView(R.id.lin_zx)
    LinearLayout lin_zx;

    @InjectView(R.id.lin_not_vr)
    LinearLayout lin_not_vr;

    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;

    @InjectView(R.id.but_login)
    Button but_login;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.text_zgzh)
    TextView text_zgzh;

    @InjectView(R.id.text_state)
    TextView text_state;

    @InjectView(R.id.img)
    ImageView img;

    private UserModel model;
    String photo;

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        but_login_out.setOnClickListener(this);
        lin_validate_certification.setOnClickListener(this);
        lin_setting.setOnClickListener(this);
        lin_reset_password.setOnClickListener(this);
        lin_not_vr.setOnClickListener(this);
        lin_is_vr.setOnClickListener(this);
        but_login.setOnClickListener(this);
        rel_nodify_person.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume------");
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String userrole = model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            lin_not_vr.setVisibility(View.GONE);
            lin_is_vr.setVisibility(View.VISIBLE);
        } else {
            lin_not_vr.setVisibility(View.VISIBLE);
            lin_is_vr.setVisibility(View.GONE);
        }
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(photo) || "null".equals(photo) || photo == null) {
            Picasso.with(getContext()).load("111").fit().error(R.drawable.img_default).into(img);
        } else {
           Picasso.with(getContext()).load(path + photo).fit().error(R.drawable.img_default).into(img);
        }

        if (StringUtils.isEmpty(model.getNickname())) {
            text_name.setText(model.getMobile());
        } else {
            text_name.setText(model.getNickname());
        }

        String certification = model.getCertification();
        if (String.valueOf(Constants.SR).equals(userrole) || String.valueOf(Constants.PC).equals(userrole) || String.valueOf(Constants.SP).equals(userrole)) {
            text_state.setText("已认证");
            text_state.setTextColor(getResources().getColor(R.color.green));
        } else {
            text_state.setText("未认证");
            text_state.setTextColor(getResources().getColor(R.color.grey_font));
        }

        text_zgzh.setText(certification);
    }

    @Override
    protected void initDatas() {
        title.setText("我的");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_login_out:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.login_out_title))
                        .setMessage(getString(R.string.login_out_message))
                        .setPositiveButton(getString(R.string.app_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearData();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialogBuilder.create().show();

                break;

            case R.id.lin_reset_password:
                Intent intent = new Intent(getContext(), ModifyPasswordActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("token", UserInfoModel.getInstance().getToken());
                startActivity(intent);
                break;
            case R.id.rel_nodify_person:
                startActivity(new Intent(getContext(), ModifyPersonActivity.class));
                break;
            case R.id.but_login:
                Intent login = new Intent(getContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                break;

            case R.id.lin_not_vr:

                break;
            case R.id.lin_is_vr:

                break;

            case R.id.lin_zx:
                break;
            case R.id.lin_validate_certification:
                startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                break;
            case R.id.lin_setting:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
        }
    }

    private void clearData() {
        UserInfoModel.getInstance().loginOut();
        getContext().stopService(new Intent(getContext(), StepService.class));
//        if(HomeFragment.timer!=null){
//            HomeFragment.timer.cancel();
//        }
//        if (EMChat.getInstance().isLoggedIn()) {
//            EMChatManager.getInstance().logout(true,new EMCallBack() {
//
//                @Override
//                public void onSuccess() {
//                    // TODO Auto-generated method stub
//                    System.out.println("onSuccess------");
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void onError(int code, String message) {
//                    // TODO Auto-generated method stub
//                    System.out.println("onError------");
//                }
//            });
//        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
