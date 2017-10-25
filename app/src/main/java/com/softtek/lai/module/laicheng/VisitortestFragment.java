package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.presenter.VisitGetPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_visitortest)
public class VisitortestFragment extends LazyBaseFragment<VisitGetPresenter> implements VisitGetPresenter.VisitGetView, View.OnClickListener {
    private VisitortestFragment.VisitorVoiceListener listener;
    private ShakeSwitch shakeOFF;

    @InjectView(R.id.bt_again)
    Button bt_again;
    private LinearLayout.LayoutParams parm;
    private StartVisitorLinkListener linkListener;

    @InjectView(R.id.tv_weight)
    TextView tv_weight;//体重
    @InjectView(R.id.tv_weight_caption)
    TextView tv_weight_caption;//状态
    @InjectView(R.id.tv_body_fat_rate)
    TextView tv_body_fat_rate;//体脂率
    @InjectView(R.id.tv_bmi)
    TextView tv_bmi;//BMI;
    @InjectView(R.id.tv_internal_fat_rate)
    TextView tv_internal_fat_rate;//内脂率
    @InjectView(R.id.iv_voice)
    ImageView iv_voice;
    @InjectView(R.id.tv_info_state)
    TextView mBleState;

    @InjectView(R.id.health_btn)
    Button health_btn;
    @InjectView(R.id.share_btn)
    Button share_btn;
    @InjectView(R.id.bt_create)
    Button bt_create;//
    @InjectView(R.id.bt_history)
    Button bt_history;

    //访客信息
    @InjectView(R.id.ll_visitor)
    LinearLayout ll_visitor;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phoneNo)
    TextView tv_phoneNo;
    @InjectView(R.id.tv_age)
    TextView tv_age;
    @InjectView(R.id.tv_gender)
    TextView tv_gender;
    @InjectView(R.id.tv_height)
    TextView tv_height;
    @InjectView(R.id.mid_lay)
    LinearLayout mid_lay;

    VisitorModel model;
    VisitGetPresenter presenter;
    //    private boolean isPlay = true;
    private String recordId;
    private Dialog dialog;//对话框
    private long visitorId;

    private String weight = "";//体重
    private String bodyFatRate = "";//体脂率
    private String bodyAge = "";//身体年龄

    private AlertDialog.Builder builder;

    private AlertDialog.Builder noVisitorBuilder;

    public VisitortestFragment() {
        // Required empty public constructor
    }

    public LastInfoData visitorLastData;

    @Override
    protected void lazyLoad() {

        presenter.GetData(UserInfoModel.getInstance().getToken(), 0);
    }

    public interface StartVisitorLinkListener {
        void onLinkVisitorListener();
    }

    //第一次进入为访客测获取最新访客测量信息
    @Override
    public void getDatasuccess(LastInfoData data) {
        if (isDetached()) {
            return;
        }
        visitorLastData = data;
        if (data != null && !TextUtils.isEmpty(data.getRecordId())) {
            tv_weight_caption.setVisibility(View.VISIBLE);
            recordId = data.getRecordId();
            mid_lay.setVisibility(View.VISIBLE);
            weight = String.valueOf(data.getWeight());
            bodyFatRate = data.getBodyFatRate();
            bodyAge = data.getPhysicalAge();
            tv_weight.setText(data.getWeight() + "");//体重
            tv_weight_caption.setText(data.getBodyTypeTitle());//状态
            tv_body_fat_rate.setText(data.getBodyFatRate() + "%");
            tv_bmi.setText(data.getBMI());
            tv_internal_fat_rate.setText(data.getViscusFatIndex());
            if (data.getVisitor() != null) {
                model = new VisitorModel();
                model.setName(data.getVisitor().getName());
                model.setBirthDate(data.getVisitor().getBirthDate());
                model.setGender(data.getVisitor().getGender());
                model.setHeight(data.getVisitor().getHeight());
                model.setPhoneNo(data.getVisitor().getPhoneNo());
                model.setVisitorId(data.getVisitor().getId());
                visitorId = data.getVisitor().getId();
                ll_visitor.setVisibility(View.VISIBLE);
                tv_name.setText(data.getVisitor().getName());
                tv_phoneNo.setText(data.getVisitor().getPhoneNo());
                tv_age.setText(data.getVisitor().getAge() + "");
                if (0 == data.getVisitor().getGender()) {
                    tv_gender.setText("男");
                } else {
                    tv_gender.setText("女");
                }
                tv_height.setText(data.getVisitor().getHeight() + "");
                Log.i("model访客", model.toString() + model.getVisitorId());
            }
            shakeOFF.setOnShakeON();

        } else {
            mid_lay.setVisibility(View.INVISIBLE);
            ll_visitor.setVisibility(View.INVISIBLE);
            tv_weight.setText("0.0");//体重
            tv_weight_caption.setVisibility(View.INVISIBLE);//状态
            tv_body_fat_rate.setText("--");
            tv_bmi.setText("--");
            tv_internal_fat_rate.setText("--");

        }
    }

    public interface VisitorVoiceListener {
        void onVisitorVoiceListener();
    }

    public interface ShakeSwitch {
        void setOnShakeON();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VisitortestFragment.VisitorVoiceListener) {
            listener = (VisitortestFragment.VisitorVoiceListener) context;
            shakeOFF = (ShakeSwitch) context;
        }
        if (context instanceof StartVisitorLinkListener) {
            linkListener = (StartVisitorLinkListener) context;
        }
    }

    @Override
    protected void initViews() {
        bt_create.setOnClickListener(this);
        bt_history.setOnClickListener(this);
        health_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        mBleState.setOnClickListener(this);
    }

    VisitorBroadCast visitorBroadCast;
    LocalBroadcastManager manager;

    @Override
    protected void initDatas() {
        presenter = new VisitGetPresenter(this);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        tv_weight.setTypeface(tf);
        manager = LocalBroadcastManager.getInstance(getContext());
        visitorBroadCast = new VisitorBroadCast();
        manager.registerReceiver(visitorBroadCast, new IntentFilter("visitorinfo"));
    }


    //语音
    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        }
        if (listener != null) {
            listener.onVisitorVoiceListener();
        }
    }

    public VisitorModel getVisitorModel() {
        return model;
    }

    private static final int LOCATION_PREMISSION = 100;
    private static final int SINCE_LAICHEN = 1;//莱称来源
    private static final int VISITOR = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_create:
                Intent in = new Intent(getActivity(), VisitorinfoActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);
                break;
            case R.id.bt_history:
                Intent intent = new Intent(getActivity(), VisithistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.health_btn:
                Intent health = new Intent(getContext(), HealthyReportActivity.class);
                health.putExtra("reportId", recordId);
                health.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                health.putExtra("isVisitor", HealthyReportActivity.VISITOR);
                startActivity(health);
                break;
            case R.id.share_btn:
                showDialog();
                break;
            case R.id.tv_info_state:
                linkListener.onLinkVisitorListener();
                break;

        }
    }

    String value;
    String url;
    String title_value = "莱聚+体测，精彩人生";

    //分享对话框
    private void showDialog() {
        url = AddressManager.get("shareHost") + "ShareLastRecord?type=0&accountId=" + visitorId;
        value = "体重 " + "+" + weight + "斤" + "\n" + "体脂率 " + "+" + bodyFatRate + "\n" + "身体年龄 " + "+" + bodyAge;
        if (dialog == null) {
            dialog = new Dialog(getActivity(), R.style.custom_dialog);
            dialog.setCanceledOnTouchOutside(true);
            Window win = dialog.getWindow();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.x = 120;
            params.y = 100;
            assert win != null;
            win.setAttributes(params);
            dialog.setContentView(R.layout.share_dialog);
            dialog.findViewById(R.id.ll_weixin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                            .share();
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.ll_circle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                            .share();
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.ll_sina).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                            .share();
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.dialog_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private int close;
    SimpleDateFormat format = new SimpleDateFormat("yyyy");
    int NowYear = Integer.parseInt(format.format(new Date()));
    private int choose_year;


    //摇一摇刷新U
    @SuppressLint("SetTextI18n")
    public void refreshUi(LastInfoData data) {
        tv_weight.setText("0.0");
        tv_body_fat_rate.setText("- -");
        tv_bmi.setText("- -");
        tv_internal_fat_rate.setText("- -");
        mid_lay.setVisibility(View.INVISIBLE);
        tv_weight_caption.setVisibility(View.INVISIBLE);
    }


    @SuppressLint("SetTextI18n")
    public void UpdateData(BleMainData data) {
        if (data != null) {
            tv_weight_caption.setVisibility(View.VISIBLE);
            mid_lay.setVisibility(View.VISIBLE);
            recordId = data.getRecordId();
            bodyAge = data.getPhysicalAge();
            weight = String.valueOf(data.getWeight());


//            tv_weight.setText(data.getWeight() + "");//体重
//            tv_weight_caption.setText(data.getBodyTypeTitle());//状态
//            tv_weight_caption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
//            tv_body_fat_rate.setText(data.getBodyFatRate());
//            tv_bmi.setText(data.getBMI());
//            tv_internal_fat_rate.setText(data.getViscusFatIndex());//内脂
            tv_weight.setText(data.getWeight() + "");
            if (data.getBodyTypeTitle() != null) {
                tv_weight_caption.setText(data.getBodyTypeTitle());
            }
            if (data.getBodyTypeColor() != null) {
                tv_weight_caption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
            }
            if (data.getBodyFatRate() != null) {
                tv_body_fat_rate.setText(data.getBodyFatRate() + data.getBodyFatRateUnit());
            }
            if (data.getBMI() != null) {
                tv_bmi.setText(data.getBMI() + "");
            }
            if (data.getViscusFatIndex() != null) {
                tv_internal_fat_rate.setText(data.getViscusFatIndex());
            }


            bodyFatRate = data.getBodyFatRate();
        }

    }

    public void refreshVoiceIcon() {
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }

    public void setStateTip(String state) {
        if (mBleState != null) {
            mBleState.setText(state);
        }
    }

//    @OnClick(R.id.tv_info_state)
//    public void linkVisitorClick(){
//        linkListener.onLinkVisitorListener();
//    }

    @Override
    public void onDestroyView() {
        manager.unregisterReceiver(visitorBroadCast);
        super.onDestroyView();
    }

    public class VisitorBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("visitorinfo")) {
                model = (VisitorModel) intent.getParcelableExtra("visitorModel");
                choose_year = intent.getExtras().getInt("choose");
                if (model != null && !TextUtils.isEmpty(model.getName())) {
                    ll_visitor.setVisibility(View.VISIBLE);
                    visitorId = model.getVisitorId();
                    tv_name.setText(model.getName());
                    tv_phoneNo.setText(model.getPhoneNo());
                    tv_age.setText((NowYear - choose_year) + "");
                    if (0 == model.getGender()) {
                        tv_gender.setText("男");
                    } else {
                        tv_gender.setText("女");
                    }
                    tv_height.setText(String.valueOf(model.getHeight()));

                    tv_weight.setText(String.valueOf(0.0));
                    tv_weight_caption.setVisibility(View.INVISIBLE);
                    tv_body_fat_rate.setText("- -");
                    mid_lay.setVisibility(View.INVISIBLE);
                    tv_bmi.setText("- -");
                    tv_internal_fat_rate.setText("- -");
                    shakeOFF.setOnShakeON();
                }
            }
        }
    }
}
