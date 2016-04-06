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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.File.model.FilterModel;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;
import com.softtek.lai.module.home.view.HomeActviity;

import java.util.Calendar;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;


@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, View.OnTouchListener {
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
    EditText tv_sex;

    @Required(order = 4, message = "请选择身高")
    @InjectView(R.id.tv_height)
    EditText tv_height;

    @Required(order = 5, message = "请选择体重")
    @InjectView(R.id.tv_weight)
    EditText tv_weight;

    //添加身体围度按钮
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;
    //完成按钮
    @InjectView(R.id.btn_finish)
    Button btn_finish;


    @InjectView(R.id.ll_nickname)
    ViewGroup ll_nickname;

    @InjectView(R.id.ll_birth)
    ViewGroup ll_birth;

    @InjectView(R.id.ll_sex)
    ViewGroup ll_sex;

    @InjectView(R.id.ll_height)
    ViewGroup ll_height;

    @InjectView(R.id.ll_weight)
    ViewGroup ll_weight;

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

    //获取当前日期
    Calendar ca = Calendar.getInstance();
    int myear = ca.get(Calendar.YEAR);//获取年份
    int mmonth = ca.get(Calendar.MONTH);//获取月份
    int mday = ca.get(Calendar.DATE);//获取日

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tv_birth.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        tv_birth.setFocusable(false);
//                        show_birth_dialog();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//
//                        tv_birth.setFocusable(true);
//                        break;
//                }
//                return false;
//            }
//        });

        ll_birth.setOnTouchListener(this);
//        tv_sex.setOnClickListener(this);
        ll_sex.setOnTouchListener(this);

//        tv_height.setOnClickListener(this);
        ll_height.setOnTouchListener(this);

//        tv_weight.setOnClickListener(this);
        ll_weight.setOnTouchListener(this);

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
        tv_right.setTextSize(16);
        tv_right.setPadding(0,0,25,0);
        tv_right.setGravity(Gravity.CENTER);
        file = new FileModel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
//                String nick = et_nickname.getText().toString();
//                if (LaiApplication.getInstance().getFilterList().contains(new FilterModel(nick))) {
//                    et_nickname.setError("该昵称不合法");
////                    Util.toastMsg("该昵称不合法");
//                } else {
//                    validateLife.validate();
//                }
                validateLife.validate();
                break;
            case R.id.btn_Add_bodydimension:
                Intent intent = new Intent(CreatFlleActivity.this, DimensionRecordActivity.class);
                intent.putExtra("file", file);
                startActivityForResult(intent, GET_BODY_DIMENSION);
                w = false;
                break;
//            case R.id.ll_sex:
//
//            case R.id.tv_sex:
//                show_sex_dialog();
//                break;
//            case R.id.ll_height:
//
//            case R.id.tv_height:
//                show_height_dialog();
//                break;
//            case R.id.ll_weight:
//
//            case R.id.tv_weight:
//                show_weight_dialog();
//                break;
            case R.id.tv_right:
                Intent intent1 = new Intent(CreatFlleActivity.this, HomeActviity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.btn_finish:
//                    String nick = et_nickname.getText().toString();
//                    if (LaiApplication.getInstance().getFilterList().contains(new FilterModel(nick))) {
//                        et_nickname.setError("该昵称不合法");
//                    } else {
//                        validateLife.validate();
//                    }
                    validateLife.validate();
                    break;
                case R.id.btn_Add_bodydimension:
                    Intent intent = new Intent(CreatFlleActivity.this, DimensionRecordActivity.class);
                    intent.putExtra("file", file);
                    startActivityForResult(intent, GET_BODY_DIMENSION);
                    w = false;
                    break;
                case R.id.ll_birth:
                    show_birth_dialog();
                    break;
                case R.id.ll_sex:

                case R.id.tv_sex:
                    show_sex_dialog();
                    break;
                case R.id.ll_height:

                case R.id.tv_height:
                    show_height_dialog();
                    break;
                case R.id.ll_weight:

                case R.id.tv_weight:
                    show_weight_dialog();
                    break;
                case R.id.tv_right:
                    Intent intent1 = new Intent(CreatFlleActivity.this, HomeActviity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
        return true;
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


    //生日对话框
//    public void show_birth_dialog() {
//        DatePickerDialog dialog = new DatePickerDialog(
//                CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                if (year > myear) {
//                    show_warn_dialog();
//                }
//                if (year == myear && month > mmonth) {
//                    show_warn_dialog();
//                }
//                if (year == myear && month == mmonth && day > mday) {
//                    show_warn_dialog();
//                } else {
//                    tv_birth.setText(year + "-" + (month + 1) + "-" + day);
//                    tv_birth.setError(null);
//                }
//            }
//        }, myear, mmonth, mday);
//        dialog.setTitle("");
//        //dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        dialog.show();
//    }

    public void show_birth_dialog() {
        final Dialog birth_dialog = new Dialog(CreatFlleActivity.this);
        birth_dialog.setTitle("选择生日(年-月-日)");
        birth_dialog.setContentView(R.layout.birth_dialog);
        Button b1 = (Button) birth_dialog.findViewById(R.id.button1);
//      Button b2 = (Button) height_dialog.findViewById(R.id.button2);
        final NumberPicker np1 = (NumberPicker) birth_dialog.findViewById(R.id.numberPicker1);
        np1.setMaxValue(myear);
        np1.setValue(1960);//
        np1.setMinValue(1900);
        np1.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) birth_dialog.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(6);
        np2.setMinValue(1);
        np2.setWrapSelectorWheel(false);

        final NumberPicker np3 = (NumberPicker) birth_dialog.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(15);
        np3.setMinValue(1);
        np3.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (np1.getValue()==myear&&np2.getValue()>(mmonth+1)) {
                    birth_dialog.dismiss();
                    show_warn_dialog();
                }
                if (np1.getValue() == myear && np2.getValue() == (mmonth+1) && np3.getValue() > mday) {
                    birth_dialog.dismiss();
                    show_warn_dialog();
                } else {
                    tv_birth.setText(String.valueOf(np1.getValue()) + "-" + String.valueOf(np2.getValue()) + "-" + String.valueOf(np3.getValue()));
                    tv_birth.setError(null);
                    birth_dialog.dismiss();
                }
            }
        });
//        b2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                height_dialog.dismiss(); // dismiss the dialog
//            }
//        });
//        birth_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        birth_dialog.show();



//        DatePickerDialog dialog = new DatePickerDialog(
//                CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                if (year > myear) {
//                    show_warn_dialog();
//                }
//                if (year == myear && month > mmonth) {
//                    show_warn_dialog();
//                }
//                if (year == myear && month == mmonth && day > mday) {
//                    show_warn_dialog();
//                } else {
//                    tv_birth.setText(year + "-" + (month + 1) + "-" + day);
//                    tv_birth.setError(null);
//                }
//            }
//        }, myear, mmonth, mday);
//        dialog.setTitle("");
//        //dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        dialog.show();
   }



    //生日警告对话框
    public void show_warn_dialog() {
        Dialog dialog = new AlertDialog.Builder(CreatFlleActivity.this)
                .setMessage("生日不能大于当前日期,请重新选择")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                show_birth_dialog();
                            }
                        }).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
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
                                tv_sex.setError(null);
                            }
                        })
//                .setNegativeButton("取消",null)
                .setPositiveButton("完成", null)
                .create();
        //genderdialog.setCanceledOnTouchOutside(false);  // 设置点击屏幕Dialog不消失
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
                tv_height.setError(null);
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
        //height_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
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
                if (np.getValue() < 80) {
                    Dialog dialog = new AlertDialog.Builder(CreatFlleActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                                            tv_weight.setError(null);
                                        }
                                    })

                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                    tv_weight.setError(null);
                }
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
        //weight_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        weight_dialog.show();
    }

}
