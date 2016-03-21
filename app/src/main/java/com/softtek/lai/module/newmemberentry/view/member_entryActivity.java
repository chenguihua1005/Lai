package com.softtek.lai.module.newmemberentry.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.DimensionRecordActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_member_entry)
public class member_entryActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    //照片上传
    @InjectView(R.id.tv_photoupload)
    TextView tv_photoupload;

    @InjectView(R.id.btn_Add_bodydimension)
    Button btn_Add_bodydimension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        tv_photoupload.setOnClickListener(this);
        btn_Add_bodydimension.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("新学员录入");
        tv_left.setBackgroundResource(R.drawable.back);
        tv_right.setText("确定");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_left:
                Intent intent3 = new Intent(member_entryActivity.this, member_entryActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.tv_right:

                finish();
                break;
            case R.id.btn_Add_bodydimension:
                startActivity(new Intent(member_entryActivity.this, DimensionRecordActivity.class));
                finish();
            case R.id.tv_photoupload:

                break;
        }
    }
}
