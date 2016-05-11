package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.present.MyPublicWewlListManager;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_news)
public class MyNewsActivity extends BaseActivity implements View.OnClickListener,MyPublicWewlListManager.MyPublicWewlListCallback {

@InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    MyPublicWewlListManager myPublicWewlListManager;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long accountid=Long.parseLong(userInfoModel.getUser().getUserid());
    @Override
    protected void initViews() {
        tv_title.setText("我的消息");
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        myPublicWewlListManager=new MyPublicWewlListManager(this);
        myPublicWewlListManager.doGetNowRgName(accountid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;


        }
    }

    @Override
    public void getMyPublicWewlList(PublicWewlfModel publicWewlfModel) {


    }
}
