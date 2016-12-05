package com.softtek.lai.module.bodygame3.more.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.utils.StringUtil;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_editor_text)
public class EditorTextOlineActivity extends BaseActivity implements Validator.ValidationListener{

    public static final int UPDATE_CLASS_NAME=1;
    public static final int UPDATE_GROUP_NAME=2;
    public static final int ADD_GROUP_NAME=3;
    public static final int Edit_AIXIN_PHONE=4;

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @Required(order=1)
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;

    private int flag;
    private String classId;

    @Override
    protected void initViews() {
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        classId=intent.getStringExtra("classId");
        switch (flag){
            case UPDATE_CLASS_NAME:
                tv_title.setText("编辑班级名称");
                et_value.setHint("班级名称");
                et_value.setText(intent.getStringExtra("name"));
                et_value.setMaxEms(10);
                Editable etext=et_value.getText();
                Selection.setSelection(etext,etext.length());
                break;
            case UPDATE_GROUP_NAME:
                tv_title.setText("修改组名");
                et_value.setHint("小组名称");
                et_value.setText(intent.getStringExtra("name"));
                Editable etext1=et_value.getText();
                Selection.setSelection(etext1,etext1.length());
                break;
            case ADD_GROUP_NAME:
                tv_title.setText("添加小组");
                et_value.setHint("小组名称");
                break;
            case Edit_AIXIN_PHONE:
                tv_title.setText("添加爱心学员");
                et_value.setHint("输入爱心学员手机号");
                et_value.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }

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
                    SoftInputUtil.hidden(EditorTextOlineActivity.this);
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

    private void showTip(String msg){
        new AlertDialog.Builder(this).setMessage(msg)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }


    @Override
    public void onValidationSucceeded() {
        if (StringUtil.length(et_value.getText().toString())>12){
            showTip("填写内容必须小于12个字");
        }else {
            switch (flag){
                case UPDATE_CLASS_NAME: {
                    final String value=et_value.getText().toString();
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .updateClassName(UserInfoModel.getInstance().getToken(),
                                    classId,
                                    value,
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            if (responseData.getStatus() == 200) {
                                                Intent back = getIntent();
                                                back.putExtra("value", value);
                                                setResult(RESULT_OK, back);
                                                finish();
                                            } else {
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        }
                                    });
                }
                    break;
                case UPDATE_GROUP_NAME: {
                    final String value = et_value.getText().toString();
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .updateGroupName(UserInfoModel.getInstance().getToken(),
                                    classId,
                                    getIntent().getStringExtra("groupId"),
                                    value,
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            if (responseData.getStatus() == 200) {
                                                Intent back = getIntent();
                                                back.putExtra("value", value);
                                                setResult(RESULT_OK, back);
                                                finish();
                                            } else {
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        }
                                    });
                }
                    break;
                case ADD_GROUP_NAME: {
                    final String value = et_value.getText().toString();
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .addGroup(UserInfoModel.getInstance().getToken(),
                                    classId,
                                    value,
                                    UserInfoModel.getInstance().getUserId(),
                                    new RequestCallback<ResponseData<ClassGroup>>() {
                                        @Override
                                        public void success(ResponseData<ClassGroup> responseData, Response response) {
                                            if (responseData.getStatus() == 200) {
                                                Intent back = getIntent();
                                                ClassGroup group = responseData.getData();
                                                group.setCGName(value);
                                                back.putExtra("group", group);
                                                setResult(RESULT_OK, back);
                                                finish();
                                            } else {
                                                Util.toastMsg(responseData.getMsg());
                                            }
                                        }
                                    });
                }
                    break;
            }


        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
//        switch (flag){
//            case UPDATE_CLASS_NAME:
//                showTip("请填写班级名");
//                break;
//            case UPDATE_GROUP_NAME:
//                showTip("请填写组名");
//                break;
//            case ADD_GROUP_NAME:
//                showTip("请填写组名");
//                break;
//        }
        showTip("请填写内容");
    }
}
