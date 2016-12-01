package com.softtek.lai.module.message2.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
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

    @Required(order=1,message = "请填写手机号")
    @Regex(order = 2, patternResId = R.string.phonePattern, messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;


    @Override
    protected void initViews() {
        tv_title.setText("添加爱心学员");
        et_value.setHint("输入爱心学员手机号");
        et_value.setInputType(InputType.TYPE_CLASS_PHONE);


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

    }


    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
