package com.softtek.lai.module.healthyreport.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthyreport.HealthEntryActivity;
import com.softtek.lai.module.healthyreport.model.BodyDimensionPost;
import com.softtek.lai.module.healthyreport.model.BodyDimensions;
import com.softtek.lai.module.healthyreport.model.HealthyItem;
import com.softtek.lai.module.healthyreport.net.HealthyRecordService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/7/2017.
 */

public class HealthyReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HealthyItem> items;
    private List<BodyDimensions> bodyDimensions;
    private BodyDimensionPost postData = new BodyDimensionPost();
    private OnItemClickListener listener;
    private OnScrollListener scrollListener;
    private boolean isVisitor;
    private static final int TYPE_MAIN = 1;
    private static final int TYPE_FEET = 2;
    private String reportId;
    private int sourceType;

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public HealthyReportAdapter(String reportId, List<HealthyItem> items, List<BodyDimensions> bodyDimensions, Context context, boolean isVisitor, OnScrollListener onScrollListener) {
        this.items = items;
        this.bodyDimensions = bodyDimensions;
        this.context = context;
        this.isVisitor = isVisitor;
        this.scrollListener = onScrollListener;
        this.reportId = reportId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myHolder = null;
        switch (viewType) {
            case TYPE_MAIN:
                myHolder = new HealthyReportHolder(LayoutInflater.from(context).inflate(R.layout.healthy_item, parent, false));
                break;
            case TYPE_FEET:
                myHolder = new GirthHolder(LayoutInflater.from(context).inflate(R.layout.girth_item, parent, false));
                break;
        }
        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HealthyReportAdapter.HealthyReportHolder) {
            HealthyItem item = items.get(position);
            ((HealthyReportHolder) holder).tv_name.setText(item.getTitle());
            ((HealthyReportHolder) holder).tv_standard.setText(item.getCaption());
            if (!TextUtils.isEmpty(item.getColor())) {
                if (item.getColor().startsWith("#")) {
                    ((HealthyReportHolder) holder).tv_standard.setTextColor(Color.parseColor(item.getColor()));
                } else {
                    ((HealthyReportHolder) holder).tv_standard.setTextColor(Color.parseColor("#" + item.getColor()));
                }
            }
            if (isVisitor) {
                //holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                ((HealthyReportHolder) holder).tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
            } else {
                ((HealthyReportHolder) holder).tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
            }
            if (!TextUtils.isEmpty(item.getValue())) {
                SpannableString ss = new SpannableString(item.getValue() + item.getUnit());
                ss.setSpan(new AbsoluteSizeSpan(36), item.getValue().length(), ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if ("kg/m2".equals(item.getUnit())) {
                    ss.setSpan(new SuperscriptSpan(), ss.length() - 1, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                ((HealthyReportHolder) holder).tv_num.setText(ss);
            }
            ((HealthyReportHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            if (items.get(position).getTitle().equals("身体年龄") && getSourceType() == 6) {
                ((HealthyReportHolder) holder).mRemark.setVisibility(View.VISIBLE);
            }else {
                ((HealthyReportHolder) holder).mRemark.setVisibility(View.GONE);
            }
        } else if (holder instanceof HealthyReportAdapter.GirthHolder) {
            final GirthHolder girthHolder = (GirthHolder) holder;
            for (int i = 0; i < bodyDimensions.size(); i++) {
                if (bodyDimensions.get(i).getParamName().equals("胸围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mCircum.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setCircum(bodyDimensions.get(i).getValue());
                } else if (bodyDimensions.get(i).getParamName().equals("腰围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mWaistline.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setWaistline(bodyDimensions.get(i).getValue());
                } else if (bodyDimensions.get(i).getParamName().equals("臀围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mHipline.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setHiplie(bodyDimensions.get(i).getValue());
                } else if (bodyDimensions.get(i).getParamName().equals("上臀围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mUparmgirth.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setUpArmGirth(bodyDimensions.get(i).getValue());
                } else if (bodyDimensions.get(i).getParamName().equals("大腿围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mUpleggirth.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setUpLegGirth(bodyDimensions.get(i).getValue());
                } else if (bodyDimensions.get(i).getParamName().equals("小腿围") && bodyDimensions.get(i).getValue() != 0) {
                    girthHolder.mDoleggirth.setText(bodyDimensions.get(i).getValue() + bodyDimensions.get(i).getUnit());
                    postData.setDoLegGirth(bodyDimensions.get(i).getValue());
                }

            }
            girthHolder.mExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (girthHolder.mGirthContent.getVisibility() == View.VISIBLE) {
                        girthHolder.mGirthContent.setVisibility(View.GONE);
                        girthHolder.mExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_down));
                    } else {
                        girthHolder.mGirthContent.setVisibility(View.VISIBLE);
                        scrollListener.doScrollListener(position);
                        girthHolder.mExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_up));
                    }
                }
            });
        }
    }

    public interface OnScrollListener {
        void doScrollListener(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FEET;
        } else {
            return TYPE_MAIN;
        }
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size() == 0 ? 0 : items.size() + 1;
    }

    public class HealthyReportHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_num;
        TextView tv_standard;
        RelativeLayout rl_item;
        TextView mRemark;


        public HealthyReportHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.num);
            tv_standard = itemView.findViewById(R.id.tv_standard);
            rl_item = itemView.findViewById(R.id.rl_item);
            mRemark = itemView.findViewById(R.id.tv_remark);
        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class GirthHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mExpand;
        private LinearLayout mGirthContent;

        TextView mCircum;
        TextView mWaistline;
        TextView mHipline;
        TextView mUparmgirth;
        TextView mUpleggirth;
        TextView mDoleggirth;

        RelativeLayout mCircumContent;
        RelativeLayout mWaistlineContent;
        RelativeLayout mHiplineContent;
        RelativeLayout mUparmgirthContent;
        RelativeLayout mUpleggirthContent;
        RelativeLayout mDoleggirthContent;

        Button mSubmit;

        public GirthHolder(View itemView) {
            super(itemView);
            mExpand = itemView.findViewById(R.id.iv_expand);
            mGirthContent = itemView.findViewById(R.id.ll_girth);

            mCircum = itemView.findViewById(R.id.tv_circum);
            mWaistline = itemView.findViewById(R.id.tv_waistline);
            mHipline = itemView.findViewById(R.id.tv_hiplie);
            mUparmgirth = itemView.findViewById(R.id.tv_uparmgirth);
            mUpleggirth = itemView.findViewById(R.id.tv_upleggirth);
            mDoleggirth = itemView.findViewById(R.id.tv_doleggirth);

            mCircumContent = itemView.findViewById(R.id.rl_circum);
            mWaistlineContent = itemView.findViewById(R.id.rl_waistline);
            mHiplineContent = itemView.findViewById(R.id.rl_hiplie);
            mUparmgirthContent = itemView.findViewById(R.id.rl_uparmgirth);
            mUpleggirthContent = itemView.findViewById(R.id.rl_upleggirth);
            mDoleggirthContent = itemView.findViewById(R.id.rl_doleggirth);
            mSubmit = itemView.findViewById(R.id.btn_submit);

            mCircumContent.setOnClickListener(this);
            mWaistlineContent.setOnClickListener(this);
            mHiplineContent.setOnClickListener(this);
            mUparmgirthContent.setOnClickListener(this);
            mUpleggirthContent.setOnClickListener(this);
            mDoleggirthContent.setOnClickListener(this);
            mSubmit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_circum:
                    createDialog("选择胸围", 50, 200, 90, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mCircum.setText(String.valueOf(value) + "cm");
                            postData.setCircum(value);
                        }
                    }).show();
                    break;
                case R.id.rl_waistline:
                    createDialog("选择腰围", 40, 200, 80, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mWaistline.setText(String.valueOf(value) + "cm");
                            postData.setWaistline(value);
                        }
                    }).show();
                    break;
                case R.id.rl_hiplie:
                    createDialog("选择臀围", 50, 250, 90, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mHipline.setText(String.valueOf(value) + "cm");
                            postData.setHiplie(value);
                        }
                    }).show();
                    break;
                case R.id.rl_uparmgirth:
                    createDialog("选择上臂围", 10, 70, 50, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mUparmgirth.setText(String.valueOf(value) + "cm");
                            postData.setUpArmGirth(value);
                        }
                    }).show();
                    break;
                case R.id.rl_upleggirth:
                    createDialog("选择大腿围", 10, 90, 50, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mUpleggirth.setText(String.valueOf(value) + "cm");
                            postData.setUpLegGirth(value);
                        }
                    }).show();
                    break;
                case R.id.rl_doleggirth:
                    createDialog("选择小腿围", 10, 70, 50, new HealthEntryActivity.DoSelectedListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(float value) {
                            mDoleggirth.setText(String.valueOf(value) + "cm");
                            postData.setDoLegGirth(value);
                        }
                    }).show();
                    break;
                case R.id.btn_submit:
                    postData.setRecordId(reportId);
                    ZillaApi.NormalRestAdapter.create(HealthyRecordService.class).saveBodyDimension(UserInfoModel.getInstance().getToken(), postData, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                            Toast.makeText(context, responseData.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    }

    private AlertDialog createDialog(String title, int min, int max, int defaultValue, final HealthEntryActivity.DoSelectedListener listener) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = view.findViewById(R.id.numberPicker2);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np1.setMaxValue(max);
        np1.setValue(defaultValue);
        np1.setMinValue(min);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        return dialog.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    int v1 = np1.getValue();
                    listener.onClick(v1 + Float.valueOf("0." + np2.getValue()));
                }

            }
        }).setNegativeButton("取消", null).create();
    }
}