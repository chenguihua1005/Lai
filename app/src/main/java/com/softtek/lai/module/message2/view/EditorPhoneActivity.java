package com.softtek.lai.module.message2.view;

import android.content.Intent;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.AiXinStudent;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_editor_text)
public class EditorPhoneActivity extends BaseActivity implements Validator.ValidationListener{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Required(order=1,message = "请填写手机号")
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;

    private String classId;

    @Override
    protected void initViews() {
        tv_title.setText("添加爱心学员");
        et_value.setHint("输入爱心学员手机号");
        tv_right.setText("确认");
        String value=getIntent().getStringExtra("aixin");
        et_value.setText(value);
        et_value.setInputType(InputType.TYPE_CLASS_PHONE);
        fl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftInputUtil.hidden(EditorPhoneActivity.this);
                validateLife.validate();
            }
        });

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_value.setText("");
            }
        });
        et_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEND){
                    SoftInputUtil.hidden(EditorPhoneActivity.this);
                    validateLife.validate();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initDatas() {
        classId=getIntent().getStringExtra("classId");
    }


    @Override
    public void onValidationSucceeded() {
        dialogShow("验证手机号");
        final String phone=et_value.getText().toString();
        ZillaApi.NormalRestAdapter.create(Message2Service.class)
                .validatePhone(UserInfoModel.getInstance().getToken(),
                        phone,
                        classId,
                        new RequestCallback<ResponseData<AiXinStudent>>() {
                            @Override
                            public void success(ResponseData<AiXinStudent> data, Response response) {
                                dialogDissmiss();
                                if(data.getStatus()==200){
                                    Intent intent=getIntent();
                                    intent.putExtra("accountId",data.getData().AccountId);
                                    intent.putExtra("phone",phone);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }else{
                                    et_value.setError(data.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        });
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
