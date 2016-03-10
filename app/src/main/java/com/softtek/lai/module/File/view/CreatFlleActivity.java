package com.softtek.lai.module.File.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.presenter.CreateFileImpl;
import com.softtek.lai.module.File.presenter.ICreateFilepresenter;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_creatfile)
public class CreatFlleActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    private int gender=0;
    private ICreateFilepresenter ICreateFilepresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1,message = "昵称必填项")
    @InjectView(R.id.et_nickname)
    EditText et_nickname;

    @Required(order = 2,message = "生日必填项")
    @InjectView(R.id.et_birth)
    EditText et_birth;

    @Required(order = 3,message = "性别必填项")
    @InjectView(R.id.et_sex)
    EditText et_sex;

    @Required(order = 4,message = "身高必填项")
    @InjectView(R.id.et_height)
    EditText et_height;

    @Required(order = 5,message = "体重必填项")
    @InjectView(R.id.et_weight)
    EditText et_weight;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    @InjectView(R.id.btn_finish)
    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_finish.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
   }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ICreateFilepresenter = new CreateFileImpl(this);
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
        }
    }

    @Override
    public void onValidationSucceeded() {
        String nick=et_nickname.getText().toString();
        String birthday=et_birth.getText().toString();
        String gender=et_sex.getText().toString();
        String height=et_height.getText().toString();
        String weight=et_weight.getText().toString();
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
