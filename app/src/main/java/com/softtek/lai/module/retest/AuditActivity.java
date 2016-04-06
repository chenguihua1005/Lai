package com.softtek.lai.module.retest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_audit)
public class AuditActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //信息保存控件
    @InjectView(R.id.tv_write_chu_weight)
    TextView tv_write_chu_weight;
    @InjectView(R.id.tv_audit_now_weight)
    TextView tv_audit_now_weight;
    //体脂
    @InjectView(R.id.tv_retestAudit_tizhi)
    TextView tv_retestAudit_tizhi;
    //胸围
    @InjectView(R.id.tv_retestAudit_wasit)
    TextView tv_retestAudit_wasit;
    //腰围
    @InjectView(R.id.tv_retestAudit_yaowei)
    TextView tv_retestAudit_yaowei;
    //臀围
    @InjectView(R.id.tv_retestAudit_tunwei)
    TextView tv_retestAudit_tunwei;
    //上臂围
    @InjectView(R.id.tv_retestAudit_upArmGirth)
    TextView tv_retestAudit_upArmGirth;
    //上腿围
    @InjectView(R.id.tv_retestAudit_upLegGirth)
    TextView tv_retestAudit_upLegGirth;
    //小腿围
    @InjectView(R.id.tv_retestAudit_doLegGirth)
    TextView tv_retestAudit_doLegGirth;
    //内脂
    @InjectView(R.id.tv_retesrAudit_fat)
    TextView tv_retesrAudit_fat;


    //触发弹框选择点击事件
    //初始体重
    @InjectView(R.id.ll_write_chu_weight)
    LinearLayout ll_write_chu_weight;
    //现在体重
    @InjectView(R.id.ll_retestAudit_nowweight)
    LinearLayout ll_retestAudit_nowweight;
    //体脂
    @InjectView(R.id.ll_retestAudit_tizhi)
    LinearLayout ll_retestAudit_tizhi;
    //胸围
    @InjectView(R.id.ll_retestAudit_wasit)
    LinearLayout ll_retestAudit_wasit;
    //腰围
    @InjectView(R.id.ll_retestAudit_yaowei)
    LinearLayout ll_retestAudit_yaowei;
    //臀围
    @InjectView(R.id.ll_retestAudit_tunwei)
    LinearLayout ll_retestAudit_tunwei;
    //上臂围
    @InjectView(R.id.ll_retestAudit_upArmGirth)
    LinearLayout ll_retestAudit_upArmGirth;
    //大腿围
    @InjectView(R.id.ll_retestAudit_upLegGirth)
    LinearLayout ll_retestAudit_upLegGirth;
    //小腿围
    @InjectView(R.id.ll_retestAudit_doLegGirth)
    LinearLayout ll_retestAudit_doLegGirth;
    //内脂
    @InjectView(R.id.ll_retesrAudit_fat)
    LinearLayout ll_retesrAudit_fat;



    private RetestPre retestPre;
    private RetestAuditModel retestAudit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_right.setOnClickListener(this);
        tv_write_chu_weight.setOnClickListener(this);
        ll_write_chu_weight.setOnClickListener(this);
        ll_retestAudit_nowweight.setOnClickListener(this);
        ll_retestAudit_tizhi.setOnClickListener(this);
        ll_retestAudit_wasit.setOnClickListener(this);
        ll_retestAudit_yaowei.setOnClickListener(this);
        ll_retestAudit_tunwei.setOnClickListener(this);
        ll_retestAudit_upArmGirth.setOnClickListener(this);
        ll_retestAudit_upLegGirth.setOnClickListener(this);
        ll_retestAudit_doLegGirth.setOnClickListener(this);
        ll_retesrAudit_fat.setOnClickListener(this);
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {

        title.setText(R.string.AuditBarT);
        tv_right.setText(R.string.AuditBarR);
        retestPre=new RetestclassImp();
        retestAudit=new RetestAuditModel("3","55","3","63","3","3","3","3","3","3","3","3");
        retestPre.doGetAudit(36,3,"2016-03-28");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_right:
//                retestAudit.setDoLegGirth(tv_retestAudit_doLegGirth+"");
                retestPre.doPostAudit("36","3","2016-03-28",retestAudit);
                break;
            //信息点击事件
            case R.id.ll_write_chu_weight:
                show_information("初始体重（kg）",200,70,20,9,5,0,0);
                break;
            case R.id.ll_retestAudit_nowweight:
                show_information("现在体重（kg）",200,70,20,9,5,0,1);
                break;
            case R.id.ll_retestAudit_tizhi:
                show_information("体脂（%）",100,50,0,9,5,0,2);
                break;
            case R.id.ll_retestAudit_wasit:
                show_information("胸围（CM）",100,50,0,9,5,0,3);
                break;
            case R.id.ll_retestAudit_yaowei:
                show_information("腰围（CM）",100,50,0,9,5,0,4);
                break;
            case R.id.ll_retestAudit_tunwei:
                show_information("臀围（CM）",100,50,0,9,5,0,5);
                break;
            case R.id.ll_retestAudit_upArmGirth:
                show_information("上臂围（CM）",50,25,0,9,5,0,6);
                break;
            case R.id.ll_retestAudit_upLegGirth:
                show_information("大腿围（CM）",150,80,0,9,5,0,7);
                break;
            case R.id.ll_retestAudit_doLegGirth:
                show_information("小腿围（CM）",100,50,0,9,5,0,8);
                break;
            case R.id.ll_retesrAudit_fat:
                show_information("内脂（%）",100,50,0,9,5,0,9);
                break;

        }

    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker)view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(np1maxvalur);
        np1.setValue(np1value);
        np1.setMinValue(np1minvalue);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(np2maxvalue);
        np2.setValue(np2value);
        np2.setMinValue(np2minvalue);
        np2.setWrapSelectorWheel(false);
        information_dialog.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (num==0) {
                    tv_write_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview

                }
                else if (num==1)
                {
                    tv_audit_now_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==2)
                {
                    tv_retestAudit_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestAudit_wasit.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==4)
                {
                    tv_retestAudit_yaowei.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==5)
                {
                    tv_retestAudit_tunwei.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==6)
                {
                    tv_retestAudit_upArmGirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==7)
                {
                    tv_retestAudit_upLegGirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==8)
                {
                    tv_retestAudit_doLegGirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if (num==9)
                {
                    tv_retesrAudit_fat.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

}
