package com.softtek.lai.module.retest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_audit)
public class AuditActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{
    @LifeCircleInject
    ValidateLife validateLife;
    //标题栏
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    //信息保存控件
    @Required(order = 1,message = "初始体重必填项，请选择")
    @InjectView(R.id.tv_audit_chu_weight)
    EditText tv_audit_chu_weight;
    @Required(order = 2,message = "现在体重必填项，请选择")
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
    //照片显示
    @InjectView(R.id.im_retestaudit_showphoto)
    ImageView im_retestaudit_showphoto;

    //iv_audit_head
    @InjectView(R.id.iv_audit_head)
    CircleImageView iv_audit_head;
    @InjectView(R.id.tv_audit_nick)
    TextView tv_audit_nick;
    @InjectView(R.id.tv_audit_phone)
    TextView tv_audit_phone;
    @InjectView(R.id.tv_audit_class)
    TextView tv_audit_class;
    @InjectView(R.id.tv_audit_weekth)
    TextView tv_audit_weekth;
    @InjectView(R.id.tv_audit_monst)
    TextView tv_audit_monst;
    @InjectView(R.id.tv_audit_dayst)
    TextView tv_audit_dayst;
    @InjectView(R.id.tv_audit_monen)
    TextView tv_audit_monen;
    @InjectView(R.id.tv_audit_dayen)
    TextView tv_audit_dayen;


    //触发弹框选择点击事件
    //初始体重
    @InjectView(R.id.ll_audit_chu_weight)
    LinearLayout ll_audit_chu_weight;
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


    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    long loginid=Long.parseLong(userInfoModel.getUser().getUserid());
    String accountid;
    private RetestPre retestPre;
    private RetestAuditModel retestAudit;
    String acountid;
    String Typedate;
    String classid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_audit_chu_weight.setOnClickListener(this);
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
        EventBus.getDefault().register(this);



    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initDatas() {

        tv_title.setText(R.string.AuditBarT);
        tv_right.setText(R.string.AuditBarR);
        retestPre=new RetestclassImp();
        retestAudit=new RetestAuditModel();
        Intent intent=getIntent();
        String accountId=intent.getStringExtra("accountId");
        String loginId=intent.getStringExtra("loginId");
        String classId=intent.getStringExtra("classId");
        classid=classId;
        accountid=accountId;
        String typedate=intent.getStringExtra("typeDate");
        Typedate=typedate;
        //开班时间，判断班级名称（几月班）
        String StartDate=intent.getStringExtra("StartDate");
        //开始周期
        String CurrStart=intent.getStringExtra("CurrStart");
        //结束周期
        String CurrEnd=intent.getStringExtra("CurrEnd");
        //昵称
        String UserName=intent.getStringExtra("UserName");
        //手机号
        String Mobile=intent.getStringExtra("Mobile");
        //头像
        String Photo=intent.getStringExtra("Photo");
        //第几周期
        String Weekth=intent.getStringExtra("Weekth");
//        typedate
        acountid=accountId;
        retestPre.doGetAudit(Integer.parseInt(accountId),Integer.parseInt(classId),Typedate);

    }
    @Subscribe
    public void doGetDates(RetestAuditModelEvent retestAuditModelEvent){
        Log.i("retestAuditModel"+retestAuditModelEvent.getRetestAuditModels());
        tv_audit_chu_weight.setText(Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getInitWeight())+"");
        tv_audit_nick.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getUserName());
        tv_audit_phone.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getMobile());
        String StartDate=retestAuditModelEvent.getRetestAuditModels().get(0).getStartDate();
        String CurrStart=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrStart();
        String CurrEnd=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrEnd();
        String[] mon=StartDate.split("-");
        String[] currStart=CurrStart.split("-");
        String[] currEnd=CurrEnd.split("-");
        tv_audit_class.setText(tomonth(mon[1]));
        tv_audit_monst.setText(currStart[1]);
        tv_audit_dayst.setText(currStart[2]);
        tv_audit_monen.setText(currEnd[1]);
        tv_audit_dayen.setText(currEnd[2]);
        tv_audit_weekth.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getWeekth());
        if(!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto())) {
            Picasso.with(this).load(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto()).placeholder(R.drawable.img_default).error(R.drawable.img_default).into(iv_audit_head);
        }
        else {
            Picasso.with(this).load("www").placeholder(R.drawable.img_default).error(R.drawable.img_default).into(iv_audit_head);
        }
        tv_audit_now_weight.setText(Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getWeight())+"");
        tv_retestAudit_tizhi.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getPysical());
        tv_retesrAudit_fat.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getFat());
        tv_retestAudit_wasit.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getCircum());
        tv_retestAudit_yaowei.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getWaistline());
        tv_retestAudit_tunwei.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getHiplie());
        tv_retestAudit_upArmGirth.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getUpArmGirth());
        tv_retestAudit_upLegGirth.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getUpArmGirth());
        tv_retestAudit_doLegGirth.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getDoLegGirth());
        if(!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getImage())) {
            im_retestaudit_showphoto.setVisibility(View.VISIBLE);
            Picasso.with(this).load(retestAuditModelEvent.getRetestAuditModels().get(0).getImage()).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(im_retestaudit_showphoto);
        }
        else {
            im_retestaudit_showphoto.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_right:
                validateLife.validate();
//                retestAudit.setDoLegGirth(tv_retestAudit_doLegGirth+"");

                break;
            //信息点击事件
            case R.id.ll_audit_chu_weight:
                show_information("初始体重（斤）",200,70,20,9,5,0,0);
                break;
            case R.id.ll_retestAudit_nowweight:
                show_information("现在体重（斤）",200,70,20,9,5,0,1);
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
                show_information("内脂",100,50,0,9,5,0,9);
                break;
            case R.id.ll_left:
                finish();
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
                    tv_audit_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    tv_audit_chu_weight.setError(null);
                }
                else if (num==1)
                {
                    tv_audit_now_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    tv_audit_now_weight.setError(null);
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

    public String tomonth(String month){
        if (month.equals("01")){
            month="一月班";
        }
        else if (month.equals("02")){
            month="二月班";
        }else if (month.equals("03"))
        {
            month="三月班";
        }else if (month.equals("04"))
        {
            month="四月班";

        }else if (month.equals("05"))
        {
            month="五月班";
        }else if (month.equals("06"))
        {
            month="六月班";
        }else if (month.equals("07"))
        {
            month="七月班";
        } else if (month.equals("08"))
        {
            month="八月班";
        }else if (month.equals("09"))
        {
            month="九月班";
        }else if (month.equals("10"))
        {
            month="十月班";
        }else if (month.equals("11"))
        {
            month="十一月班";
        }else
        {
            month="十二月班";
        }
        return month;
    }

    @Override
    public void onValidationSucceeded() {
        retestAudit.setInitWeight(tv_audit_chu_weight.getText()+"");
        retestAudit.setWeight(tv_audit_now_weight.getText()+"");
        retestAudit.setPysical(tv_retestAudit_tizhi.getText()+"");
        retestAudit.setFat(tv_retesrAudit_fat.getText()+"");
        retestAudit.setCircum(tv_retestAudit_wasit.getText()+"");
        retestAudit.setWaistline(tv_retestAudit_yaowei.getText()+"");
        retestAudit.setHiplie(tv_retestAudit_tunwei.getText()+"");
        retestAudit.setUpArmGirth(tv_retestAudit_upArmGirth.getText()+"");
        retestAudit.setUpLegGirth(tv_retestAudit_upLegGirth.getText()+"");
        retestAudit.setDoLegGirth(tv_retestAudit_doLegGirth.getText()+"");
        retestAudit.setAccountId(accountid);
        retestAudit.setClassId(classid);

//        retestAudit.getCircum(tv_retestAudit_wasit.getText());
        retestPre.doPostAudit(loginid+"",accountid,Typedate,retestAudit);
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }



}
