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
import com.softtek.lai.module.customermanagement.model.HealthyItem;

import java.util.List;


public class HealthyReportCustomerAdapter extends RecyclerView.Adapter<HealthyReportCustomerAdapter.HealthyReportHolder> {

    private Context context;
    private List<HealthyItem> items;
    private OnItemClickListener listener;
    private boolean isVisitor;

    public HealthyReportCustomerAdapter(List<HealthyItem> items, Context context, boolean isVisitor) {
        this.items = items;
        this.context = context;
        this.isVisitor = isVisitor;
    }

    @Override
    public HealthyReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HealthyReportHolder(LayoutInflater.from(context).inflate(R.layout.healthy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(HealthyReportHolder holder, final int position) {
        HealthyItem item = items.get(position);
        holder.tv_name.setText(item.getTitle());
        holder.tv_standard.setText(item.getCaption());
        if (!TextUtils.isEmpty(item.getColor())) {
            if (item.getColor().startsWith("#")) {
                holder.tv_standard.setTextColor(Color.parseColor(item.getColor()));
            } else {
                holder.tv_standard.setTextColor(Color.parseColor("#" + item.getColor()));
            }
        }
        if (isVisitor) {
            //holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
        } else {
            holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.img_act_detail_more), null);
        }
        if (!TextUtils.isEmpty(item.getValue())) {
            SpannableString ss = new SpannableString(item.getValue() + item.getUnit());
            ss.setSpan(new AbsoluteSizeSpan(36), item.getValue().length(), ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if ("kg/m2".equals(item.getUnit())) {
                ss.setSpan(new SuperscriptSpan(), ss.length() - 1, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            holder.tv_num.setText(ss);
        }
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HealthyReportHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_num;
        TextView tv_standard;
        RelativeLayout rl_item;

        public HealthyReportHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.num);
            tv_standard = (TextView) itemView.findViewById(R.id.tv_standard);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
