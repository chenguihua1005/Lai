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

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.personalPK.model.PKCreatModel;
import com.softtek.lai.module.personalPK.model.PKForm;
import com.softtek.lai.module.personalPK.model.SavePK;
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

    @Required(order = 1,message = "请选择PK开始时间")
    @InjectView(R.id.tv_start)
    TextView tv_start;
    @Required(order = 2,message = "请选择PK结束时间")
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
            Picasso.with(this).load(AddressManager.get("photoHost")+model.getUserPhoto())
                    .fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(sender1_header);
        }
        if(StringUtils.isNotEmpty(model.getBeUserPhoto())){
            Picasso.with(this).load(AddressManager.get("photoHost")+model.getBeUserPhoto())
                    .fit()
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(sender2_header);
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
        int afterDay=currentDay+1;
        String currentDate=currentYear+"年"+(currentMonth<10?"0"+currentMonth:currentMonth)+"月"+(afterDay<10?"0"+afterDay:afterDay)+"日";
        tv_start.setText(currentDate);
        tv_end.setText(currentDate);
        form.setStart(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(currentDate,"yyyy-MM-dd"));
        form.setEnd(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(currentDate,"yyyy-MM-dd"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //判断开始时间和结束时间的大小
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
                    int month=monthOfYear+1;
                    String date=year+"年"+(month<10?("0"+month):month)+"月"+(dayOfMonth<10?("0"+dayOfMonth):dayOfMonth)+"日";
                    if(year<currentYear||(year==currentYear&&monthOfYear+1<currentMonth)||
                            (year==currentYear&&monthOfYear+1==currentMonth&&dayOfMonth<=currentDay)){
                        String tip;
                        if(flag){
                            tip="PK开始时间必须大于当前时间";
                        }else{
                            tip="PK结束时间必须大于当前时间";
                        }
                        showTip(tip);
                    }else{
                        //输出当前日期
                        count=0;
                        if(flag){
                            tv_start.setText(date);
                            form.setStart(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date,"yyyy-MM-dd"));
                        }else{
                            tv_end.setText(date);
                            form.setEnd(DateUtil.getInstance("yyyy年MM月dd日").convertDateStr(date,"yyyy-MM-dd"));
                        }
                    }
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)+1).show();

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
        String start=tv_start.getText().toString();
        String end=tv_end.getText().toString();
        if(DateUtil.getInstance("yyyy年MM月dd日").isGt(start,end)){
            new AlertDialog.Builder(this).setMessage("PK结束时间不能小于PK开始时间").create().show();
            return;
        }
        dialogShow("创建PK中...");
        manager.savePK(form, new RequestCallback<ResponseData<SavePK>>() {
            @Override
            public void success(ResponseData<SavePK> savePKResponseData, Response response) {
                dialogDissmiss();
                if(savePKResponseData.getStatus()==100){
                    Util.toastMsg(savePKResponseData.getMsg());
                    return;
                }
                try {
                    Intent intent=new Intent(SelectTimeActivity.this,PKDetailActivity.class);
                    intent.putExtra("pkType",Constants.CREATE_PK);
                    intent.putExtra("pkId",savePKResponseData.getData().getPKId());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
