
package com.softtek.lai.module.laicheng_new.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng_new.model.CustomerData;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class ChooseCustomerRecyclerViewAdapter extends RecyclerView.Adapter<ChooseCustomerRecyclerViewAdapter.ViewHolder> {

    private List<CustomerData.ItemsBean> myItems;
    private ItemListener myListener;
    private Context mContext;

    public ChooseCustomerRecyclerViewAdapter(List<CustomerData.ItemsBean> items, ItemListener listener, Context context) {
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
                .inflate(R.layout.recycler_item_choose_customer, parent, false)); // TODO
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
        void onItemClick(CustomerData.ItemsBean item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mHeadView;
        private TextView mUsername;
        private TextView mState;
        private TextView mCreateTime;

        public CustomerData.ItemsBean item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mHeadView = itemView.findViewById(R.id.tv_head);
            mUsername = itemView.findViewById(R.id.tv_username);
            mState = itemView.findViewById(R.id.tv_state);
            mCreateTime = itemView.findViewById(R.id.tv_create_time);
        }

        public void setData(CustomerData.ItemsBean item) {
            this.item = item;
//            if (!TextUtils.isEmpty(item.getPhoto())) {
//                Picasso.with(mContext).load(AddressManager.get("photoHost") + item.getPhoto()).fit().into(mHeadView);
//            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(AddressManager.get("photoHost") + item.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeadView);
            }

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append("由");
            SpannableString str1 = new SpannableString(item.getCreator());
            str1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)), 0, str1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.append(str1);
            builder.append("于");
            SpannableString str2 = new SpannableString(item.getCreatedTime());
            str2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)), 0, str2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.append(str2);

            builder.append("添加");
            mCreateTime.setText(builder);
            String name = item.getName();
            String mobile;
            if (item.isSuperior()){
                mobile = item.getMobile();
            }else {
                mobile = item.getMobile().substring(0,3) + "****" + item.getMobile().substring(7,11);
            }
            mUsername.setText(name + "(" + mobile + ")");
            mState.setText(item.getTagName());
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
