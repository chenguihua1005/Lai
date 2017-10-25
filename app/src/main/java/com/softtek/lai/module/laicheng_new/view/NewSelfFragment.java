package com.softtek.lai.module.laicheng_new.view;

import android.content.Context;
import android.content.Intent;
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
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.net.BleService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

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
    private TextView mReadMore;

    private StartLinkListener linkListener;


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
            case R.id.tv_history_record:
                Intent intent = new Intent(getActivity(), HealthyReportActivity.class);
                intent.putExtra("isVisitor", HealthyReportActivity.NON_VISITOR);
                intent.putExtra("reportId", "");
                intent.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                startActivity(intent);
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
        mWeightCaption = (TextView)mView.findViewById(R.id.tv_weight_caption);
        mInfoDataContent = (LinearLayout)mView.findViewById(R.id.ll_info_data);
        mBodyFatRate = (TextView)mView.findViewById(R.id.tv_body_fat_rate);
        mBmi = (TextView)mView.findViewById(R.id.tv_bmi);
        mInternalFatRate = (TextView)mView.findViewById(R.id.tv_internal_fat_rate);
        mWeight = (TextView)mView.findViewById(R.id.tv_weight);
        mWeightBottom = (TextView)mView.findViewById(R.id.tv_weight_bottom) ;
        mBodyFatBottom = (TextView)mView.findViewById(R.id.tv_body_fat_bottom);
        mBodyBmiBottom = (TextView)mView.findViewById(R.id.tv_bmi_bottom);
        mInternalFatRateBottom = (TextView)mView.findViewById(R.id.tv_internal_fat_rate_bottom);
        mShare = (TextView)mView.findViewById(R.id.tv_share);
        mHealthReport = (TextView)mView.findViewById(R.id.tv_health_report);
        mLastTime = (TextView)mView.findViewById(R.id.tv_time);
        mReadMore = (TextView)mView.findViewById(R.id.tv_history_record);
        mReadMore.setOnClickListener(this);

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

    public void refreshVoiceIcon() {
        if (NewLaiBalanceActivity.isVoiceHelp) {
            mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_on));
        } else {
            mVoiceSwitch.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
        }
    }
}
