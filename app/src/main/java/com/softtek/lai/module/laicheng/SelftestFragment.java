package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.presenter.SelftestPresenter;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_selftest)
public class SelftestFragment extends LazyBaseFragment implements SelftestPresenter.SelftestView {
    private static final String ARGUMENTS = "mainFragment";
    private VoiceListener listener;
    @InjectView(R.id.tv_weight_caption)
    TextView mWeightCaption;
    //    @InjectView(R.id.ll_info_state)
//    LinearLayout mBleStateContent;
    @InjectView(R.id.tv_info_state)
    TextView mBleState;
    @InjectView(R.id.ll_info_data)
    LinearLayout mInfoDataContent;
    @InjectView(R.id.tv_body_fat_rate)
    TextView mBodyFatRate;
    @InjectView(R.id.tv_bmi)
    TextView mBmi;
    @InjectView(R.id.tv_internal_fat_rate)
    TextView mInternalFatRate;
    @InjectView(R.id.tv_weight)
    TextView mWeight;
    @InjectView(R.id.tv_weight_bottom)
    TextView mWeightBottom;
    @InjectView(R.id.tv_body_fat_bottom)
    TextView mBodyFatBottom;
    @InjectView(R.id.tv_bmi_bottom)
    TextView mBodyBmiBottom;
    @InjectView(R.id.tv_internal_fat_rate_bottom)
    TextView mInternalFatRateBottom;
    @InjectView(R.id.iv_voice)
    ImageView mVoice;
    @InjectView(R.id.tv_share)
    TextView mShare;
    @InjectView(R.id.tv_time)
    TextView mLastTime;

    private SelftestPresenter presenter;

    @Override
    public void getLastInfoSuccess(LastInfoData data) {
        refreshUi(data);
    }

    @Override
    public void getLastInfoFailed() {
        refreshUi(null);
    }


    public interface VoiceListener {
        void onVoiceListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VoiceListener) {
            listener = (SelftestFragment.VoiceListener) context;
        }
    }

    public static SelftestFragment newInstance(@Nullable BleMainData data) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, data);
        SelftestFragment fragment = new SelftestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        presenter.getLastInfo(1);
    }

    @Override
    protected void initViews() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        mWeight.setTypeface(tf);
        presenter = new SelftestPresenter(this);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        } else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        }
        if (listener != null) {
            listener.onVoiceListener();
        }
    }

    @OnClick(R.id.tv_share)
    public void onShareClick() {

    }

    @SuppressLint("SetTextI18n")
    public void updateUI(BleMainData data) {
        if (data != null) {
            if (data.getWeightUnit().equals("斤")) {
                mWeight.setText(data.getWeight() + "");
            } else if (data.getWeightUnit().equals("公斤")) {
                mWeight.setText(data.getWeight() * 2 + "");
            }
            mWeight.setText(data.getWeight() + "");
            mWeightCaption.setText(data.getBodyTypeTitle());
            mWeightCaption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
            mBodyFatRate.setText(data.getBodyFatRate() + "%");
            mBmi.setText(data.getBMI() + "");
            mInternalFatRate.setText(data.getViscusFatIndex() + "%");
        }
    }

    @SuppressLint("SetTextI18n")
    public void refreshUi(LastInfoData data) {
        mWeight.setText("0.0");
        mBodyFatRate.setText("- -");
        mBmi.setText("- -");
        mInternalFatRate.setText("- -");
        if (data == null){
            mWeight.setText("- -");
            mBodyFatBottom.setText("- -");
            mBodyBmiBottom.setText("- -");
            mInternalFatRateBottom.setText("- -");
            mLastTime.setText("上次测量：");
        }else {
            mWeight.setText(data.getWeight()+"");
            mBodyFatBottom.setText(data.getBodyFatRate());
            mBodyBmiBottom.setText(data.getBMI());
            mInternalFatRateBottom.setText(data.getViscusFatIndex());
            mLastTime.setText("上次测量："+data.getMeasuredTime());
        }
    }

    public void setStateTip(String state) {
        if (mBleState != null) {
            mBleState.setText(state);
        }
    }

    public void refreshVoiceIcon() {
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        } else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }
}
