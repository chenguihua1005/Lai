/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.jpush.JpushSet;
import com.softtek.lai.module.bodygame3.activity.view.FcStuActivity;
import com.softtek.lai.module.bodygame3.activity.view.WriteFCActivity;
import com.softtek.lai.module.bodygame3.photowall.PublishDyActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_my)
public class MineFragment extends LazyBaseFragment implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.but_login_out)
    Button but_login_out;

    @InjectView(R.id.rl_validate_certification)
    RelativeLayout rl_validate_certification;

    @InjectView(R.id.rel_nodify_person)
    RelativeLayout rel_nodify_person;

    @InjectView(R.id.rl_setting)
    RelativeLayout rl_setting;

    @InjectView(R.id.rl_reset_password)
    RelativeLayout rl_reset_password;

    @InjectView(R.id.rl_dynamic)
    RelativeLayout rl_dynamic;

    @InjectView(R.id.rl_zx)
    RelativeLayout rl_zx;

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
        rl_validate_certification.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_reset_password.setOnClickListener(this);
        lin_not_vr.setOnClickListener(this);
        lin_is_vr.setOnClickListener(this);
        but_login.setOnClickListener(this);
        rel_nodify_person.setOnClickListener(this);
        rl_dynamic.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (TextUtils.isEmpty(photo)) {
            Picasso.with(getContext()).load(R.drawable.img_default).into(img);
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
            text_state.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
        } else {
            text_state.setText("未认证");
            text_state.setTextColor(ContextCompat.getColor(getContext(),R.color.grey_font));
        }

        text_zgzh.setText(certification);
    }

    @Override
    protected void initDatas() {
        title.setText("我的");

    }

    @Override
    protected void lazyLoad() {

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

            case R.id.rl_reset_password:
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

            case R.id.rl_zx:
                break;
            case R.id.rl_validate_certification:
                startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                break;
            case R.id.rl_setting:
//                startActivity(new Intent(getContext(), SettingsActivity.class));
                startActivity(new Intent(getContext(), WriteFCActivity.class));
                break;
            case R.id.rl_dynamic:
                Intent personal=new Intent(getContext(), PersionalActivity.class);
                personal.putExtra("isFocus",1);
                personal.putExtra("personalId",String.valueOf(UserInfoModel.getInstance().getUserId()));
                personal.putExtra("personalName",text_name.getText().toString());
                startActivity(personal);
                break;
        }
    }

    private void clearData() {

        if (HomeFragment.timer != null) {
            HomeFragment.timer.cancel();
        }
        final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
        if (!hxid.equals("-1")) {
            SharedPreferenceService.getInstance().put("HXID", "-1");
            EMClient.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    UserInfoModel.getInstance().loginOut();
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                    getActivity().finish();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

                @Override
                public void onProgress(int progress, String status) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onError(int code, String message) {
                    // TODO Auto-generated method stub

                }
            });
        } else {
            UserInfoModel.getInstance().loginOut();
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
            getActivity().finish();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
        JPushInterface.init(getContext().getApplicationContext());
        JpushSet set = new JpushSet(getContext().getApplicationContext());
        set.setAlias("");
    }
}
