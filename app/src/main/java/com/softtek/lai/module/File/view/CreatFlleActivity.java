package com.softtek.lai.module.File.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;
import com.softtek.lai.module.home.view.HomeActviity;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private String SexData[] = { "男", "女" };//性别数据
    private int gender=0;
    private ICreateFilepresenter ICreateFilepresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1,message = "昵称必填项")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order = 2,message = "生日必填项")
    @InjectView(R.id.tv_birth)
    TextView tv_birth;

    @Required(order = 3,message = "性别必填项")
    @InjectView(R.id.tv_sex)
    TextView tv_sex;

    @Required(order = 4,message = "身高必填项")
    @InjectView(R.id.tv_height)
    TextView tv_height;

    @Required(order = 5,message = "体重必填项")
    @InjectView(R.id.tv_weight)
    TextView tv_weight;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

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
    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_birth.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        ll_height.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        tv_height.setText("170");
        tv_weight.setText("66");
        btn_Add_bodydimension.setOnClickListener(this);
        tv_right.setOnClickListener(this);
   }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ICreateFilepresenter = new CreateFileImpl(this);
        //tv_left.setText("返回");
        tv_left.setBackgroundResource(R.drawable.ale);
        tv_title.setText("创建档案");
        tv_right.setText("跳过");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish:
                validateLife.validate();
                break;
            case R.id.btn_Add_bodydimension:
                startActivity(new Intent(CreatFlleActivity.this,DimensionRecordActivity.class));
                break;
            case R.id.ll_birth:
                DatePickerDialog dialog=new DatePickerDialog(
                        CreatFlleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_birth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                },2014,8,17);
                dialog.setTitle("");
                dialog.show();
                break;
            case R.id.ll_sex:
                Dialog genderdialog=new AlertDialog.Builder(CreatFlleActivity.this)
                        .setTitle("请选择您的性别")
                        .setSingleChoiceItems(SexData,0,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tv_sex.setText(SexData[which]);
                                    }
                                }).setIcon(R.mipmap.ic_launcher)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认",null).create();
                genderdialog.show();
                break;
            case R.id.ll_height:

                break;
            case R.id.tv_right:
                startActivity(new Intent(CreatFlleActivity.this,HomeActviity.class));
        }
    }


    @Override
    public void onValidationSucceeded() {
        String nick=et_nickname.getText().toString();
        String birthday=tv_birth.getText().toString();
        String gender=tv_sex.getText().toString();
        String height=tv_height.getText().toString();
        String weight=tv_weight.getText().toString();
        Log.i("nick:"+nick+";birthday:"+birthday+";gender:"+gender+";height:"+height+";weight:"+weight);

       /* File file =new File(nickn,bir,se,heig,weigh);
        file.setNickname(nickn);
        file.setBirthday(bir);
        file.setGender(se);
        file.setHeight(heig);
        file.setWeight(weigh);*/


       // String token=SharedPreferenceService.getInstance().get("token","");
        String token="A027ED74A15F155734E66868438AA289";
        ICreateFilepresenter.CreateFile(token,nick,birthday,Integer.parseInt(height),Integer.parseInt(weight),gender.equals("男")?1:0);
//        createFilepresenter.createFile("","",file);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }


//    @Override
//    public void toActivity() {
//        startActivity(new Intent(this, TabMainActivity.class));
//    }
}
