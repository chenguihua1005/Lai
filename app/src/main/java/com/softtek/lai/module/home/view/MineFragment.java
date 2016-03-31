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

    private ACache aCache;

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.GONE);
        but_login_out.setOnClickListener(this);
        lin_validate_certification.setOnClickListener(this);
        lin_setting.setOnClickListener(this);
        lin_reset_password.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        aCache = ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);
        title.setText("我的");

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
            case R.id.lin_validate_certification:
                startActivity(new Intent(getContext(),ValidateCertificationActivity.class));
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
