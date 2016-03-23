package com.softtek.lai.module.newmemberentry.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.DimensionRecordActivity;
import com.softtek.lai.module.newmemberentry.view.model.Newstudents;
import com.softtek.lai.module.newmemberentry.view.presenter.INewStudentpresenter;
import com.softtek.lai.module.newmemberentry.view.presenter.NewStudentInputImpl;

import java.io.File;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_member_entry)
public class EntryActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener {
    private String SexData[] = {"男","女"};//性别数据
    private String classidDate[]={ "参赛班级1", "参赛班级2", "参赛班级3", "参赛班级4", "参赛班级5", "参赛班级6",
            "参赛班级7", "参赛班级8", "参赛班级9", "参赛班级10", "参赛班级11", "参赛班级12", "参赛班级13", "参赛班级14", "参赛班级15" };

    private INewStudentpresenter INewStudentpresenter;
    //toolbar
    @InjectView(R.id.tv_title)
    TextView tv_title;
    //返回
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    //确定
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.ll_birthday)
    LinearLayout ll_birthday;

    @InjectView(R.id.ll_gender)
    LinearLayout ll_gender;

    //参赛班级
    @InjectView(R.id.ll_classid)
    LinearLayout ll_classid;

    //表单验证
    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1,message = "昵称必填项")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    //资格证号
    @InjectView(R.id.et_certification)
    EditText et_certification;

    @Required(order = 2,message = "手机号码必填项")
    @Regex(order = 3,patternResId = R.string.phonePattern,messageResId = R.string.phoneValidate)
    @InjectView(R.id.et_mobile)
    EditText et_mobile;

    @Required(order = 4,message = "参赛班级必填项")
    @InjectView(R.id.et_classid)
    EditText et_classid;

    @Required(order = 5,message = "初始体重必填项")
    @InjectView(R.id.et_weight)
    EditText et_weight;

    //体脂
    @InjectView(R.id.et_pysical)
    EditText et_pysical;
    //内脂
    @InjectView(R.id.et_fat)
    EditText et_fat;

    @Required(order = 6,message = "生日必填项")
    @InjectView(R.id.et_birthday)
    EditText et_birthday;

    @Required(order = 7,message = "性别必填项")
    @InjectView(R.id.et_gender)
    EditText et_gender;

    //照片上传
    @InjectView(R.id.tv_photoupload)
    TextView tv_photoupload;

    @InjectView(R.id.img1)
    ImageView img1;

    //添加身体围度
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    //照片上传
    StringBuffer path=new StringBuffer();
    private static final int PHOTO=1;

    private Newstudents newstudents;//存储用户表单数据
    private static final int GET_BODY_DIMENSION=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //返回按钮
        ll_left.setOnClickListener(this);
        //确定按钮
        tv_right.setOnClickListener(this);
        //照片上传
        tv_photoupload.setOnClickListener(this);
        //添加身体围度
        btn_Add_bodydimension.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_classid.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        INewStudentpresenter = new NewStudentInputImpl(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("新学员录入");
        //tv_left.setBackground(null);
        tv_right.setText("确定");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //确定按钮
            case R.id.tv_right:
                validateLife.validate();

                break;
            case R.id.btn_Add_bodydimension:
                Intent intent1=new Intent(EntryActivity.this,DimensionRecordActivity.class);
                intent1.putExtra("newstudents",newstudents);
                startActivityForResult(intent1,GET_BODY_DIMENSION);
                break;
            case R.id.tv_photoupload:
                takepic();

                break;
            case R.id.ll_birthday:
                show_birth_dialog();
                break;
            case R.id.ll_gender:
                show_sex_dialog();
                break;
            case R.id.ll_classid:
                show_participating_dialog();
                break;
        }
    }

    public void takepic() {
        path.append(Environment.getExternalStorageDirectory().getPath()).append("/123.jpg");
        File file=new File(path.toString());
        Uri uri= Uri.fromFile(file);
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK&&resultCode==PHOTO){
            Bitmap bm= BitmapFactory.decodeFile(path.toString());
            img1.setImageBitmap(bm);
        }
        //身体围度值传递
        if (requestCode==Activity.RESULT_OK&&resultCode==GET_BODY_DIMENSION){
            newstudents= (Newstudents) data.getSerializableExtra("newstudents");
            Log.i("身体围度值传递"+newstudents.toString());
        }
    }

    @Override
    public void onValidationSucceeded() {
        String nickname=et_nickname.getText().toString();
        String certification=et_certification.getText().toString();
        String mobile=et_mobile.getText().toString();
        String classid=et_classid.getText().toString();
        String weight=et_weight.getText().toString();
        String pysical=et_pysical.getText().toString();
        String fat=et_fat.getText().toString();
        String birthday=et_birthday.getText().toString();
        String gender=et_gender.getText().toString();
        Log.i("nickname:"+nickname+";certification:"+certification+";mobile:"+mobile+";classid:"+classid+";weight:"+weight+"pysical:"+pysical+"fat:"+fat+"birthday:"+birthday+"gender:"+gender);
        Log.i("newstudents------"+newstudents);
        newstudents.setNickname(nickname);
        newstudents.setCertification(certification);
        newstudents.setMobile(mobile);
        newstudents.setClassid(classid);
        newstudents.setWeight(weight);
        newstudents.setPysical(pysical);
        newstudents.setFat(fat);
        newstudents.setBirthday(birthday);
        newstudents.setGender(gender);
        INewStudentpresenter.input(newstudents);

//        newstudents.setNickname("name");
//        newstudents.setCertification("1234");
//        newstudents.setMobile("18206182087");
//        newstudents.setClassid("id1");
//        newstudents.setWeight("95");
//        newstudents.setPysical("54");
//        newstudents.setFat("60");
//        newstudents.setBirthday("2014-01-12");
//        newstudents.setGender("男");
//        INewStudentpresenter.input(newstudents);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }

    //所在班级
    public void show_participating_dialog() {
        Dialog dialog = new AlertDialog.Builder(EntryActivity.this)
                .setTitle("请选择参赛班级")
                .setNegativeButton("取消", null)
                .setItems(classidDate,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                et_classid.setText(classidDate[which]);
                            }
                        }).create();
        dialog.show();
    }
    //生日对话框
    public void show_birth_dialog(){
        DatePickerDialog dialog=new DatePickerDialog(
                EntryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_birthday.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        },2016,8,17);
        dialog.setTitle("");
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.show();
    }

    //性别对话框
    public void show_sex_dialog(){
        Dialog genderdialog=new AlertDialog.Builder(EntryActivity.this)
                .setTitle("请选择您的性别")
                .setSingleChoiceItems(SexData,2,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_gender.setText(SexData[which]);
                            }
                        })
                .setNegativeButton("取消",null)
                .setPositiveButton("确认",null)
                .create();
        genderdialog.setCanceledOnTouchOutside(false);  // 设置点击屏幕Dialog不消失
        genderdialog.show();
    }
}
