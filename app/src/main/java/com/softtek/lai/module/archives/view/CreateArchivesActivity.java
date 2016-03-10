package com.softtek.lai.module.archives.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_create_archives)
public class CreateArchivesActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    @Required(order = 1,message = "昵称必填项")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order =2,message = "生日必填项")
    @InjectView(R.id.et_birth)
    EditText et_birth;

    @Required(order = 3,message = "性别必填项")
    @InjectView(R.id.et_sex)
    EditText et_sex;

    @Required(order =4,message = "身高必填项")
    @InjectView(R.id.et_height)
    EditText et_height;

    @Required(order = 5,message = "体重必填项")
    @InjectView(R.id.et_weight)
    EditText et_weight;

    @InjectView(R.id.btn_add_bodydimension)
    Button btn_add_bodydimension;

    @InjectView(R.id.btn_finish)
    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_add_bodydimension.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_bodydimension:
                break;
            case R.id.btn_finish:
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
