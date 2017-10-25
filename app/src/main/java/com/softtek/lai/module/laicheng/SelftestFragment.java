package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.healthyreport.HistoryDataActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.presenter.SelftestPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_selftest)
public class SelftestFragment extends LazyBaseFragment<SelftestPresenter> implements SelftestPresenter.SelftestView {
    private static final String ARGUMENTS = "mainFragment";
    private VoiceListener listener;
    private StartLinkListener linkListener;

    @InjectView(R.id.tv_weight_caption)
    TextView mWeightCaption;
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

    private Dialog dialog;//对话框

    private String recordId = "";

    private String weight = "";
    private String bodyFatRate = "";
    private String bodyAge = "";
    private String value = "";

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

    public interface StartLinkListener {
        void onLinkListener();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VoiceListener) {
            listener = (SelftestFragment.VoiceListener) context;
        }
        if (context instanceof StartLinkListener){
            linkListener = (StartLinkListener)context;
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
        getPresenter().getLastInfo(1);
    }

    @Override
    protected void initViews() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        mWeight.setTypeface(tf);

        setPresenter(new SelftestPresenter(this));
    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        } else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        }
        if (listener != null) {
            listener.onVoiceListener();
        }
    }

    @OnClick(R.id.tv_info_state)
    public void onLinkClick(){
        if (linkListener != null){
            linkListener.onLinkListener();
        }
    }

    //测量成功更新UI
    @SuppressLint("SetTextI18n")
    public void updateUI(BleMainData data) {
        if (data != null) {
            mWeight.setText(data.getWeight() + "");
            if (data.getBodyTypeTitle() != null) {
                mWeightCaption.setText(data.getBodyTypeTitle());
            }
            if (data.getBodyTypeColor() != null) {
                mWeightCaption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
            }
            if (data.getBodyFatRate() != null) {
                mBodyFatRate.setText(data.getBodyFatRate() + data.getBodyFatRateUnit());
            }
            if (data.getBMI() != null) {
                mBmi.setText(data.getBMI() + "");
            }
            if (data.getViscusFatIndex() != null) {
                mInternalFatRate.setText(data.getViscusFatIndex());
            }

            recordId = data.getRecordId();

            weight = String.valueOf(data.getWeight());
            bodyFatRate = data.getBodyFatRate();
            bodyAge = data.getPhysicalAge();
            value = "体重 " + "+" + weight + "斤" + "\n" + "体脂率 " + "+" + bodyFatRate + "\n" + "身体年龄 " + "+" + bodyAge;
        }
        mWeightCaption.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        mHealthReport.setVisibility(View.VISIBLE);
    }

    //摇一摇刷新U
    @SuppressLint("SetTextI18n")
    public void refreshUi(LastInfoData data) {
        mWeightCaption.setVisibility(View.INVISIBLE);
        mShare.setVisibility(View.INVISIBLE);
        mHealthReport.setVisibility(View.INVISIBLE);
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
            mWeightBottom.setText(data.getWeight() + "斤");
            if (data.getBodyFat() != null) {
                mBodyFatBottom.setText(data.getBodyFatRate() + "%");
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
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        } else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }

    @OnClick(R.id.tv_health_report)
    public void goToHealthReport() {
        Intent intent = new Intent(getActivity(), HealthyReportActivity.class);
        intent.putExtra("isVisitor", HealthyReportActivity.NON_VISITOR);
        intent.putExtra("reportId", recordId);
        intent.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
        startActivity(intent);
    }

    @OnClick(R.id.tv_share)
    public void share() {
        showDialog();
    }

    @OnClick(R.id.ll_more)
    public void goToMoreinfo() {
        startActivity(new Intent(getActivity(), HistoryDataActivity.class).putExtra("accountId",UserInfoModel.getInstance().getUserId()));
    }


    String url = AddressManager.get("shareHost")+"ShareLastRecord?type=1&accountId=" + UserInfoModel.getInstance().getUserId();
    String title_value = "莱聚+体测，精彩人生";

    //分享对话框
    private void showDialog() {
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
                    dismiss();
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
                    dismiss();
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
                    dismiss();
                }
            });
            dialog.findViewById(R.id.dialog_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            dialog.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        dialog.show();
    }

    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
