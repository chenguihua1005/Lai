package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.presenter.VisitortestPresenter;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_selftest)
public class SelftestFragment extends LazyBaseFragment implements VisitortestPresenter.VisitortestView {
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

    private VisitortestPresenter presenter;

    private boolean isPlay = true;

    @Override
    public void getLastInfoSuccess(BleMainData data) {
        refreshUi();
    }

    @Override
    public void getLastInfoFailed() {
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
        presenter.getLastInfo();
    }

    @Override
    protected void initViews() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        mWeight.setTypeface(tf);
        presenter = new VisitortestPresenter(this);

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
    public void onShareClick(){

    }

    @SuppressLint("SetTextI18n")
    public void updateUI(BleMainData data) {
        mWeight.setText(data.getWeight_item().getValue() + "");
        mWeightCaption.setText(data.getWeight_con().getCaption());
//        mWeightCaption.setTextColor(Color.parseColor("#" + data.getWeight_item().getColor()));
        mBodyFatRate.setText(data.getBodyfatrate() + "%");
        mBmi.setText(data.getBmi() + "");
        mInternalFatRate.setText(data.getVisceralfatindex() + "%");
        mWeightBottom.setText(data.getWeight_item().getValue() + "");
        mBodyFatBottom.setText(data.getBodyfat() + "");
        mBodyBmiBottom.setText(data.getBmi() + "");
        mInternalFatRateBottom.setText(data.getVisceralfatindex() + "%");
    }

    public void refreshUi(){
        mWeight.setText("0.0");
        mBodyFatRate.setText("- -");
        mBmi.setText("- -");
        mInternalFatRate.setText("- -");
    }

    public void setStateTip(String state) {
        if (mBleState != null) {
            mBleState.setText(state);
        }
    }

    public void refreshVoiceIcon(){
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
        }else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }
}
