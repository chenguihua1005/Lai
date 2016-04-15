package com.softtek.lai.module.confirmInfo.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;
import com.softtek.lai.module.newmemberentry.view.EntryActivity;

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

    @InjectView(R.id.btn_sure)
    Button btn_sure;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    private ConinfoModel coninfo;
    private IUpConfirmInfopresenter iUpConfirmInfopresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_sure:
                String token = SharedPreferenceService.getInstance().get("token", "");
                coninfo = new ConinfoModel();
                // Log.i("ConinfoModel:--------------" + ConinfoModel);
                coninfo.setAccountid(12);
                coninfo.setClassid("202984");
                coninfo.setNickname("mynickname");
                coninfo.setBirthday("2012_12_07");
                coninfo.setGender(1);
                coninfo.setPhoto("photoname");
                coninfo.setWeight(100);
                coninfo.setPysical(22);
                coninfo.setFat(12);
                coninfo.setCircum(11);
                coninfo.setWaistline(12);
                coninfo.setHiplie(11);
                coninfo.setUparmgirth(12);
                coninfo.setUpleggirth(11);
                coninfo.setDoleggirth(11);
                iUpConfirmInfopresenter.changeUpConfirmInfo(token,coninfo);
                break;
            case  R.id.btn_Add_bodydimension:
//              startActivity(new Intent(this, EntryActivity.class));
                break;
        }
    }
}
