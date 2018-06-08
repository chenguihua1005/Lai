
package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.utils.StringUtil;

import java.util.List;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private List<HistoryDataModel.RecordsBean> myItems;
    private ItemListener myListener;
    private Context mContext;

    public HistoryRecyclerViewAdapter(List<HistoryDataModel.RecordsBean> items, ItemListener listener, Context context) {
        myItems = items;
        myListener = listener;
        mContext = context;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_history_select, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
    }

    public interface ItemListener {
        void onItemClick(HistoryDataModel.RecordsBean item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mIcon;
        private TextView mWeek;
        private TextView mTime;
        private TextView mWeight;
        private TextView mBodyFat;
        private TextView mInternalFat;

        // TODO - Your view members
        public HistoryDataModel.RecordsBean item;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.iv_icon);
            mWeek = itemView.findViewById(R.id.tv_week);
            mTime = itemView.findViewById(R.id.tv_time);
            mWeight = itemView.findViewById(R.id.tv_weight);
            mBodyFat = itemView.findViewById(R.id.tv_body_fat);
            mInternalFat = itemView.findViewById(R.id.tv_internal_fat);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(HistoryDataModel.RecordsBean item) {
            this.item = item;
            if (item == null) {
                return;
            }
            if (0 == item.getSourceType()) {
                //莱秤数据
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.laicheng_hand));//laicheng
            } else if (1 == item.getSourceType() || 4 == item.getSourceType()) {
                //复测
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_fuce_icon));
                mIcon.setTextColor(mContext.getResources().getColor(R.color.fuce));
                mIcon.setText("复测");
            } else if (5 == item.getSourceType()) {
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.laicheng_icon));
            } else if (6 == item.getSourceType()) {
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.laicheng_lite_icon));
            } else if (7 == item.getSourceType()) {
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_chuce_icon));
                mIcon.setTextColor(mContext.getResources().getColor(R.color.chuce));
                mIcon.setText("初测");
            } else {
                mIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shoudongluru));
            }

            mWeek.setText(item.getWeek());
            mTime.setText(item.getMeasuredTime());
            SpannableString spannableString = new SpannableString(StringUtil.getFloatValue(item.getWeight()) + "斤");
            spannableString.setSpan(new AbsoluteSizeSpan(30), spannableString.length() - 1, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mWeight.setText(spannableString);
            SpannableString spannable = new SpannableString(StringUtil.getFloatValue(item.getBodyFatRate()) + "%");
            spannable.setSpan(new AbsoluteSizeSpan(30), spannable.length() - 1, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mBodyFat.setText(spannable);
            mInternalFat.setText(StringUtil.getFloatValue(item.getViscusFatIndex()));
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
                                