package com.softtek.lai.module.personalPK.view;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKDetailMold;
import com.softtek.lai.module.personalPK.model.PKForm;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.module.personalPK.presenter.PKListManager;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_select_time)
public class SelectTimeActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.rl_start)
    RelativeLayout rl_start;
    @InjectView(R.id.rl_end)
    RelativeLayout rl_end;

    @Required(order = 1,message = "请选择开始日期")
    @InjectView(R.id.tv_start)
    TextView tv_start;
    @Required(order = 1,message = "请选择结束日期")
    @InjectView(R.id.tv_end)
    TextView tv_end;

    @InjectView(R.id.sender1_header)
    CircleImageView sender1_header;
    @InjectView(R.id.sender2_header)
    CircleImageView sender2_header;
    @InjectView(R.id.tv_pk_name1)
    TextView tv_pk_name1;
    @InjectView(R.id.tv_pk_name2)
    TextView tv_pk_name2;
    @InjectView(R.id.iv_jiangli)
    ImageView iv_jiangli;


    int currentYear;
    int currentMonth;
    int currentDay;

    PKForm form;
    PKListManager manager;
    PKCreatModel model;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rl_start.setOnClickListener(this);
        rl_end.setOnClickListener(this);
        tv_title.setText("选择PK挑战时间");
        tv_right.setText("保存");
    }

    @Override
    protected void initDatas() {
        manager=new PKListManager();
        model=getIntent().getParcelableExtra("pkmodel");
        tv_pk_name1.setText(model.getUserName());
        tv_pk_name2.setText(model.getBeUserName());
        switch (model.getChipType()){
            case Constants.NAIXI:
                iv_jiangli.setBackgroundResource(R.drawable.pk_naixi);
                break;
            case Constants.NAIXICAO:
                iv_jiangli.setBackgroundResource(R.drawable.pk_list_naixicao);
                break;
            case Constants.ZIDINGYI:
                iv_jiangli.setBackgroundResource(R.drawable.pk_chouma);
                break;
        }
        if(StringUtils.isNotEmpty(model.getUserPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+model.getUserPhoto()).fit().into(sender1_header);
        }
        if(StringUtils.isNotEmpty(model.getBeUserPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+model.getBeUserPhoto()).fit().into(sender2_header);
        }
        form=new PKForm();
        form.setBeChallenged(model.getBeChallenged());
        form.setChallenged(model.getChallenged());
        form.setChip(model.getChip());
        form.setChipType(model.getChipType());
        form.setTarget(model.getTarget());
        form.setTargetType(model.getTargetType());

        //获取当前年月日
        currentYear= DateUtil.getInstance().getCurrentYear();
        currentMonth=DateUtil.getInstance().getCurrentMonth();
        currentDay=DateUtil.getInstance().getCurrentDay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //保存
                validateLife.validate();
                break;
            case R.id.rl_start:
                showDateDialog(true);
                break;
            case R.id.rl_end:
                showDateDialog(false);
                break;

        }
    }


    int count=0;
    private void showDateDialog(final boolean flag) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(count==0){
                    count=1;
                    if(year<currentYear){
                        showTip("日期不能小于当前年份");
                    }else if(year==currentYear&&monthOfYear+1<currentMonth){
                        showTip("日期不能小于当前月份");
                    }else if(year==currentYear&&monthOfYear+1==currentMonth&&dayOfMonth<=currentDay){
                        showTip("日期不能小于等于当前日期");
                    }else{
                        //输出当前日期
                        count=0;
                        String date=year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
                        if(flag){
                            tv_start.setText(date);
                            form.setStart(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date,"yyyy-MM-dd HH:mm:ss"));
                        }else{
                            tv_end.setText(date);
                            form.setEnd(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date,"yyyy-MM-dd HH:mm:ss"));
                        }
                    }
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void showTip(String value){
        AlertDialog dialog=new AlertDialog.Builder(this).setMessage(value).create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                count=0;
            }
        });
        dialog.show();
    }

    @Override
    public void onValidationSucceeded() {
        Log.i(form.toString());
        dialogShow("创建PK中...");
        manager.savePK(this, form, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                dialogDissmiss();
                Intent intent=new Intent(SelectTimeActivity.this,PKDetailActivity.class);
                PKDetailMold detailMold=new PKDetailMold();
                detailMold.setChallenged(form.getChallenged());
                detailMold.setBeChallenged(form.getBeChallenged());
                detailMold.setUserName(model.getUserName());
                detailMold.setBUserName(model.getBeUserName());
                detailMold.setPhoto(model.getUserPhoto());
                detailMold.setBPhoto(model.getBeUserPhoto());
                detailMold.setChipType(model.getChipType());
                detailMold.setChip(model.getChip());
                detailMold.setStart(form.getStart());
                detailMold.setEnd(form.getEnd());
                detailMold.setStatus(PKDetailActivity.NOCHALLENGE);//设置应战状态：未因战
                //目标类型
                detailMold.setTargetType(model.getTargetType());
                if(detailMold.getTargetType()==1)
                    detailMold.setTarget(model.getTarget()+"");
                else
                    detailMold.setTarget("在PK期限内，达成更多步数的人即为赢家");
                //点赞数
                detailMold.setChpcou(0);
                detailMold.setBchpcou(0);
                detailMold.setChaTotal(0);
                detailMold.setBchaTotal(0);
                intent.putExtra("pkmodel",detailMold);
                intent.putExtra("pkType",Constants.CREATE_PK);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                super.failure(error);
            }
        });
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        new AlertDialog.Builder(this).setMessage(failedRule.getFailureMessage()).create().show();
    }
}
