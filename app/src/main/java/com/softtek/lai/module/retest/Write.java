package com.softtek.lai.module.retest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.retest.model.RetestWrite;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_write)
public class Write extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;

    private RetestPre retestPre;
    private RetestWrite retestWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        title.setText("复测录入");
        tv_right.setText("保存");
        iv_email.setVisibility(View.INVISIBLE);
        retestPre=new RetestclassImp();
        retestWrite=new RetestWrite();
        retestWrite.setCircum("13");
        retestWrite.setAccountId("18175239201");
        retestWrite.setClassId("");
        retestWrite.setDoLegGirth("");
        retestWrite.setFat("");
        retestWrite.setHiplie("");
        retestWrite.setImage("");
        retestWrite.setPysical("");
        retestWrite.setUpArmGirth("");
        retestWrite.setWaistline("");
        retestWrite.setWeight("");
        retestPre.doGetWrite(19,16,retestWrite);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //标题栏左返回
            case R.id.ll_left:
                finish();

            break;
            //标题栏右提交保存事件
            case R.id.tv_right:

                break;

        }

    }
}
