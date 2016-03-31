/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import butterknife.InjectView;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.File.model.FilterModel;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;
import com.softtek.lai.module.home.view.HomeActviity;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {
    private String SexData[] = {"男", "女"};//性别数据
    private int gender = 0;
    private ICreateFilepresenter ICreateFilepresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, message = "请输入昵称")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order = 2, message = "请选择生日")
    @InjectView(R.id.tv_birth)
    EditText tv_birth;

    @Required(order = 3, message = "请选择性别")
    @InjectView(R.id.tv_sex)
    TextView tv_sex;

    @Required(order = 4, message = "请选择身高")
    @InjectView(R.id.tv_height)
    TextView tv_height;

    @Required(order = 5, message = "请选择体重")
    @InjectView(R.id.tv_weight)
    TextView tv_weight;

    //添加身体围度按钮
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;
    //完成按钮
    @InjectView(R.id.btn_finish)
    Button btn_finish;


    @InjectView(R.id.ll_nickname)
    LinearLayout ll_nickname;

    @InjectView(R.id.ll_birth)
    LinearLayout ll_birth;

    @InjectView(R.id.ll_sex)
    LinearLayout ll_sex;

    @InjectView(R.id.ll_height)
    LinearLayout ll_height;

    @InjectView(R.id.ll_weight)
    LinearLayout ll_weight;

    //toolbar
    //标题
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    //跳过按钮
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //存储用户表单数据
    private FileModel file;
    private static final int GET_BODY_DIMENSION = 1;

    private boolean w = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_birth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        tv_birth.setFocusable(false);
                        DatePickerDialog dialog = new DatePickerDialog(
                                CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_birth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, 2016, 8, 17);
                        dialog.setTitle("");
                        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                        dialog.show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:

                        tv_birth.setFocusable(true);
                        break;
                }
                return false;
            }
        });
        ll_sex.setOnClickListener(this);
        ll_height.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ICreateFilepresenter = new CreateFileImpl(this);
        tv_title.setText("我的档案");
        tv_left.setBackground(null);
        tv_right.setText("跳过");
        file = new FileModel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                String nick = et_nickname.getText().toString();
                if (LaiApplication.getInstance().getFilterList().contains(new FilterModel(nick))) {
                    Util.toastMsg("该昵称不合法");
                } else {
                    validateLife.validate();
                }
                break;
            case R.id.btn_Add_bodydimension:
                Intent intent = new Intent(CreatFlleActivity.this, DimensionRecordActivity.class);
                intent.putExtra("file", file);
                startActivityForResult(intent, GET_BODY_DIMENSION);
                w = false;
                break;
            case R.id.ll_birth:
                DatePickerDialog dialog = new DatePickerDialog(
                        CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_birth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, 2016, 8, 17);
                dialog.setTitle("");
                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                dialog.show();
                break;
            case R.id.ll_sex:
                show_sex_dialog();
                break;
            case R.id.ll_height:
                show_height_dialog();
                break;
            case R.id.ll_weight:
                show_weight_dialog();
                break;
            case R.id.tv_right:
                Intent intent1 = new Intent(CreatFlleActivity.this, HomeActviity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        String nick = et_nickname.getText().toString();
        String birthday = tv_birth.getText().toString();
        String gender = tv_sex.getText().toString();
        String height = tv_height.getText().toString();
        String weight = tv_weight.getText().toString();
        Log.i("创建档案：" + "nick:" + nick + ";birthday:" + birthday + ";gender:" + gender + ";height:" + height + ";weight:" + weight);
        if (w == true) {
            file = new FileModel();
        }
        Log.i("file:--------------" + file);
        file.setNickname(nick);
        file.setBrithday(birthday);
        file.setGender(gender.equals("女") ? 0 : 1);
        file.setHeight(Integer.parseInt(height));
        file.setWeight(Integer.parseInt(weight));
        Log.i("file>>>>>>>>>>>>>>>>>>>" + file);
        String token = SharedPreferenceService.getInstance().get("token", "");
        ICreateFilepresenter.createFile(token, file);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    //身体围度值传递
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GET_BODY_DIMENSION) {
            file = (FileModel) data.getSerializableExtra("file");
            Log.i("创建档案围度file:" + file);
        }
    }

    //性别对话框
    public void show_sex_dialog() {
        Dialog genderdialog = new AlertDialog.Builder(CreatFlleActivity.this)
                .setTitle("请选择您的性别")
                .setSingleChoiceItems(SexData, 2,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_sex.setText(SexData[which]);
                            }
                        })
//                .setNegativeButton("取消",null)
                .setPositiveButton("完成", null)
                .create();
        genderdialog.setCanceledOnTouchOutside(false);  // 设置点击屏幕Dialog不消失
        genderdialog.show();
    }

    //身高对话框
    public void show_height_dialog() {
        final Dialog height_dialog = new Dialog(CreatFlleActivity.this);
        height_dialog.setTitle("选择身高(单位：cm)");
        height_dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) height_dialog.findViewById(R.id.button1);
//        Button b2 = (Button) height_dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) height_dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setValue(155);
        np.setMinValue(50);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_height.setText(String.valueOf(np.getValue())); //set the value to textview
                height_dialog.dismiss();
            }
        });
//        b2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                height_dialog.dismiss(); // dismiss the dialog
//            }
//        });
        height_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        height_dialog.show();
    }

    //体重对话框
    public void show_weight_dialog() {
        final Dialog weight_dialog = new Dialog(CreatFlleActivity.this);
        weight_dialog.setTitle("选择体重(单位：斤)");
        weight_dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) weight_dialog.findViewById(R.id.button1);
//        Button b2 = (Button) weight_dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) weight_dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setValue(100);
        np.setMinValue(20);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                weight_dialog.dismiss();
            }
        });
//        b2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                weight_dialog.dismiss(); // dismiss the dialog
//            }
//        });
        weight_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        weight_dialog.show();
    }
}
