package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.StringUtil;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_editor_text)
public class EditorTextActivity extends BaseActivity implements Validator.ValidationListener{

    public static final int UPDATE_CLASS_NAME=1;
    public static final int UPDATE_GROUP_NAME=2;
    public static final int ADD_GROUP_NAME=3;
    public static final int ADD_CLASS_MAIL=4;

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Required(order=1)
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;

    private int flag;
    private List<String> groups;
    @Override
    protected void initViews() {
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        switch (flag){
            case UPDATE_CLASS_NAME:
                tv_title.setText("班级名称");
                et_value.setHint("班级名称");
                et_value.setText(intent.getStringExtra("name"));
                et_value.setMaxEms(10);
                Editable etext=et_value.getText();
                Selection.setSelection(etext,etext.length());
                break;
            case UPDATE_GROUP_NAME:
                tv_title.setText("小组名称");
                et_value.setHint("小组名称");
                et_value.setText(intent.getStringExtra("name"));
                groups=intent.getStringArrayListExtra("groups");
                Editable etext1=et_value.getText();
                Selection.setSelection(etext1,etext1.length());
                break;
            case ADD_GROUP_NAME:
                tv_title.setText("添加新小组");
                et_value.setHint("小组名称");
                groups=intent.getStringArrayListExtra("groups");
                break;
            case ADD_CLASS_MAIL:
                tv_right.setText("添加班级邮箱");
                et_value.setHint("班级邮箱");
                break;
        }
        tv_right.setText("确定");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLife.validate();

            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_value.setText("");
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onValidationSucceeded() {
        if (StringUtil.length(et_value.getText().toString())>24){
            String message="";
            switch (flag){
                case UPDATE_CLASS_NAME:
                    message="班级名称不能超过12个汉字";
                    break;
                case ADD_GROUP_NAME:
                case UPDATE_GROUP_NAME:
                    message="小组名称不能超过12个汉字";
                    break;
            }
            et_value.requestFocus();
            et_value.setError(Html.fromHtml("<font color=#FFFFFF>" + message + "</font>"));
        }else {
            if(flag==UPDATE_GROUP_NAME){
                if(groups.size()!=1&&groups.contains(et_value.getText().toString().trim())){
                    et_value.requestFocus();
                    et_value.setError(Html.fromHtml("<font color=#FFFFFF>小组名称已存在</font>"));
                    return;
                }
            }else if(flag==ADD_GROUP_NAME){
                if(groups.contains(et_value.getText().toString().trim())){
                    et_value.requestFocus();
                    et_value.setError(Html.fromHtml("<font color=#FFFFFF>小组名称已存在</font>"));
                    return;
                }
            }
            Intent back=getIntent();
            back.putExtra("value",et_value.getText().toString());
            setResult(RESULT_OK,back);
            finish();
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message="";
        switch (flag){
            case UPDATE_CLASS_NAME:
                message="请输入班级名称";
                break;
            case ADD_GROUP_NAME:
            case UPDATE_GROUP_NAME:
                message="请输入小组名称";
                break;
            case ADD_CLASS_MAIL:
                message = "请输入班级邮箱";
        }
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(Html.fromHtml("<font color=#FFFFFF>" + message + "</font>"));
        }
    }
}
