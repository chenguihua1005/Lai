package com.softtek.lai.module.laicheng_new.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.healthyreport.HistoryDataActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.net.BleService;
import com.softtek.lai.utils.RequestCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewSelfFragment extends Fragment implements View.OnClickListener {
    private static final String ARGUMENTS = "mainFragment";
    private View mView;
    private TextView mBleState;
    private ImageView mVoiceSwitch;
    private TextView mWeightCaption;
    private LinearLayout mInfoDataContent;
    private TextView mBodyFatRate;
    private TextView mBmi;
    private TextView mInternalFatRate;
    private TextView mWeight;
    private TextView mWeightBottom;
    private TextView mBodyFatBottom;
    private TextView mBodyBmiBottom;
    private TextView mInternalFatRateBottom;
    private TextView mShare;
    private TextView mHealthReport;
    private TextView mLastTime;
    private LinearLayout mReadMore;
    private ImageView mBleIcon;

    private StartLinkListener linkListener;
    private String recordId;

    private String weight = "";
    private String bodyFatRate = "";
    private String bodyAge = "";
    private String value = "";

    private Dialog dialog;//分享对话框

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(getActivity(), R.layout.fragment_self_new, null);
        initView();
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartLinkListener) {
            linkListener = (StartLinkListener) context;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_info_state:
                if (linkListener != null) {
                    linkListener.onLinkListener();
                }
                break;
            case R.id.iv_voice:
                if (NewLaiBalanceActivity.isVoiceHelp) {
                    NewLaiBalanceActivity.isVoiceHelp = false;
                    mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
                } else {
                    NewLaiBalanceActivity.isVoiceHelp = true;
                    mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
                }
                break;
            case R.id.ll_more:
                startActivity(new Intent(getActivity(), HistoryDataActivity.class).putExtra("accountId", UserInfoModel.getInstance().getUserId()));
                break;
            case R.id.tv_health_report:
                Intent intent_health = new Intent(getActivity(), HealthyReportActivity.class);
                intent_health.putExtra("isVisitor", HealthyReportActivity.NON_VISITOR);
                intent_health.putExtra("reportId", recordId);
                intent_health.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                startActivity(intent_health);
                break;
            case R.id.tv_share:
                showDialog();
                break;
        }
    }

    public interface StartLinkListener {
        void onLinkListener();
    }

    private void initView() {
        mBleState = (TextView) mView.findViewById(R.id.tv_info_state);
        mBleState.setOnClickListener(this);
        mVoiceSwitch = (ImageView) mView.findViewById(R.id.iv_voice);
        mVoiceSwitch.setOnClickListener(this);
        mWeightCaption = (TextView) mView.findViewById(R.id.tv_weight_caption);
        mInfoDataContent = (LinearLayout) mView.findViewById(R.id.ll_info_data);
        mBodyFatRate = (TextView) mView.findViewById(R.id.tv_body_fat_rate);
        mBmi = (TextView) mView.findViewById(R.id.tv_bmi);
        mInternalFatRate = (TextView) mView.findViewById(R.id.tv_internal_fat_rate);
        mWeight = (TextView) mView.findViewById(R.id.tv_weight);
        mWeightBottom = (TextView) mView.findViewById(R.id.tv_weight_bottom);
        mBodyFatBottom = (TextView) mView.findViewById(R.id.tv_body_fat_bottom);
        mBodyBmiBottom = (TextView) mView.findViewById(R.id.tv_bmi_bottom);
        mInternalFatRateBottom = (TextView) mView.findViewById(R.id.tv_internal_fat_rate_bottom);
        mShare = (TextView) mView.findViewById(R.id.tv_share);
        mShare.setOnClickListener(this);
        mHealthReport = (TextView) mView.findViewById(R.id.tv_health_report);
        mHealthReport.setOnClickListener(this);
        mLastTime = (TextView) mView.findViewById(R.id.tv_time);
        mReadMore = (LinearLayout) mView.findViewById(R.id.ll_more);
        mReadMore.setOnClickListener(this);
        mBleIcon = (ImageView) mView.findViewById(R.id.iv_ble_icon);


        ZillaApi.NormalRestAdapter.create(BleService.class).
                getLastData(UserInfoModel.getInstance().getToken(), 1, new RequestCallback<ResponseData<LastInfoData>>() {
                    @Override
                    public void success(ResponseData<LastInfoData> data, Response response) {
                        refreshUi(data.getData());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        refreshUi(null);
                    }
                });

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        mWeight.setTypeface(tf);
    }

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

    public static NewSelfFragment newInstance(@Nullable BleMainData data) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENTS, data);
        NewSelfFragment fragment = new NewSelfFragment();
        fragment.setArguments(arguments);
        return fragment;
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

    public void refreshVoiceIcon() {
        if (NewLaiBalanceActivity.isVoiceHelp) {
            mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        } else {
            mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }

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

    String url = AddressManager.get("shareHost") + "ShareLastRecord?type=1&accountId=" + UserInfoModel.getInstance().getUserId();
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
                    dialogDismiss();
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
                    dialogDismiss();
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
                    dialogDismiss();
                }
            });
            dialog.findViewById(R.id.dialog_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDismiss();
                }
            });
            dialog.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDismiss();
                }
            });
        }
        dialog.show();
    }

    private void dialogDismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
