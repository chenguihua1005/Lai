package com.softtek.lai.module.File.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.model.File;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.view.RegistActivity;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private String SexData[] = { "男", "女" };//性别数据
    private int gender=0;
    private ICreateFilepresenter ICreateFilepresenter;
    //敏感词
    private String SensitiveWord[]={"康宝来","康葆来","康葆莱","康寳来","康寳莱","Herbalife","贺宝芙","賀寶芙",
            "力腾","LIFTOFF","普莱乐","PROLESSA","奈沃科","NITEWORKS","燃脂美","THERMOJETICS","美纤宝","THERMO-BOND","科迪赛","维迪片","夜新宁","维康宝","CR7","代乐",
            "莱聚","萊聚","莱秤","萊秤","莱表格","萊表格",
            "天使听见爱","Let angels hear love","服务提供商","SP","小康莱了","营养俱乐部",
            "马克休斯","小康康","C罗","克里斯蒂亚诺*罗纳尔多","罗纳尔多","吉米*罗恩","伊格纳罗","沙欣贤",
            "100%","专家"};
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

    //按钮
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

    private File file;//存储用户表单数据
    private static final int GET_BODY_DIMENSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ll_nickname.setOnClickListener(this);
        ll_birth.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        ll_height.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
   }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ICreateFilepresenter = new CreateFileImpl(this);
        file=new File();
        tv_left.setBackgroundResource(R.drawable.back_h);
        tv_title.setText("我的档案");
        tv_right.setText("跳过");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish:
                validateLife.validate();
                break;
            case R.id.btn_Add_bodydimension:
                Intent intent=new Intent(this,DimensionRecordActivity.class);
                intent.putExtra("file",file);
                startActivityForResult(intent,GET_BODY_DIMENSION);
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
                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
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
                genderdialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                genderdialog.show();
                break;
            case R.id.ll_height:
                show_height_dialog();
                break;
            case R.id.ll_weight:
                show_weight_dialog();
                break;
            case R.id.tv_left:
                startActivity(new Intent(CreatFlleActivity.this,RegistActivity.class));
                break;
            case R.id.tv_right:
                startActivity(new Intent(CreatFlleActivity.this,HomeActviity.class));
                break;
        }
    }

    public void show_height_dialog()
    {
        final Dialog height_dialog = new Dialog(CreatFlleActivity.this);
        height_dialog.setTitle("选择身高");
        height_dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) height_dialog.findViewById(R.id.button1);
        Button b2 = (Button) height_dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) height_dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setMinValue(50);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv_height.setText(String.valueOf(np.getValue())); //set the value to textview
                height_dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                height_dialog.dismiss(); // dismiss the dialog
            }
        });
        height_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        height_dialog.show();
    }

    public void show_weight_dialog()
    {
        final Dialog weight_dialog = new Dialog(CreatFlleActivity.this);
        weight_dialog.setTitle("选择体重");
        weight_dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) weight_dialog.findViewById(R.id.button1);
        Button b2 = (Button) weight_dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) weight_dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        np.setMinValue(20);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                weight_dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                weight_dialog.dismiss(); // dismiss the dialog
            }
        });
        weight_dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        weight_dialog.show();
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

        String token= SharedPreferenceService.getInstance().get("token","");
//        String token="8024F670BB230B9C5B6190F7EDAC3C86";
        ICreateFilepresenter.createFile(token,file);
//        ICreateFilepresenter.createFile(token,nick,birthday,Integer.parseInt(height),Integer.parseInt(weight),gender.equals("男")?1:0);
//        createFilepresenter.createFile("","",file);
       // Toast.makeText(CreatFlleActivity.this,"创建档案成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==GET_BODY_DIMENSION){
                file= (File) data.getSerializableExtra("file");
                Log.i(file.toString());
        }
    }
}
