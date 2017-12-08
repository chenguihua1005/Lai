package com.softtek.lai.module.customermanagement.view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.model.FindCustomerModel;
import com.softtek.lai.module.customermanagement.model.SituationOfMobileModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.string.phoneNum;
import static java.security.AccessController.getContext;

/**
 * Created by jessica.zhang on 11/17/2017.
 */

@InjectLayout(R.layout.activity_add_customer)
public class AddCustomerActivity extends BaseActivity implements View.OnClickListener, ValidationListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Required(order = 1, messageResId = R.string.phoneValidateNull)
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @LifeCircleInject
    ValidateLife validateLife;

    @Override
    protected void initViews() {
        tv_title.setText("添加客户");
        tv_right.setText("下一步");
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                validateLife.validate();
                break;


        }
    }

    @Override
    public void onValidationSucceeded() {
        final String phoneNum = et_phone.getText().toString().trim();

        CustomerService service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
        service.getSituationOfTheMobile(UserInfoModel.getInstance().getToken(), phoneNum,"", new Callback<ResponseData<SituationOfMobileModel>>() {
            @Override
            public void success(ResponseData<SituationOfMobileModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    SituationOfMobileModel model = responseData.getData();
                    boolean IsLocked = model.isLocked();//是否被锁定，true-是，false-否
                    boolean IsDownline = model.isDownline();//是否是下线，true-是，false-否
                    boolean IsRegistered = model.isRegistered();//是否注册，true-是，false-否
                    boolean IsInMyClub = model.isInMyClub();//是否是本俱乐部客户，true-是，false-否

//                    如果已经被锁定且为本人下级直接跳转到个人详情页
                    if (IsLocked && IsDownline) {
                        Intent intent = new Intent(AddCustomerActivity.this, CustomerDetailActivity.class);
                        startActivity(intent);
                    } else if (IsLocked && !IsDownline) {//如果已经被锁定且不是本人下级，提示添加失败
                        Util.toastMsg(responseData.getMsg());
                    } else if (IsInMyClub) {//如果已经被本俱乐部录为线索，跳转至个人详情页
                        Intent intent = new Intent(AddCustomerActivity.this, CustomerDetailActivity.class);
                        startActivity(intent);
                    } else if (IsRegistered && !IsLocked) {//如果已经注册账号但未被锁定，下一步时在新客户页面关联档案信息，可添加备注信息
                        Intent intent = new Intent(AddCustomerActivity.this, NewCustomerActivity.class);
                        intent.putExtra("mobile", phoneNum);
                        startActivity(intent);
                    } else if (!IsRegistered) {//如果没有注册跳转到新客户页面
                        Intent intent = new Intent(AddCustomerActivity.this, NewCustomerActivity.class);
                        intent.putExtra("mobile", phoneNum);
                        startActivity(intent);
                    }
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
            }
        });

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }
}
