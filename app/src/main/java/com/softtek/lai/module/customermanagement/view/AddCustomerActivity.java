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
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.string.phoneNum;

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
        Intent intent = new Intent(AddCustomerActivity.this, NewCustomerActivity.class);
        intent.putExtra("mobile", phoneNum);
//        intent.putExtra("model", null);
        startActivity(intent);

//        CustomerService service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
//        service.findCustomerByMobile(UserInfoModel.getInstance().getToken(), phoneNum, new RequestCallback<ResponseData<FindCustomerModel>>() {
//            @Override
//            public void success(ResponseData<FindCustomerModel> responseData, Response response) {
//                int status = responseData.getStatus();
//                if (200 == status) {
//                    FindCustomerModel model = responseData.getData();
//                    Intent intent = new Intent(AddCustomerActivity.this, NewCustomerActivity.class);
//                    intent.putExtra("mobile", phoneNum);
//                    intent.putExtra("model", model);
//                    startActivity(intent);
//                } else {
//                    Util.toastMsg(responseData.getMsg());
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                super.failure(error);
//            }
//        });


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }
}
