package com.softtek.lai.module.laicheng_new.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;
import com.softtek.lai.module.laicheng.VisithistoryActivity;
import com.softtek.lai.module.laicheng.VisitorinfoActivity;
import com.softtek.lai.module.laicheng.VisitortestFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.net.VisitorService;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewVisitorFragment extends Fragment implements View.OnClickListener {
    private static final String ARGUMENTS = "mainFragment";
    private View view;
    private TextView tv_weight;//体重
    private TextView tv_weight_caption;//状态
    private TextView tv_body_fat_rate;//体脂率
    private TextView tv_bmi;//BMI;
    private TextView tv_internal_fat_rate;//内脂率
    private ImageView iv_voice;
    private TextView mBleState;

    private Button health_btn;
    private Button share_btn;
    private Button bt_create;//
    private Button bt_history;
    private Button mCustomerFrom;
    private ImageView mNote;

    //访客信息
    private LinearLayout ll_visitor;
    private TextView tv_name;
    private TextView tv_phoneNo;
    private TextView tv_age;
    private TextView tv_gender;
    private TextView tv_height;
    private LinearLayout mid_lay;
    private ImageView mBleIcon;


    private String recordId;

    private StartVisitorLinkListener linkListener;
    private RenameVisitorListener renameVisitorListener;

    private VisitorModel model;
    private String weight = "";//体重
    private String bodyFatRate = "";//体脂率
    private String bodyAge = "";//身体年龄
    private long visitorId;

    VisitorBroadCast visitorBroadCast;
    LocalBroadcastManager manager;
    SimpleDateFormat format = new SimpleDateFormat("yyyy");
    int NowYear = Integer.parseInt(format.format(new Date()));
    private int choose_year;
    private Dialog dialog;//分享对话框

    private boolean isNeedReconnect = false;

    public boolean isNeedReconnect() {
        return isNeedReconnect;
    }

    public void setNeedReconnect(boolean needReconnect) {
        isNeedReconnect = needReconnect;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_visitor_new, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_weight_caption = view.findViewById(R.id.tv_weight_caption);
        tv_body_fat_rate = view.findViewById(R.id.tv_body_fat_rate);
        tv_bmi = view.findViewById(R.id.tv_bmi);
        tv_internal_fat_rate =  view.findViewById(R.id.tv_internal_fat_rate);
        iv_voice =  view.findViewById(R.id.iv_voice);
        mBleState = view.findViewById(R.id.tv_info_state);
        health_btn =  view.findViewById(R.id.health_btn);
        share_btn = view.findViewById(R.id.share_btn);
        bt_create = view.findViewById(R.id.bt_create);
        bt_history = view.findViewById(R.id.bt_history);

        ll_visitor = view.findViewById(R.id.ll_visitor);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phoneNo = view.findViewById(R.id.tv_phoneNo);
        tv_age = view.findViewById(R.id.tv_age);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_height = view.findViewById(R.id.tv_height);
        mid_lay = view.findViewById(R.id.mid_lay);
        mBleIcon = view.findViewById(R.id.iv_ble_icon);
        mNote = view.findViewById(R.id.iv_note);
        mCustomerFrom = view.findViewById(R.id.bt_from);

        mBleState.setOnClickListener(this);
        bt_history.setOnClickListener(this);
        health_btn.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        iv_voice.setOnClickListener(this);
        mNote.setOnClickListener(this);
        mCustomerFrom.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        tv_weight.setTypeface(tf);
    }

    private void initData() {
        manager = LocalBroadcastManager.getInstance(getContext());
        visitorBroadCast = new NewVisitorFragment.VisitorBroadCast();
        manager.registerReceiver(visitorBroadCast, new IntentFilter("visitorinfo"));

        ZillaApi.NormalRestAdapter.create(VisitorService.class)
                .getData(UserInfoModel.getInstance().getToken(), 0, new Callback<ResponseData<LastInfoData>>() {
                    @Override
                    public void success(ResponseData<LastInfoData> data, Response response) {
                        if (200 == data.getStatus()) {
                            if (data.getData() != null) {
                                getLastData(data.getData());
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ZillaApi.dealNetError(error);
                    }
                });
    }

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
            case R.id.iv_voice:
                if (NewLaiBalanceActivity.isVoiceHelp) {
                    iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
                    NewLaiBalanceActivity.isVoiceHelp = false;
                } else {
                    iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
                    NewLaiBalanceActivity.isVoiceHelp = true;
                }
                break;
            case R.id.iv_note:
                renameVisitorListener.onVRenameListener();
                break;
            case R.id.bt_from:
                startActivity(new Intent(getActivity(),ChooseCustomerAvtivity.class));

        }
    }

    public interface StartVisitorLinkListener {
        void onLinkVisitorListener();
    }

    public interface RenameVisitorListener{
        void onVRenameListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartVisitorLinkListener) {
            linkListener = (StartVisitorLinkListener) context;
        }
        if (context instanceof RenameVisitorListener){
            renameVisitorListener = (RenameVisitorListener)context;
        }
    }

    public static NewVisitorFragment newInstance(@Nullable BleMainData data) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, data);
        NewVisitorFragment fragment = new NewVisitorFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public void refreshVoiceIcon() {
        if (NewLaiBalanceActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }

    public void setClickable(boolean available){
        if (available){
            mBleState.setEnabled(true);
        }else {
            mBleState.setEnabled(false);
        }
    }

    public void setRenameIcon(boolean alive){
        if (alive){
            mNote.setVisibility(View.VISIBLE);
        }else {
            mNote.setVisibility(View.INVISIBLE);
        }
    }

    public void getLastData(LastInfoData data) {
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
                }
                linkListener.onLinkVisitorListener();
                setNeedReconnect(true);
            }
        }
    }

    public void setStateTip(String state) {
        if (mBleState != null) {
            mBleState.setText(state);
        }
    }

    public void setBleIcon(boolean isVisibility) {
        if (isVisibility) {
            mBleIcon.setVisibility(View.VISIBLE);
        } else {
            mBleIcon.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateData(BleMainData data) {
        if (data != null) {
            tv_weight_caption.setVisibility(View.VISIBLE);
            mid_lay.setVisibility(View.VISIBLE);
            recordId = data.getRecordId();
            bodyAge = data.getPhysicalAge();
            weight = String.valueOf(data.getWeight());
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

    public VisitorModel getVisitorModel() {
        return model;
    }

}
