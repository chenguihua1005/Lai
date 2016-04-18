package com.softtek.lai.module.confirmInfo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.confirmInfo.EventModel.ConinfoEvent;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;
import com.softtek.lai.module.newmemberentry.view.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.view.model.PargradeModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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

    //修改信息
//    昵称。生日。性别。照片。体重。体脂。内脂
//    胸围。腰围。臀围。上臂围。大腿围。小腿围



//    显示姓名,生日,性别,手机号码, 参赛班级,初始体重, 照片. 用户可以对参赛数据进行一次修改.
//           // "Mobile": "18286565885",
//            "Photo": "201603290913492218475932.jpg",
//         //   "ClassName": "20160310_1",
//            "Weight": "158",
//            "Pysical": "",
//            "Fat": "",
//            "Circum": "",
//            "Waistline": "",
//            "Hiplie": "",
//            "UpArmGirth": "",
//            "UpLegGirth": "",
//            "DoLegGirth": ""

    //确认参赛信息
    @InjectView(R.id.et_name)
    EditText et_name;

    @InjectView(R.id.et_birthday)
    EditText et_birthday;

    @InjectView(R.id.et_sex)
    EditText et_sex;

    //手机号码
    @InjectView(R.id.et_mobile)
    EditText et_mobile;

    @InjectView(R.id.et_classname)
    EditText et_classname;

    @InjectView(R.id.et_weight)
    EditText et_weight;

    @InjectView(R.id.et_pysical)
    EditText et_pysical;

    @InjectView(R.id.et_fat)
    EditText et_fat;

    @InjectView(R.id.et_yaowei)
    EditText et_yaowei;
//    "Circum": "",
//            "Waistline": "",
//            "Hiplie": "",
//            "UpArmGirth": "",
//            "UpLegGirth": "",
//            "DoLegGirth": ""
    //确认照片信息
    // photo

    //确定按钮
    @InjectView(R.id.btn_sure)
    Button btn_sure;

    private ConinfoModel coninfoModel;
    private GetConfirmInfoModel getConfirmInfoModel;

    private IUpConfirmInfopresenter iUpConfirmInfopresenter;

    private String name;

    private String birthday;

    private String mobile;

    private String classname;

    private String weight;

    private String pysical;

    private String fat;

    private String yaowei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        iUpConfirmInfopresenter = new UpConfirmInfoImpl(this);
        iUpConfirmInfopresenter.getConfirmInfo(130,1);

       // getConfirmInfoModel.getBirthday();
       // Log.i("getAccountId:"+getConfirmInfoModel.getAccountId()+"getMobile:"+getConfirmInfoModel.getMobile()+"getBirthday:"+getConfirmInfoModel.getBirthday()+"getClassName:"+getConfirmInfoModel.getClassName());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("报名参赛");
        btn_sure.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ConinfoEvent coninfoEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + coninfoEvent.getConfirmInfoModel());
        GetConfirmInfoModel getConfirmInfoModel = coninfoEvent.getConfirmInfoModel();
//        for (PargradeModel cl : pargradeModels) {
//            System.out.println("dsfsdfsdfsdfsdfsdf?????/?????>>》》》》》》》》》》》》》》" + "ClassIdModel:" + cl.getClassId() + "ClassName:" + cl.getClassName());
//            PargradeModel p1 = new PargradeModel(cl.getClassId(), cl.getClassName());
//            pargradeModelList.add(p1);
//        }
        name=getConfirmInfoModel.getUserName();

        birthday=getConfirmInfoModel.getBirthday();

        mobile=getConfirmInfoModel.getMobile();

        weight=getConfirmInfoModel.getWeight();

        pysical=getConfirmInfoModel.getPysical();

        fat=getConfirmInfoModel.getFat();

        yaowei=getConfirmInfoModel.getWaistline();

        et_name.setText(name);

        et_birthday.setText(birthday);

        et_mobile.setText(mobile);

        et_weight.setText(weight);

        et_pysical.setText(pysical);

        et_fat.setText(fat);

        et_yaowei.setText(yaowei);
//        Log.i("------------>>>>>>getConfirmInfoModel.getBirthday:"+getConfirmInfoModel.getBirthday());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_sure:
                String token = SharedPreferenceService.getInstance().get("token", "");
                coninfoModel = new ConinfoModel();
                coninfoModel.setAccountid(12);
                coninfoModel.setClassid("202984");
                coninfoModel.setNickname(et_name.getText().toString());
                coninfoModel.setBirthday(et_birthday.getText().toString());
                coninfoModel.setGender(1);
                coninfoModel.setPhoto("photoname");
                coninfoModel.setWeight(Integer.parseInt(et_weight.getText().toString()));
                coninfoModel.setPysical(22);
                coninfoModel.setFat(Integer.parseInt(et_pysical.getText().toString()));
                coninfoModel.setCircum(11);
                coninfoModel.setWaistline(12);
                coninfoModel.setHiplie(11);
                coninfoModel.setUparmgirth(12);
                coninfoModel.setUpleggirth(11);
                coninfoModel.setDoleggirth(11);
                iUpConfirmInfopresenter.changeUpConfirmInfo(token,coninfoModel);
                break;

        }
    }
}
