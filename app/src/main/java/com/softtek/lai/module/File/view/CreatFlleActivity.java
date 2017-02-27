/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.FileModel;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;


@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, View.OnTouchListener {
    private List<String> gradeList = new ArrayList<>();
    private List<String> gradeIDList = new ArrayList<>();
    private String select_grade = "";
    private ICreateFilepresenter ICreateFilepresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, message = "请输入姓名")
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

    //跳过按钮
    @InjectView(R.id.iv_left)
    ImageView iv_left;

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
        ll_birth.setOnTouchListener(this);
        ll_sex.setOnTouchListener(this);
        ll_height.setOnTouchListener(this);
        ll_weight.setOnTouchListener(this);
        btn_finish.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        iv_left.setVisibility(View.GONE);
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void initDatas() {
        ICreateFilepresenter = new CreateFileImpl(this);
        tv_title.setText("我的档案");
        file = new FileModel();
        addGrade();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                validateLife.validate();
                break;
            case R.id.btn_Add_bodydimension:
                Intent intent = new Intent(CreatFlleActivity.this, DimensionRecordActivity.class);
                intent.putExtra("file", file);
                startActivityForResult(intent, GET_BODY_DIMENSION);
                w = false;
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.btn_finish:
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
                    showGradeDialog();
                    break;
                case R.id.ll_height:

                case R.id.tv_height:
                    show_height_dialog();
                    break;
                case R.id.ll_weight:

                case R.id.tv_weight:
                    show_weight_dialog();
                    break;
            }
        }
        return true;
    }

    private boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0;
    }

    private int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    @Override
    public void onValidationSucceeded() {
        String nick = et_nickname.getText().toString().trim();
        String birthday = tv_birth.getText().toString();
        String gender = tv_sex.getText().toString();
        String height = tv_height.getText().toString();
        String weight = tv_weight.getText().toString();

        if (length(nick) > 12) {
            Util.toastMsg("姓名不能超过6个汉字");
        } else {

            if (w == true) {
                file = new FileModel();
            }

            file.setNickname(nick);
            file.setBirthday(birthday);
            file.setGender(gender.equals("男") ? 0 : 1);

            String heights = height.split("cm")[0];
            file.setHeight(Double.parseDouble(heights));
            String weights = weight.split("斤")[0];
            file.setWeight(Double.parseDouble(weights));

            String token = getIntent().getStringExtra("token");
            ICreateFilepresenter.createFile(token, file);
        }
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

        }
    }


    public void show_birth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.birth_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np1.setMaxValue(myear);
        np1.setValue(1990);
        np1.setMinValue(1900);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(mmonth + 1);
        np2.setMinValue(1);
        np2.setWrapSelectorWheel(false);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(mday);
        np3.setMinValue(1);
        np3.setWrapSelectorWheel(false);
        np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        birdialog.setTitle("选择生日(年-月-日)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (np1.getValue() == myear && np2.getValue() > (mmonth + 1)) {
                    show_warn_dialog();
                }
                if (np1.getValue() == myear && np2.getValue() == (mmonth + 1) && np3.getValue() > mday) {
                    show_warn_dialog();
                } else {
                    tv_birth.setText(String.valueOf(np1.getValue()) + "-" + String.valueOf(np2.getValue()) + "-" + String.valueOf(np3.getValue()));
                    tv_birth.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    //生日警告对话框
    public void show_warn_dialog() {
        Dialog dialog = new AlertDialog.Builder(this)
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

    //身高对话框
    public void show_height_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(240);
        if (tv_sex.getText().toString().equals("男")) {
            np1.setValue(170);
        } else if (tv_sex.getText().toString().equals("女")) {
            np1.setValue(155);
        } else {
            np1.setValue(155);
        }
        np1.setMinValue(100);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择身高(单位：cm)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_height.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "cm");
                tv_height.setError(null);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    //体重对话框
    public void show_weight_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(600);
        if (tv_sex.getText().toString().equals("男")) {
            np1.setValue(150);
        } else if (tv_sex.getText().toString().equals("女")) {
            np1.setValue(100);
        } else {
            np1.setValue(100);
        }
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np1.getValue() < 80) {
                    Dialog dialog1 = new AlertDialog.Builder(CreatFlleActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "斤");
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
                    dialog1.show();
                    dialog1.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()) + "斤");
                    tv_weight.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    private void addGrade() {
        gradeList.add("女");
        gradeList.add("男");
        gradeIDList.add("1");
        gradeIDList.add("0");
    }


    public void showGradeDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_grade, null);
        final WheelView wheel_grade = (WheelView) view.findViewById(R.id.wheel_grade);
        wheel_grade.setOffset(1);
        wheel_grade.setItems(gradeList);
        wheel_grade.setSeletion(0);
        select_grade = "";
        wheel_grade.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_grade = item;
            }
        });
        birdialog.setTitle("选择性别").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_grade)) {
                            select_grade = gradeList.get(0);
                        }
                        tv_sex.setText(select_grade);
                        tv_sex.setError(null);
                        select_grade = "";
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create()
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
