package com.softtek.lai.module.laijumine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.AboutMeActivity;
import com.softtek.lai.module.home.view.ModifyPasswordActivity;
import com.softtek.lai.module.home.view.ModifyPersonActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_setting)
public class MySettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.re_detail)
    RelativeLayout re_detail;
    @InjectView(R.id.re_setpassword)
    RelativeLayout re_setpassword;
    @InjectView(R.id.re_aboutour)
    RelativeLayout re_aboutour;
    @InjectView(R.id.tv_versionname)
    TextView tv_versionname;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.but_login_out)
    Button but_login_out;

    @Override
    protected void initViews() {
        tv_title.setText("设置");
        tv_versionname.setText("V" + DisplayUtil.getAppVersionName(this));
        re_detail.setOnClickListener(this);
        re_setpassword.setOnClickListener(this);
        re_aboutour.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        but_login_out.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_detail:
                startActivity(new Intent(this, ModifyPersonActivity.class));
                break;
            case R.id.re_setpassword:
                Intent intent = new Intent(this, ModifyPasswordActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("token", UserInfoModel.getInstance().getToken());
                startActivity(intent);
                break;
            case R.id.re_aboutour:
                startActivity(new Intent(this, AboutMeActivity.class));
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.but_login_out:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
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
        }

    }

    private void clearData() {

//        final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
//        if (!hxid.equals("-1")) {
//            SharedPreferenceService.getInstance().put("HXID", "-1");
//            EMClient.getInstance().logout(true, new EMCallBack() {
//                @Override
//                public void onSuccess() {
//                    //关闭环信服务
////                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(new Intent(HXLoginService.HXLOGIN_CLOSE_SELF));
//                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
//                    Looper.prepare();
//                    UserInfoModel.getInstance().loginOut();
//                    Intent intent=new Intent(getBaseContext(), LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                    Looper.loop();
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {}
//
//                @Override
//                public void onError(int code, String message) {}
//            });
//
//        } else {
        SharedPreferenceService.getInstance(this).put("ClassId", "");
        UserInfoModel.getInstance().loginOut();
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
        finish();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        }

    }
}
