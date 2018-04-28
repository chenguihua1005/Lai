package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.BodyDimensionsModel;
import com.softtek.lai.module.healthyreport.adapter.HealthyReportAdapter;
import com.softtek.lai.module.healthyreport.model.BodyDimensions;
import com.softtek.lai.module.healthyreport.model.HealthyItem;

import java.util.List;


public class HealthyReportCustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HealthyItem> items;
    private List<BodyDimensionsModel> dimensionsModels;
    private OnItemClickListener listener;
    private boolean isVisitor;
    private static final int TYPE_LIST = 1;
    private static final int TYPE_DIMEN = 2;

    public HealthyReportCustomerAdapter(List<HealthyItem> items, List<BodyDimensionsModel> bodyDimensionsModels, Context context, boolean isVisitor) {
        this.items = items;
        this.dimensionsModels = bodyDimensionsModels;
        this.context = context;
        this.isVisitor = isVisitor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myHolder = null;
        switch (viewType){
            case TYPE_LIST:
                myHolder = new HealthyReportHolder(LayoutInflater.from(context).inflate(R.layout.healthy_item, parent, false));
                break;
            case TYPE_DIMEN:
                myHolder = new DimensionsHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_dimensions, parent, false));
        }
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_LIST) {
            HealthyReportHolder reportHolder = (HealthyReportHolder) holder;
            HealthyItem item = items.get(position);
            reportHolder.tv_name.setText(item.getTitle());
            reportHolder.tv_standard.setText(item.getCaption());
            if (!TextUtils.isEmpty(item.getColor())) {
                if (item.getColor().startsWith("#")) {
                    reportHolder.tv_standard.setTextColor(Color.parseColor(item.getColor()));
                } else {
                    reportHolder.tv_standard.setTextColor(Color.parseColor("#" + item.getColor()));
                }
            }
            if (isVisitor) {
                //holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                reportHolder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
            } else {
                reportHolder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
            }
            if (!TextUtils.isEmpty(item.getValue())) {
                SpannableString ss = new SpannableString(item.getValue() + item.getUnit());
                ss.setSpan(new AbsoluteSizeSpan(36), item.getValue().length(), ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if ("kg/m2".equals(item.getUnit())) {
                    ss.setSpan(new SuperscriptSpan(), ss.length() - 1, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                reportHolder.tv_num.setText(ss);
            } else {
                reportHolder.tv_num.setText("");
            }
            reportHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            if (!item.getTitle().equals("身体年龄")){
                reportHolder.mRemark.setVisibility(View.GONE);
            }else {
                reportHolder.mRemark.setVisibility(View.VISIBLE);
            }
        }else if (getItemViewType(position) == TYPE_DIMEN){
            DimensionsHolder dimensionsHolder = (DimensionsHolder)holder;
            dimensionsHolder.mName.setText(dimensionsModels.get(position - items.size()).getParamName());
            dimensionsHolder.mDimension.setText(dimensionsModels.get(position - items.size()).getValue() + dimensionsModels.get(position - items.size()).getUnit());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position <= items.size() -1) {
            return TYPE_LIST;
        }else {
            return TYPE_DIMEN;
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
        return items.size() + dimensionsModels.size();
    }

    public class HealthyReportHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_num;
        TextView tv_standard;
        TextView mRemark;
        RelativeLayout rl_item;

        public HealthyReportHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.num);
            mRemark = itemView.findViewById(R.id.tv_remark);
            tv_standard = (TextView) itemView.findViewById(R.id.tv_standard);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    public class DimensionsHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mDimension;

        public DimensionsHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_name);
            mDimension = itemView.findViewById(R.id.tv_dimension);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
