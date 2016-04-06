/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.InjectView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_my)
public class MineFragment extends BaseFragment implements View.OnClickListener, Validator.ValidationListener {
    @InjectView(R.id.tv_title)
    TextView title;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.but_login_out)
    Button but_login_out;

    @InjectView(R.id.lin_validate_certification)
    LinearLayout lin_validate_certification;

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

    private ACache aCache;

    private UserModel model;

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.GONE);
        but_login_out.setOnClickListener(this);
        lin_validate_certification.setOnClickListener(this);
        lin_setting.setOnClickListener(this);
        lin_reset_password.setOnClickListener(this);
        lin_not_vr.setOnClickListener(this);
        lin_is_vr.setOnClickListener(this);
        but_login.setOnClickListener(this);
        model = UserInfoModel.getInstance().getUser();
        String userrole=model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            lin_not_vr.setVisibility(View.GONE);
            lin_is_vr.setVisibility(View.VISIBLE);
        } else {
            lin_not_vr.setVisibility(View.VISIBLE);
            lin_is_vr.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initDatas() {
        aCache = ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);
        title.setText("我的");

        if ("".equals(model.getNickname())) {
            text_name.setText(model.getMobile().toString());
        } else {
            text_name.setText(model.getNickname().toString());
        }

        String certification = model.getCertification().toString();

        if ("".equals(certification)) {
            text_state.setText("未认证");
            text_state.setTextColor(getResources().getColor(R.color.grey_font));
        } else {
            text_state.setText("已认证");
            text_state.setTextColor(getResources().getColor(R.color.green));
        }

        text_zgzh.setText(certification);

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

                break;
            case R.id.but_login:
                startActivity(new Intent(getContext(),LoginActivity.class));
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

                break;
        }
    }

    private void clearData() {
        UserInfoModel.getInstance().loginOut();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
