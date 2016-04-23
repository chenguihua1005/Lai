/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.view.HealthyRecordActivity;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.softtek.lai.module.historydate.view.HistoryDataActivity;
import com.softtek.lai.module.login.view.LoginActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy_record)
public class HealthyRecordFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_weight)
    TextView tv_weight;
    @InjectView(R.id.tv_body_fat)
    TextView tv_body_fat;
    @InjectView(R.id.tv_fat)
    TextView tv_fat;
    @InjectView(R.id.tv_bust)
    TextView tv_bust;
    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;
    @InjectView(R.id.tv_hipline)
    TextView tv_hipline;
    @InjectView(R.id.tv_uphipline)
    TextView tv_uphipline;
    @InjectView(R.id.tv_leg)
    TextView tv_leg;
    @InjectView(R.id.tv_shin)
    TextView tv_shin;
    @InjectView(R.id.tv_healthhistoty)
    TextView tv_healthhistoty;

    @InjectView(R.id.lin_is_vr)
    LinearLayout lin_is_vr;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.but_login)
    Button but_login;

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.GONE);
        fl_right.setOnClickListener(this);
        tv_weight.setOnClickListener(this);
        tv_body_fat.setOnClickListener(this);
        tv_fat.setOnClickListener(this);
        tv_bust.setOnClickListener(this);
        tv_waistline.setOnClickListener(this);
        tv_hipline.setOnClickListener(this);
        tv_uphipline.setOnClickListener(this);
        tv_leg.setOnClickListener(this);
        tv_shin.setOnClickListener(this);
        tv_healthhistoty.setOnClickListener(this);
        but_login.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("健康记录");
        iv_email.setImageResource(R.drawable.healthedit);
        if("".equals(UserInfoModel.getInstance().getToken())){
            lin_is_vr.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
            fl_right.setVisibility(View.GONE);

        }else{
            lin_is_vr.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            fl_right.setVisibility(View.VISIBLE);
            //获取健康记录
        }

    }

    private static final int EDIT_HEALTHY_RECORD=2;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_right:
                startActivityForResult(new Intent(getContext(), HealthEntryActivity.class),2);
                break;
            case R.id.tv_weight:
                Intent intent=new Intent(getContext(),HealthyRecordActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
                break;
            case R.id.tv_body_fat:
                Intent intent1=new Intent(getContext(),HealthyRecordActivity.class);
                intent1.putExtra("id",1);
                startActivity(intent1);
                break;
            case R.id.tv_fat:
                Intent intent2=new Intent(getContext(),HealthyRecordActivity.class);
                intent2.putExtra("id",2);
                startActivity(intent2);
                break;
            case R.id.tv_bust:
                Intent intent3=new Intent(getContext(),HealthyRecordActivity.class);
                intent3.putExtra("id",3);
                startActivity(intent3);
                break;
            case R.id.tv_waistline:
                Intent intent4=new Intent(getContext(),HealthyRecordActivity.class);
                intent4.putExtra("id",4);
                startActivity(intent4);
                break;
            case R.id.tv_hipline:
                Intent intent5=new Intent(getContext(),HealthyRecordActivity.class);
                intent5.putExtra("id",5);
                startActivity(intent5);
                break;
            case R.id.tv_uphipline:
                Intent intent6=new Intent(getContext(),HealthyRecordActivity.class);
                intent6.putExtra("id",6);
                startActivity(intent6);
                break;
            case R.id.tv_leg:
                Intent intent7=new Intent(getContext(),HealthyRecordActivity.class);
                intent7.putExtra("id",7);
                startActivity(intent7);
                break;
            case R.id.tv_shin:
                Intent intent8=new Intent(getContext(),HealthyRecordActivity.class);
                intent8.putExtra("id",8);
                startActivity(intent8);
                break;
            case R.id.tv_healthhistoty:
                startActivity(new Intent(getContext(), HistoryDataActivity.class));
                break;
            case R.id.but_login:
                Intent i=new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1){
            if(requestCode==EDIT_HEALTHY_RECORD){

            }
        }
    }
}
