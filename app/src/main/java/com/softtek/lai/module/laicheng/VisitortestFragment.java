package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.view.FuceAlbumActivity;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.GetVisitorModel;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.presenter.VisitGetPresenter;
import com.softtek.lai.widgets.FuCeSelectPicPopupWindow;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static android.app.Activity.RESULT_OK;

@InjectLayout(R.layout.fragment_visitortest)
public class VisitortestFragment extends LazyBaseFragment<VisitGetPresenter> implements VisitGetPresenter.VisitGetView, View.OnClickListener {
    private VisitortestFragment.VisitorVoiceListener listener;
    private VisitortestFragment.ShakeOFF shakeOFF;

    @InjectView(R.id.bt_again)
    Button bt_again;
    private LinearLayout.LayoutParams parm;
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

    FuCeSelectPicPopupWindow menuWindow;

    VisitorModel model;
    VisitGetPresenter presenter;
    //    private boolean isPlay = true;
    private String recordId;

    public VisitortestFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        presenter.GetData(UserInfoModel.getInstance().getToken(), 0);
    }

    //第一次进入为访客测获取最新访客测量信息
    @Override
    public void getDatasuccess(LastInfoData data) {
        if (data != null && !TextUtils.isEmpty(data.getRecordId())) {
            recordId = data.getRecordId();
            mid_lay.setVisibility(View.VISIBLE);
            tv_weight.setText(data.getWeight() + "");//体重
            tv_weight_caption.setText(data.getBodyTypeTitle());//状态
            tv_body_fat_rate.setText(data.getBodyFatRate());
            tv_bmi.setText(data.getBMI());
            tv_internal_fat_rate.setText(data.getViscusFatIndex());
            if (data.getVisitor() != null) {
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
            }
            shakeOFF.setOnShakeOFF();

        } else {
            mid_lay.setVisibility(View.INVISIBLE);
            ll_visitor.setVisibility(View.INVISIBLE);
            tv_weight.setText("0.0");//体重
            tv_weight_caption.setText("--");//状态
            tv_body_fat_rate.setText("--");
            tv_bmi.setText("--");
            tv_internal_fat_rate.setText("--");
            shakeOFF.setOnShakeSTOP();
        }
    }

    public interface VisitorVoiceListener {
        void onVisitorVoiceListener();
    }

    public interface ShakeOFF {
        void setOnShakeOFF();

        void setOnShakeSTOP();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VisitortestFragment.VisitorVoiceListener) {
            listener = (VisitortestFragment.VisitorVoiceListener) context;
            shakeOFF = (VisitortestFragment.ShakeOFF) context;
        }
    }

    @Override
    protected void initViews() {
        bt_create.setOnClickListener(this);
        bt_history.setOnClickListener(this);
        health_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        presenter = new VisitGetPresenter(this);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        tv_weight.setTypeface(tf);
    }


    //语音
    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
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
                startActivityForResult(in, 0);
                break;
            case R.id.bt_history:
                Intent intent = new Intent(getActivity(), VisithistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.health_btn:
                Intent health = new Intent(getContext(), HealthyReportActivity.class);
                health.putExtra("reportId", recordId);
                health.putExtra("since", SINCE_LAICHEN);
                health.putExtra("isVisitor", VISITOR);
                startActivity(health);
                break;
            case R.id.share_btn:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    com.github.snowdream.android.util.Log.i("检查权限。。。。");
                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                    if (
                            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //允许弹出提示
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);

                    } else {
                        //不允许弹出提示
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                LOCATION_PREMISSION);
                    }
                } else {

                    menuWindow = new FuCeSelectPicPopupWindow(getActivity(), itemsOnClick);
                    //显示窗口
                    menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    menuWindow.showAtLocation(getActivity().findViewById(R.id.rl_visit), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                    menuWindow.setBackgroundDrawable(new BitmapDrawable());
                }
                break;
        }
    }


    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
//            setShare(v);

        }
    };

/*    @Override
    public void onResume() {
        super.onResume();
        shakeOFF.setOnShakeSTOP();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                model = (VisitorModel) data.getParcelableExtra("visitorModel");
                if (model != null && !TextUtils.isEmpty(model.getName())) {
                    Log.i("访客信息", model.toString());
                    ll_visitor.setVisibility(View.VISIBLE);
                    tv_name.setText(model.getName());
                    tv_phoneNo.setText(model.getPhoneNo());
                    tv_age.setText(model.getBirthDate());
                    if (0 == model.getGender()) {
                        tv_gender.setText("男");
                    } else {
                        tv_gender.setText("女");
                    }
                    tv_height.setText(model.getHeight() + "");
                    shakeOFF.setOnShakeOFF();
                } else {
                    shakeOFF.setOnShakeSTOP();
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void UpdateData(BleMainData data) {
        recordId = data.getRecordId();
        tv_weight.setText(data.getWeight() + "");//体重
        tv_weight_caption.setText(data.getBodyTypeTitle());//状态
        tv_weight_caption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
        tv_body_fat_rate.setText(data.getBodyFatRate());
        tv_bmi.setText(data.getBMI());
        tv_internal_fat_rate.setText(data.getViscusFatIndex());
    }

    public void refreshVoiceIcon() {
        if (MainBaseActivity.isVoiceHelp) {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        } else {
            iv_voice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }

    public void setStateTip(String state) {
        mBleState.setText(state);
    }
}
