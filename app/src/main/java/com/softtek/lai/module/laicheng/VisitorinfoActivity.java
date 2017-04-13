package com.softtek.lai.module.laicheng;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.presenter.VisitorPresenter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_visitorinfo)
public class VisitorinfoActivity extends BaseActivity<VisitorPresenter> implements VisitorPresenter.VisitorView, View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;


    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.et_old)
    EditText et_old;
    @InjectView(R.id.et_mobile)
    EditText et_mobile;
    @InjectView(R.id.et_height)
    EditText et_height;
    @InjectView(R.id.rg_up)
    RadioGroup rg_up;
    @InjectView(R.id.btn_commit)
    Button btn_commit;
    VisitorModel visitorModel = new VisitorModel();
    @Override
    protected void initViews() {
        tv_title.setText("访客信息");
        btn_commit.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                                visitorModel.setName(et_name.getText().toString());
                visitorModel.setHeight(Float.parseFloat(et_height.getText().toString()));
                visitorModel.setBirthDate(et_old.getText().toString());
                visitorModel.setPhoneNo(et_mobile.getText().toString());
//                visitorModel.setGender(gender);
                getPresenter().commitData(UserInfoModel.getInstance().getToken(), visitorModel);
                break;
        }
    }

    @Override
    public void commit(Visitsmodel visitsmodel, VisitorModel visitorModel) {

    }
}
