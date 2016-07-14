package com.softtek.lai.module.bodygame2pc.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygamest.model.LossModel;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.softtek.lai.module.bodygamest.view.BodyweidustActivity;
import com.softtek.lai.module.bodygamest.view.GuideActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.lossweightstory.view.PictureActivity;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.retest.AuditActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.adapter.ClassAdapter;
import com.softtek.lai.module.retest.adapter.StudentAdapter;
import com.softtek.lai.module.retest.eventModel.BanJiEvent;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.model.BanjiModel;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.module.retest.model.MeasureModel;
import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.module.retest.view.QueryActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_fuce_st)
public class PCFuCeFragment extends LazyBaseFragment implements View.OnClickListener,Validator.ValidationListener{
    @LifeCircleInject
    ValidateLife validateLife;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.rel)
    RelativeLayout rel;
    //toolbar
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_writest_head)
    CircleImageView iv_writest_head;
    @InjectView(R.id.tv_writest_nick)
    TextView tv_writest_nick;
    @InjectView(R.id.tv_writest_phone)
    TextView tv_writest_phone;
    @InjectView(R.id.tv_writest_class)
    TextView tv_writest_class;
    @InjectView(R.id.tv_writest_classweek)
    TextView tv_writest_classweek;
    @InjectView(R.id.tv_writest_monst)
    TextView tv_writest_monst;
    @InjectView(R.id.tv_writest_dayst)
    TextView tv_writest_dayst;
    @InjectView(R.id.tv_writest_monen)
    TextView tv_writest_monen;
    @InjectView(R.id.tv_writest_dayen)
    TextView tv_writest_dayen;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    private ProgressDialog progressDialog;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    String loginid=userInfoModel.getUser().getUserid();
    String moblie=userInfoModel.getUser().getMobile();

    //保存数据点击
    //初始体重
    @InjectView(R.id.ll_fucest_chu_weight)
    LinearLayout ll_fucest_chu_weight;
    //现在体重
    @InjectView(R.id.ll_fucest_nowweight)
    LinearLayout ll_fucest_nowweight;
    // 体脂
    @InjectView(R.id.ll_fucest_tizhi)
    LinearLayout ll_fucest_tizhi;
    //内脂
    @InjectView(R.id.ll_retestWrite_neizhi)
    LinearLayout ll_retestWrite_neizhi;
    //添加身体维度
    @InjectView(R.id.btn_retest_write_addbodyst)
    Button btn_retest_write_addbodyst;
    //初始体重
    @Required(order = 1,message = "初始体重必填项，请选择")
    @InjectView(R.id.tv_writes_chu_weight)
    EditText tv_writes_chu_weight;
    //现在体重
    @Required(order = 2,message = "现在体重必填项，请选择")
    @InjectView(R.id.tv_retestWrites_nowweight)
    EditText tv_retestWrites_nowweight;
    @InjectView(R.id.tv_retestWritest_tizhi)
    TextView tv_retestWritest_tizhi;
    @InjectView(R.id.tv_retestWritest_neizhi)
    TextView tv_retestWritest_neizhi;
    //takeohoto
    @InjectView(R.id.im_retestwritest_takephoto)
    ImageView im_retestwritest_takephoto;
    //picture
    @InjectView(R.id.im_retestwritest_showphoto)
    ImageView im_retestwritest_showphoto;
    //delete
    @InjectView(R.id.im_deletest)
    ImageView im_deletest;
    //laichen
    @InjectView(R.id.selectlaichenst)
    CheckBox selectlaichenst;

    String gender="1";

    private static final int GET_BODY=2;
    private static final int BODY=3;

    private RetestPre retestPre;
    RetestWriteModel retestWrite;
    MeasureModel measureModel;
    RetestAuditModel retestAuditModel;
    String Mobile;
    String isState="true";
    private ImageFileCropSelector imageFileCropSelector;
    private CharSequence[] items={"拍照","从相册选择照片"};
    private PhotoListPre photoListPre;
    LossModel lossModel;
    SelectPicPopupWindow menuWindow;
    String url;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        fl_right.setOnClickListener(this);
        ll_left.setVisibility(View.GONE);
        photoListPre = new PhotoListIml();
        progressDialog = new ProgressDialog(getContext());
        btn_retest_write_addbodyst.setOnClickListener(this);
        ll_fucest_nowweight.setOnClickListener(this);
        ll_fucest_tizhi.setOnClickListener(this);
        ll_retestWrite_neizhi.setOnClickListener(this);
        im_retestwritest_takephoto.setOnClickListener(this);
        im_deletest.setOnClickListener(this);
        im_retestwritest_showphoto.setOnClickListener(this);
        tv_writes_chu_weight.setEnabled(false);
        tv_retestWrites_nowweight.setEnabled(false);
    }
    @Subscribe
    public void onEvent(LossModel model) {
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        lossModel = model;
        System.out.println("lossModel:" + lossModel);
        String path = AddressManager.get("shareHost");
        url = path + "ShareTranscript?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
        System.out.println("url:" + url);
        menuWindow = new SelectPicPopupWindow(getActivity(), itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(getActivity().findViewById(R.id.rel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe
    public void event(LaichModel laichModel){

        tv_retestWrites_nowweight.setText(StringUtils.isEmpty(laichModel.getWeight())?"":(Float.parseFloat(laichModel.getWeight())+"").equals("0.0")?"":Float.parseFloat(laichModel.getWeight())+"");
        tv_retestWritest_tizhi.setText(StringUtils.isEmpty(laichModel.getPysical())?"":Float.parseFloat(laichModel.getPysical())+"");
        tv_retestWritest_neizhi.setText(StringUtils.isEmpty(laichModel.getFat())?"":Float.parseFloat(laichModel.getFat())+"");
        retestWrite.setCircum(StringUtils.isEmpty(laichModel.getCircum())?"":Float.parseFloat(laichModel.getCircum())+"");
        retestWrite.setWaistline(StringUtils.isEmpty(laichModel.getWaistline())?"":Float.parseFloat(laichModel.getWaistline())+"");
        retestWrite.setHiplie(StringUtils.isEmpty(laichModel.getHiplie())?"":Float.parseFloat(laichModel.getHiplie())+"");
        retestWrite.setUpArmGirth(StringUtils.isEmpty(laichModel.getUpArmGirth())?"":Float.parseFloat(laichModel.getUpArmGirth())+"");
        retestWrite.setUpLegGirth(StringUtils.isEmpty(laichModel.getUpLegGirth())?"":Float.parseFloat(laichModel.getUpLegGirth())+"");
        retestWrite.setDoLegGirth(StringUtils.isEmpty(laichModel.getDoLegGirth())?"":Float.parseFloat(laichModel.getDoLegGirth())+"");
    }
    @Override
    protected void onVisible() {
        super.onVisible();
        if(getContext() instanceof BodyGameSPActivity){
            BodyGameSPActivity activity=(BodyGameSPActivity)getContext();
            activity.setAlpha(1);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_email:
            case R.id.fl_right:
                if (isState.equals("true")) {
                    tv_writes_chu_weight.setEnabled(true);
                    tv_retestWrites_nowweight.setEnabled(true);
                    validateLife.validate();
                }
                else {
                    //new AlertDialog.Builder(this).setMessage("功能开发中敬请期待").create().show();
                    progressDialog.setMessage("加载中");
                    progressDialog.show();
                    photoListPre.getLossData(UserInfoModel.getInstance().getUser().getUserid(), progressDialog);
                }

                break;

            //删除照片
            case R.id.im_deletest:
                retestWrite.setImage("");
                im_deletest.setVisibility(View.GONE);
                im_retestwritest_showphoto.setVisibility(View.GONE);
                im_retestwritest_showphoto.setImageBitmap(null);
                break;
            case R.id.btn_retest_write_addbodyst:
                Intent intent=new Intent(getContext(), BodyweidustActivity.class);
                Log.i("retestWrite="+retestWrite.toString());
                intent.putExtra("retestWrite",retestWrite);
                intent.putExtra("isState",isState);
                startActivityForResult(intent,GET_BODY);
                break;
            case R.id.ll_fucest_nowweight:
                if (isState.equals("true")) {
                    if (gender.equals("1")) {
                        show_information("现在体重（斤）", 600, 100, 50, 9, 0, 0, 1);
                    }
                    else
                    {
                        show_information("现在体重（斤）", 600, 150, 50, 9, 0, 0, 1);
                    }
                }

                break;
            case R.id.ll_fucest_tizhi:
                if (isState.equals("true")) {
                    show_information("体脂（%）", 50, 25, 1, 9, 0, 0, 2);
                }
                break;
            case R.id.ll_retestWrite_neizhi:
                if (isState.equals("true")) {
                    show_information("内脂", 30, 2, 1, 9, 0, 0, 3);
                }
                break;
            //拍照事件
            case R.id.im_retestwritest_takephoto:

                if (isState.equals("true")) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", getActivity().MODE_PRIVATE);
                    boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (isFirstRun)
                    {
                        Intent intent1=new Intent(getContext(),GuideActivity.class);
                        startActivityForResult(intent1,BODY);

                        Log.d("debug", "第一次运行");
                        editor.putBoolean("isFirstRun", false);
                        editor.commit();
                    } else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                        //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)){
                                            //允许弹出提示
                                            ActivityCompat.requestPermissions(getActivity(),
                                                    new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                        }else{
                                            //不允许弹出提示
                                            ActivityCompat.requestPermissions(getActivity(),
                                                    new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                        }
                                    }else {
                                        imageFileCropSelector.takePhoto(getActivity());
                                    }
                                } else if (which == 1) {
                                    //照片
                                    imageFileCropSelector.selectImage(getActivity());
                                }
                            }
                        }).create().show();
                        Log.d("debug", "不是第一次运行");
                    }

                }

                break;
//            case R.id.tv_right:
//
//                break;


            case R.id.im_retestwritest_showphoto:
                Intent intent1=new Intent(getContext(),PictureActivity.class);
                ArrayList<String> imags=new ArrayList<>();
                imags.add(retestWrite.getImage());
                intent1.putExtra("images",imags);
                intent1.putExtra("position",0);
                startActivity(intent1);
                break;
        }
    }

    @Subscribe
    public void doGetDates(RetestAuditModelEvent retestAuditModelEvent) throws Exception {
        Log.i("retestAuditModel"+retestAuditModelEvent.getRetestAuditModels());
        tv_writes_chu_weight.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getInitWeight().equals("")?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getInitWeight())+"");
        tv_writest_nick.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getUserName());
        Mobile=retestAuditModelEvent.getRetestAuditModels().get(0).getMobile();
        tv_writest_phone.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getMobile());
        gender=retestAuditModelEvent.getRetestAuditModels().get(0).getGender();
        String StartDate=retestAuditModelEvent.getRetestAuditModels().get(0).getStartDate();
        String CurrStart=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrStart();
        String CurrEnd=retestAuditModelEvent.getRetestAuditModels().get(0).getCurrEnd();
        String[] mon=StartDate.split("-");
        String[] currStart=CurrStart.split("-");
        String[] currEnd=CurrEnd.split("-");
        retestWrite.setClassId(retestAuditModelEvent.getRetestAuditModels().get(0).getClassId());
        tv_writest_class.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getClassName());
        tv_writest_monst.setText(currStart[1]);
        tv_writest_dayst.setText(currStart[2]);
        tv_writest_monen.setText(currEnd[1]);
        tv_writest_dayen.setText(currEnd[2]);
        tv_writest_classweek.setText(retestAuditModelEvent.getRetestAuditModels().get(0).getWeekth());
        if(!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto())) {
            Picasso.with(getActivity()).load(retestAuditModelEvent.getRetestAuditModels().get(0).getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(iv_writest_head);
        }
        else {
            Picasso.with(getActivity()).load("www").placeholder(R.drawable.img_default).error(R.drawable.img_default).fit().into(iv_writest_head);
        }
        if (!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate())) {
            if (!(ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate()).getTime() >= ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getCurrStart()).getTime() &&
                    ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getTypeDate()).getTime() <= ConverToDate(retestAuditModelEvent.getRetestAuditModels().get(0).getCurrEnd()).getTime())) {
                retestPre.GetUserMeasuredInfo(moblie);
                tv_right.setText("保存");
            } else {
                if (retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus().equals("1") || retestAuditModelEvent.getRetestAuditModels().get(0).getAMStatus().equals("2")) {
                    tv_retestWrites_nowweight.setText(Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getWeight()) + "");
                    tv_retestWritest_tizhi.setText((StringUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getPysical()))?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getPysical())+"");
                    tv_retestWritest_neizhi.setText((StringUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getFat()))?"":Float.parseFloat(retestAuditModelEvent.getRetestAuditModels().get(0).getFat())+"");
                    retestWrite.setCircum(retestAuditModelEvent.getRetestAuditModels().get(0).getCircum());
                    retestWrite.setWaistline(retestAuditModelEvent.getRetestAuditModels().get(0).getWaistline());
                    retestWrite.setHiplie(retestAuditModelEvent.getRetestAuditModels().get(0).getHiplie());
                    retestWrite.setUpArmGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getUpArmGirth());
                    retestWrite.setUpLegGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getUpLegGirth());
                    retestWrite.setDoLegGirth(retestAuditModelEvent.getRetestAuditModels().get(0).getDoLegGirth());
                    isState = "false";
                    btn_retest_write_addbodyst.setText("查看身体围度");
                    tv_right.setText("");
                    tv_right.setFocusable(false);
                    iv_email.setImageResource(R.drawable.img_share_bt);
                    iv_email.setOnClickListener(this);

                    if (!TextUtils.isEmpty(retestAuditModelEvent.getRetestAuditModels().get(0).getImage())) {
                        im_retestwritest_showphoto.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(retestAuditModelEvent.getRetestAuditModels().get(0).getImage()).placeholder(R.drawable.default_icon_square).fit().error(R.drawable.default_icon_square).into(im_retestwritest_showphoto);
                        String[] images=retestAuditModelEvent.getRetestAuditModels().get(0).getImage().split("/");
                        retestWrite.setImage(images[images.length-1]);
                    } else {
                        im_retestwritest_showphoto.setVisibility(View.GONE);
                        Picasso.with(getActivity()).load("www").placeholder(R.drawable.default_icon_square).fit().error(R.drawable.default_icon_square).into(im_retestwritest_showphoto);
                    }
                } else {
                    retestPre.GetUserMeasuredInfo(moblie);
                    tv_right.setText("保存");

                }
            }
        }
        else {
            retestPre.GetUserMeasuredInfo(moblie);
            tv_right.setText("保存");
        }
    }
    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }
    @Subscribe
    public void doGetPhotost(PhotModel photModel) {
        System.out.println("照片名称" + photModel.getImg());
        retestWrite.setImage(photModel.getImg()+"");

    }

    // Android 6.0的动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(getActivity());

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    public String tomonth(String month) {
        if (month.equals("01")) {
            month = "一月班";
        } else if (month.equals("02")) {
            month = "二月班";
        } else if (month.equals("03")) {
            month = "三月班";
        } else if (month.equals("04")) {
            month = "四月班";

        } else if (month.equals("05")) {
            month = "五月班";
        } else if (month.equals("06")) {
            month = "六月班";
        } else if (month.equals("07")) {
            month = "七月班";
        } else if (month.equals("08")) {
            month = "八月班";
        } else if (month.equals("09")) {
            month = "九月班";
        } else if (month.equals("10")) {
            month = "十月班";
        } else if (month.equals("11")) {
            month = "十一月班";
        } else {
            month = "十二月班";
        }
        return month;
    }

    @Override
    protected void initDatas() {
        tv_title.setText("复测");
        retestPre=new RetestclassImp();
        Intent intent=getActivity().getIntent();
        loginid=intent.getStringExtra("accountId");
        if (loginid==null)
        {
            loginid=UserInfoModel.getInstance().getUser().getUserid();
        }
        retestPre.doGetAudit(Long.parseLong(loginid),0,"");
        retestWrite=new RetestWriteModel();
        retestAuditModel=new RetestAuditModel();
        measureModel=new MeasureModel();
        imageFileCropSelector=new ImageFileCropSelector(getContext());
        imageFileCropSelector.setQuality(50);
        imageFileCropSelector.setOutPutAspect(1,1);
        int px=Math.min(DisplayUtil.getMobileHeight(getContext()),DisplayUtil.getMobileWidth(getContext()));
        imageFileCropSelector.setOutPut(px,px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Log.i("摘牌回来了");
                im_retestwritest_showphoto.setVisibility(View.VISIBLE);
                im_deletest.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(new File(file)).fit().into(im_retestwritest_showphoto);
                retestPre.goGetPicture(file);
            }

            @Override
            public void onError() {

            }
        });
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(getContext(), R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(getContext(), R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(lossModel.getContent() + url)
                            .withMedia(new UMImage(getContext(), R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }


        }

    };
    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode,resultCode,data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode,resultCode,data);
        //身体围度值传递
        if (requestCode == GET_BODY && resultCode ==getActivity().RESULT_OK) {
            retestWrite = (RetestWriteModel) data.getSerializableExtra("retestWrite");
            Log.i("新学员录入围度:retestWrite" + retestWrite);
        }
        if (requestCode == BODY && resultCode == getActivity().RESULT_OK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        imageFileCropSelector.takePhoto(getActivity());
                    } else if (which == 1) {
                        //照片
                        imageFileCropSelector.selectImage(getActivity());
                    }
                }
            }).create().show();
        }
    }

    public void show_information(String title, int np1maxvalur, int np1value, int np1minvalue, int np2maxvalue, int np2value, int np2minvalue, final int num) {
        final AlertDialog.Builder information_dialog = new AlertDialog.Builder(getContext());
        View view=getView().inflate(getActivity(),R.layout.dimension_dialog, null);
//        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
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
                    tv_writes_chu_weight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue())); //set the value to textview
                    tv_writes_chu_weight.setError(null);
                }
                else if (num==1)
                {
                    tv_retestWrites_nowweight.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                    tv_retestWrites_nowweight.setError(null);
                }
                else if (num==2)
                {
                    tv_retestWritest_tizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

                }
                else if(num==3)
                {
                    tv_retestWritest_neizhi.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));

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
        if (TextUtils.isEmpty(retestWrite.getImage()))
        {
            Util.toastMsg("请上传照片");
        }
        else {
            retestWrite.setWeight(tv_retestWrites_nowweight.getText() + "");
            retestWrite.setInitWeight(tv_writes_chu_weight.getText() + "");
            retestWrite.setPysical(tv_retestWritest_tizhi.getText() + "");
            retestWrite.setFat(tv_retestWritest_neizhi.getText() + "");
            retestWrite.setAccountId(loginid + "");
            Log.i(retestWrite.getImage() + "");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("正在保存...");
            progressDialog.show();
            retestPre.doPostWrite(Long.parseLong(loginid), Long.parseLong(loginid), retestWrite, getContext(), progressDialog);
        }


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }
    @Override
    protected void lazyLoad() {
        Log.i("FuCeFragment 加载数据");
    }
}
