package com.softtek.lai.module.bodygame3.activity.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.InitAuditPModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.picture.view.PictureActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_initwrite)
public class InitDataAuditActivity extends BaseActivity implements View.OnClickListener,
        Validator.ValidationListener/*,ImageFileSelector.Callback*/{
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.btn_retest_write_addbody)
    Button btn_retest_write_addbody;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.im_retestwrite_takephoto)
    ImageView im_retestwrite_takephoto;
    //显示照片
    @InjectView(R.id.im_retestwrite_showphoto)
    ImageView im_retestwrite_showphoto;
    //删除照片
    @InjectView(R.id.im_delete)
    ImageView im_delete;
    //信息点击弹框
    //初始体重
    @InjectView(R.id.ll_retestWrite_chu_weight)
    RelativeLayout ll_retestWrite_chu_weight;
    //现在体重
    @InjectView(R.id.ll_retestWrite_nowweight)
    RelativeLayout ll_retestWrite_nowweight;
    //体脂
    @InjectView(R.id.ll_retestWrite_tizhi)
    RelativeLayout ll_retestWrite_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    RelativeLayout ll_retestWrite_neizhi;

    @LifeCircleInject
    ValidateLife validateLife;

    //初始体重
    @InjectView(R.id.tv_write_chu_weight)
    EditText tv_write_chu_weight;
    //体脂
    @Required(order = 2,message = "体脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_tizhi)
    TextView tv_retestWrite_tizhi;
    //内脂
    @Required(order = 3,message = "内脂为必填项，请选择")
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;

    //昵称
    @InjectView(R.id.tv_write_nick)
    TextView tv_write_nick;
    //手机号
    @InjectView(R.id.tv_write_phone)
    TextView tv_write_phone;
    //头像
    @InjectView(R.id.iv_write_head)
    CircleImageView iv_write_head;
    //班级名称
    @InjectView(R.id.tv_write_class)
    TextView tv_write_class;
    //第几周期
    @InjectView(R.id.tv_retest_write_weekth)
    TextView tv_retest_write_weekth;
    //开始月份
    @InjectView(R.id.tv_write_starm)
    TextView tv_write_starm;
    //开始日期
    @InjectView(R.id.tv_write_stard)
    TextView tv_write_stard;
    //结束月份
    @InjectView(R.id.tv_write_endm)
    TextView tv_write_endm;
    //结束日期
    @InjectView(R.id.tv_write_endd)
    TextView tv_write_endd;
    @InjectView(R.id.rootlayout)
    RelativeLayout rootlayout;
    @InjectView(R.id.vi_noweight)
    View vi_noweight;

    String gender="1";//性别
    private static final int GET_BODY=1;//身体维度
    private static final int BODY=3;
    private CharSequence[] items={"拍照","从相册选择照片"};
    String isState="true";
    FuceSevice service;
    private ProgressDialog progressDialog;
    InitAuditPModel initAuditPModel;
    MeasuredDetailsModel measuredDetailsModel;
    Long AccountId;//用户id
    String classId=" ";//班级id
    Context context;
    String files,ACMID;
    String photoname;
    int IsAudit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_retestWrite_chu_weight.setOnClickListener(this);
        btn_retest_write_addbody.setOnClickListener(this);
        ll_retestWrite_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_delete.setOnClickListener(this);

    }

    @Override
    protected void initViews() {
        context=this;
        progressDialog = new ProgressDialog(this);
        im_retestwrite_showphoto.setOnClickListener(this);
        vi_noweight.setVisibility(View.GONE);
        ll_retestWrite_nowweight.setVisibility(View.GONE);

    }


    @Override
    protected void initDatas() {
        title.setText("初始数据录入");//设置标题栏标题
        tv_right.setText("保存");//保存数据
        classId=getIntent().getStringExtra("classId");
        AccountId=getIntent().getLongExtra("AccountId",0);
        ACMID=getIntent().getStringExtra("ACMID");
        IsAudit=getIntent().getIntExtra("Audited",1);
        if (IsAudit==1)
        {
            tv_right.setText("");
            tv_right.setEnabled(false);
            btn_retest_write_addbody.setText("查看身体围度");
            im_retestwrite_takephoto.setVisibility(View.INVISIBLE);
        }
        else {
            tv_right.setText("保存");//保存数据
        }

        service = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        //获取数据接口
        doGetInfo();

        iv_email.setVisibility(View.INVISIBLE);


    }


    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //删除照片
            case R.id.im_delete:
                im_retestwrite_showphoto.setVisibility(View.GONE);
                im_delete.setVisibility(View.GONE);
                files="";
                break;
            //标题栏左返回
            case R.id.ll_left:
                finish();
            break;
            //标题栏右提交保存事件
            case R.id.tv_right:
                if (TextUtils.isEmpty(tv_write_chu_weight.getText()))
                {
                    String message = "初始体重为必填项，请选择";
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .create().show();
                }
                else {
                    validateLife.validate();
                }
                break;

            //添加身体围度
            case R.id.btn_retest_write_addbody:
                Intent intent=new Intent(InitDataAuditActivity.this, BodyweiduActivity.class);
                intent.putExtra("initaudit",measuredDetailsModel);
                intent.putExtra("Audited",IsAudit);
                startActivityForResult(intent,GET_BODY);
                break;
            case R.id.ll_retestWrite_chu_weight:
                if (IsAudit!=1) {
                    if (gender.equals("0")) {
                        show_information("初始体重（斤）", 600, 100, 50, 9, 0, 0, 0);
                    } else {
                        show_information("初始体重（斤）", 600, 150, 50, 9, 0, 0, 0);
                    }
                }
                break;
            case R.id.ll_retestWrite_tizhi:
                if (IsAudit!=1) {
                    show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                }
                break;
            case R.id.ll_retestWrite_neizhi:
                if (IsAudit!=1) {
                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                }
                break;
            case R.id.im_retestwrite_showphoto:
                Intent intent1=new Intent(this, PictureActivity.class);
                ArrayList<String> images=new ArrayList<>();
                intent1.putExtra("images",images);
                intent1.putExtra("position",0);
                startActivity(intent1);
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //身体围度值传递
        if (requestCode==GET_BODY&&resultCode==RESULT_OK){
            Log.i("》》》》》requestCode："+requestCode+"resultCode："+resultCode);
            measuredDetailsModel=(MeasuredDetailsModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite"+measuredDetailsModel);
        }

    }
    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker)view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
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

                }
                else if (num==2)
                {
                    tv_retestWrite_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestWrite_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();


    }

    @Override
    public void onValidationSucceeded() {
        //验证成功
        doSetPostData();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
        String message = failedRule.getFailureMessage();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create().show();
    }

    /*
        * 获取初始基本数据
        * */
    private void doGetInfo() {
        service.doGetMeasuredDetails(UserInfoModel.getInstance().getToken(), ACMID, new RequestCallback<ResponseData<MeasuredDetailsModel>>() {
            @Override
            public void success(ResponseData<MeasuredDetailsModel> measuredDetailsModelResponseData, Response response) {
                int status=measuredDetailsModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        measuredDetailsModel=measuredDetailsModelResponseData.getData();
                        doSetData();
                        break;
                    default:
                        Util.toastMsg(measuredDetailsModelResponseData.getMsg());
                        break;
                }

            }
        });

    }
    /*
    * 获取数据值
    * */
    void doSetData()
    {
        if (measuredDetailsModel!=null)
        {
            try {
                final String url= AddressManager.get("photoHost");

                if (!TextUtils.isEmpty(measuredDetailsModel.getPhoto()))
                {

                    Picasso.with(context).load(url+measuredDetailsModel.getPhoto()).fit().into(iv_write_head);//头像
                }
                if (!TextUtils.isEmpty(measuredDetailsModel.getImgThumbnail()))
                {
                    im_retestwrite_showphoto.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(url+measuredDetailsModel.getImgThumbnail()).fit().into(im_retestwrite_showphoto);//图片
                    photoname=measuredDetailsModel.getImgThumbnail();
//                    SavePic savePic=new SavePic();
//                savePic.GetPicUrl(photoname);
                }
                tv_write_nick.setText(measuredDetailsModel.getUserName());//设置用户名
                tv_write_phone.setText(measuredDetailsModel.getMobile());//手机号
                tv_write_class.setText(measuredDetailsModel.getClassName());//班级名
                tv_retest_write_weekth.setText(measuredDetailsModel.getWeekNum());//当前周
                if (!TextUtils.isEmpty(measuredDetailsModel.getStartDate())) {
                    String Stardata[] = measuredDetailsModel.getStartDate().split("-");
                    tv_write_starm.setText(Long.parseLong(Stardata[1]) + "");//开班月
                    tv_write_stard.setText(Long.parseLong(Stardata[2]) + "");//开班日
                }
                if (!TextUtils.isEmpty(measuredDetailsModel.getEndDate())) {
                    String Enddata[] = measuredDetailsModel.getEndDate().split("-");
                    tv_write_endm.setText(Long.parseLong(Enddata[1]) + "");//结束月
                    tv_write_endd.setText(Long.parseLong(Enddata[2]) + "");//结束日
                }
                tv_write_chu_weight.setText("0.0".equals(measuredDetailsModel.getWeight())?"":measuredDetailsModel.getWeight());//初始体重
                tv_retestWrite_tizhi.setText("0.0".equals(measuredDetailsModel.getPysical())?"":measuredDetailsModel.getPysical());//体脂
                tv_retestWrite_neizhi.setText("0.0".equals(measuredDetailsModel.getFat())?"":measuredDetailsModel.getFat());//内脂
                gender=measuredDetailsModel.getGender();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    /*l录入*/
    void doSetPostData()
    {Log.i("AccountId"+AccountId+"classId"+classId+"ACMID"+ACMID+"身体维度上传"+"胸围"+measuredDetailsModel.getCircum()+"腰围 "+measuredDetailsModel.getWaistline()+"臀围"+measuredDetailsModel.getHiplie()+"上臂围"+measuredDetailsModel.getUpArmGirth()+"大腿围"+measuredDetailsModel.getUpLegGirth()+"小腿围"+measuredDetailsModel.getDoLegGirth());
        initAuditPModel=new InitAuditPModel();
        initAuditPModel.setACMId(ACMID);
        initAuditPModel.setReviewerId(UserInfoModel.getInstance().getUser().getUserid());
        initAuditPModel.setWeight(tv_write_chu_weight.getText().toString());//体重
        initAuditPModel.setPysical(tv_retestWrite_tizhi.getText().toString());//体脂
        initAuditPModel.setFat(tv_retestWrite_neizhi.getText().toString());//内脂
        initAuditPModel.setCircum(measuredDetailsModel.getCircum().toString());//胸围
        initAuditPModel.setCircum(measuredDetailsModel.getHiplie().toString());//臀围
        initAuditPModel.setCircum(measuredDetailsModel.getWaistline().toString());//腰围
        initAuditPModel.setCircum(measuredDetailsModel.getUpArmGirth().toString());
        initAuditPModel.setCircum(measuredDetailsModel.getUpLegGirth().toString());
        initAuditPModel.setCircum(measuredDetailsModel.getDoLegGirth());
        Log.i("上传数据" +initAuditPModel.toString() );
        doPostInitData();
    }
    //录入请求
    private void doPostInitData()
    {
        service.doReviewInitData(UserInfoModel.getInstance().getToken(), initAuditPModel, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }

            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}
