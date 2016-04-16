package com.softtek.lai.module.lossweightstory.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.widgets.CustomGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_new_story)
public class NewStoryActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl;

    @InjectView(R.id.et_sender)
    EditText et_sender;
    @InjectView(R.id.et_log_title)
    EditText et_log_title;
    @InjectView(R.id.et_weight_after)
    EditText et_weight_after;
    @InjectView(R.id.et_content)
    EditText et_content;
    @InjectView(R.id.cgv)
    CustomGridView cgv;

    private List<String> images=new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("发布故事");
        tv_right.setText("发布");
        fl.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        UserModel model= UserInfoModel.getInstance().getUser();
        et_sender.setText(model.getNickname());
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //发布日志按钮
                break;

        }
    }
}
