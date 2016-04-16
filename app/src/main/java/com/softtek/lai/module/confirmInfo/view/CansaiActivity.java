package com.softtek.lai.module.confirmInfo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_cansai)
public class CansaiActivity extends BaseActivity implements View.OnClickListener{

    //toolbar
    //标题
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;


    //确认参赛信息
    @InjectView(R.id.et_name)
    EditText et_name;

    @InjectView(R.id.et_mobile)
    EditText et_mobile;

    @InjectView(R.id.et_classid)
    EditText et_classid;

    @InjectView(R.id.et_weight)
    EditText et_weight;

    @InjectView(R.id.et_pysical)
    EditText et_pysical;

    @InjectView(R.id.et_fat)
    EditText et_fat;

    @InjectView(R.id.et_birthday)
    EditText et_birthday;

    @InjectView(R.id.et_gender)
    EditText et_gender;

    //确认照片信息
    // photo

    //确定按钮
    @InjectView(R.id.btn_sure)
    Button btn_sure;

    //添加身体围度
    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    private ConinfoModel coninfoModel;
    private GetConfirmInfoModel getConfirmInfoModel;

    private IUpConfirmInfopresenter iUpConfirmInfopresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getConfirmInfoModel = new GetConfirmInfoModel();
        String mb=getConfirmInfoModel.getMobile();
        et_birthday.setText(mb);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        iUpConfirmInfopresenter = new UpConfirmInfoImpl(this);
        tv_title.setText("报名参赛");
        btn_sure.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);

        iUpConfirmInfopresenter.getConfirmInfo(130,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_sure:
                String token = SharedPreferenceService.getInstance().get("token", "");
                coninfoModel = new ConinfoModel();
                coninfoModel.setAccountid(12);
                coninfoModel.setClassid("202984");
                coninfoModel.setNickname("mynickname");
                coninfoModel.setBirthday("2012_12_07");
                coninfoModel.setGender(1);
                coninfoModel.setPhoto("photoname");
                coninfoModel.setWeight(100);
                coninfoModel.setPysical(22);
                coninfoModel.setFat(12);
                coninfoModel.setCircum(11);
                coninfoModel.setWaistline(12);
                coninfoModel.setHiplie(11);
                coninfoModel.setUparmgirth(12);
                coninfoModel.setUpleggirth(11);
                coninfoModel.setDoleggirth(11);
                iUpConfirmInfopresenter.changeUpConfirmInfo(token,coninfoModel);
                break;
            //添加身体围度按钮
            case  R.id.btn_Add_bodydimension:
//              startActivity(new Intent(this, DimensioninputActivity.class));
                break;
        }
    }
}
