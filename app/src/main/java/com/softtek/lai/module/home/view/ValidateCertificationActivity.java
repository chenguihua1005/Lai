package com.softtek.lai.module.home.view;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教管理页面
 */
@InjectLayout(R.layout.activity_validate_certification)
public class ValidateCertificationActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @Required(order = 1, messageResId = R.string.accountValidateNull)
    @InjectView(R.id.edit_account)
    EditText edit_account;

    @Required(order = 2, messageResId = R.string.passwordValidateNull)
    @InjectView(R.id.edit_password)
    EditText edit_password;

    @InjectView(R.id.but_validate)
    Button but_validate;

    @InjectView(R.id.text_time)
    TextView text_time;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_value)
    TextView text_value;


    private ILoginPresenter loginPresenter;


    private UserModel model;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        but_validate.setOnClickListener(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void rigstHX() {
        if (progressDialog != null) {
            progressDialog.setMessage("加载中");
            progressDialog.show();
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    // 调用sdk注册方法
                    String phone = model.getMobile();
                    final String account = MD5.md5WithEncoder(phone).toLowerCase();
                    EMChatManager.getInstance().createAccountOnServer(account, "HBL_SOFTTEK#321");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!ValidateCertificationActivity.this.isFinishing()) {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                    progressDialog = null;
                                }
                            }
                            loginPresenter.updateHXState(model.getMobile(), account, "1", progressDialog, null,"noInBack");
                            finish();
                        }
                    });
                } catch (final EaseMobException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!ValidateCertificationActivity.this.isFinishing()) {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                    progressDialog = null;
                                }
                            }
                            int errorCode = e.getErrorCode();
                            String msg = "";
                            if (errorCode == EMError.USER_ALREADY_EXISTS) {
                                String phone = model.getMobile();
                                final String account = MD5.md5WithEncoder(phone).toLowerCase();
                                loginPresenter.updateHXState(model.getMobile(), account, "1", progressDialog, null,"noInBack");
                            } else {
                                showDialog();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RoleInfo roleInfo) {
        System.out.println("roleInfo:" + roleInfo);
        edit_password.setText("");
        edit_account.setText("");
        setData();
//        progressDialog.setMessage("加载中");
//        String hasEmchat = model.getHasEmchat();
//        if ("1".equals(hasEmchat)) {
//            loginPresenter.updateHXState(model.getMobile(), model.getHXAccountId(), "1", progressDialog, null,"noInBack");
//        } else {
//            rigstHX();
//        }
//        finish();
    }
    @Subscribe
    public void onEvent(Integer i) {
        //showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ValidateCertificationActivity.this)
                .setTitle("认证成功")
                .setMessage("是否需要现在开通会话功能? 开通后您就可以和体管赛中其他小伙伴聊天了。")
                .setCancelable(false)
                .setPositiveButton("开通", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        rigstHX();
                    }
                })
                .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginPresenter.updateHXState(model.getMobile(), "", "0", progressDialog, dialog,"noInBack");
                    }
                });
        dialogBuilder.create().setCancelable(false);
        dialogBuilder.create().show();

    }

    @Override
    protected void initViews() {
        tv_title.setText("身份认证");

    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
        setData();
        edit_password.setText("");
        edit_account.setText("");
    }

    private void setData() {
        model = UserInfoModel.getInstance().getUser();
        if (model.getCertTime() == null || "".equals(model.getCertTime())) {
            text_time.setText("");
        } else {
            text_time.setText("(上次认证时间：" + model.getCertTime().split(" ")[0] + ")");
        }
        String userrole = model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {
            text_value.setText("游客");
        } else if (String.valueOf(Constants.INC).equals(userrole)) {
            text_value.setText("受邀普通顾客");
        } else if (String.valueOf(Constants.SP).equals(userrole)) {
            text_value.setText("顾问");
        } else if (String.valueOf(Constants.SR).equals(userrole)) {
            text_value.setText("助教");
        } else if (String.valueOf(Constants.PC).equals(userrole)) {
            text_value.setText("高级顾客");
        } else if (String.valueOf(Constants.NC).equals(userrole)) {
            text_value.setText("普通顾客");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_validate:
                validateLife.validate();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {
        String account = model.getUserid().toString();
        String password = edit_password.getText().toString();
        String memberId = edit_account.getText().toString();
        System.out.println("account:" + account + "   password:" + password + " memberId:" + memberId);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("认证中");
        progressDialog.show();
        loginPresenter.alidateCertification(memberId, password, account, progressDialog);
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
