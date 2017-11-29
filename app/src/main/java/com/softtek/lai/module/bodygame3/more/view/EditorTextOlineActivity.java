package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
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
public class EditorTextOlineActivity extends BaseActivity implements Validator.ValidationListener {

    public static final int UPDATE_CLASS_NAME = 1;
    public static final int UPDATE_GROUP_NAME = 2;
    public static final int ADD_GROUP_NAME = 3;
    public static final int Edit_AIXIN_PHONE = 4;

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

    @Required(order = 1)
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;

    private int flag;
    private String classId;
    private String classHXId;

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        classId = intent.getStringExtra("classId");
        et_value.setImeOptions(EditorInfo.IME_ACTION_SEND);
        tv_right.setText("确认");
        fl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftInputUtil.hidden(EditorTextOlineActivity.this);
                validateLife.validate();
            }
        });
        switch (flag) {
            case UPDATE_CLASS_NAME:
                tv_title.setText("班级名称");
                et_value.setHint("班级名称");
                et_value.setText(intent.getStringExtra("name"));
                et_value.setMaxEms(10);
                classHXId = intent.getStringExtra("classHxId");
                Editable etext = et_value.getText();
                Selection.setSelection(etext, etext.length());
                break;
            case UPDATE_GROUP_NAME:
                tv_title.setText("小组名称");
                et_value.setHint("小组名称");
                et_value.setText(intent.getStringExtra("name"));
                Editable etext1 = et_value.getText();
                Selection.setSelection(etext1, etext1.length());
                break;
            case ADD_GROUP_NAME:
                tv_title.setText("添加新小组");
                et_value.setHint("小组名称");
                break;
            case Edit_AIXIN_PHONE:
                tv_title.setText("爱心学员");
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
                if (i == EditorInfo.IME_ACTION_SEND) {
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

    @Override
    public void onValidationSucceeded() {
        if (flag == Edit_AIXIN_PHONE) {
            if (et_value.getText().length() != 11) {
                et_value.requestFocus();
                et_value.setError(Html.fromHtml("<font color=#FFFFFF>请输入11位手机号码</font>"));
            }
        } else if (StringUtil.length(et_value.getText().toString()) > 24) {
            String message = "";
            switch (flag) {

                case UPDATE_CLASS_NAME:
                    message = "班级名称不能超过12个汉字";
                    break;
                case ADD_GROUP_NAME:
                case UPDATE_GROUP_NAME:
                    message = "小组名称不能超过12个汉字";
                    break;
            }
            et_value.requestFocus();
            et_value.setError(Html.fromHtml("<font color=#FFFFFF>" + message + "</font>"));
        } else {
            switch (flag) {
                case UPDATE_CLASS_NAME: {
                    final String value = et_value.getText().toString();

                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .updateClassName(classId, UserInfoModel.getInstance().getToken(),
                                    classId,
                                    value,
                                    new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(final ResponseData responseData, Response response) {
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


                    //环信群组名称修改
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
////                            String changedGroupName = value;
//                            try {
//                                EMClient.getInstance().groupManager().changeGroupName(classHXId, value);//需异步处理
//
//                                ZillaApi.NormalRestAdapter.create(MoreService.class)
//                                        .updateClassName(classId,UserInfoModel.getInstance().getToken(),
//                                                classId,
//                                                value,
//                                                new RequestCallback<ResponseData>() {
//                                                    @Override
//                                                    public void success(final ResponseData responseData, Response response) {
//                                                        if (responseData.getStatus() == 200) {
//                                                            Intent back = getIntent();
//                                                            back.putExtra("value", value);
//                                                            setResult(RESULT_OK, back);
//                                                            finish();
//                                                        } else {
//                                                            runOnUiThread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    Util.toastMsg(responseData.getMsg());
//                                                                }
//                                                            });
//
//                                                        }
//                                                    }
//                                                });
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Util.toastMsg("修改班级名称失败！");
//                                    }
//                                });
//                            }
//
//                        }
//                    }).start();
                }
                break;
                case UPDATE_GROUP_NAME: {
                    final String value = et_value.getText().toString();
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .updateGroupName(classId, UserInfoModel.getInstance().getToken(),
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
                            .addGroup(classId, UserInfoModel.getInstance().getToken(),
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
        String message = "";
        switch (flag) {
            case UPDATE_CLASS_NAME:
                message = "请输入班级名称";
                break;
            case ADD_GROUP_NAME:
            case UPDATE_GROUP_NAME:
                message = "请输入小组名称";
                break;
            case Edit_AIXIN_PHONE:
                message = "请输入手机号码";
                break;
        }
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(Html.fromHtml("<font color=#FFFFFF>" + message + "</font>"));
        }
    }
}
