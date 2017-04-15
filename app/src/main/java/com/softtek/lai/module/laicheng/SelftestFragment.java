package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.more.view.FuceAlbumActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.presenter.SelftestPresenter;
import com.softtek.lai.widgets.FuCeSelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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
    @InjectView(R.id.tv_health_report)
    TextView mHealthReport;
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
        mWeightCaption.setVisibility(View.INVISIBLE);
        mShare.setVisibility(View.INVISIBLE);
        mHealthReport.setVisibility(View.INVISIBLE);
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
            mWeight.setText(data.getWeight() + "");
            mWeightCaption.setText(data.getBodyTypeTitle());
            mWeightCaption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
            mBodyFatRate.setText(data.getBodyFatRate() + "%");
            mBmi.setText(data.getBMI() + "");
            mInternalFatRate.setText(data.getViscusFatIndex() + "%");
        }
        mWeightCaption.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        mHealthReport.setVisibility(View.VISIBLE);

    }

    @SuppressLint("SetTextI18n")
    public void refreshUi(LastInfoData data) {
        mWeight.setText("0.0");
        mBodyFatRate.setText("- -");
        mBmi.setText("- -");
        mInternalFatRate.setText("- -");
        if (data == null) {
            mWeightBottom.setText("0.0");
            mBodyFatBottom.setText("- -");
            mBodyBmiBottom.setText("- -");
            mInternalFatRateBottom.setText("- -");
            mLastTime.setText("上次测量：");
        } else {
            mWeightBottom.setText(data.getWeight() + "");
            if (data.getBodyFatRate() != null) {
                mBodyFatBottom.setText(data.getBodyFatRate());
            } else {
                mBodyFatBottom.setText("- -");
            }
            if (data.getBMI() != null) {
                mBodyBmiBottom.setText(data.getBMI());
            } else {
                mBodyBmiBottom.setText("- -");
            }
            if (data.getViscusFatIndex() != null) {
                mInternalFatRateBottom.setText(data.getViscusFatIndex());
            } else {
                mInternalFatRateBottom.setText("- -");
            }
            if (data.getMeasuredTime() != null) {
                mLastTime.setText("上次测量：" + data.getMeasuredTime());
            } else {
                mLastTime.setText("上次测量：");
            }
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

    FuCeSelectPicPopupWindow menuWindow;

    @OnClick(R.id.tv_share)
    public void share() {
        menuWindow = new FuCeSelectPicPopupWindow(getActivity(), itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(getActivity().findViewById(R.id.Re_pers_page), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            setShare(v);

        }
    };

    String value;
    String url;
    String title_value;

    private void setShare(View v) {
        switch (v.getId()) {
            case R.id.lin_weixin:
                new ShareAction(getActivity())
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTitle(title_value)
                        .withText(value)
                        .withTargetUrl(url)
                        .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                        .share();
                break;
            case R.id.lin_circle:
                new ShareAction(getActivity())
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle(title_value)
                        .withText(value)
                        .withTargetUrl(url)
                        .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                        .share();
                break;
            case R.id.lin_sina:
                new ShareAction(getActivity())
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(value + url)
                        .withMedia(new UMImage(getActivity(), R.drawable.img_share_logo))
                        .share();
                break;
            default:
                break;
        }
    }
}
