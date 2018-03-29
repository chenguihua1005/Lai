package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.CustomerModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<CustomerModel> modelList;

    public CustomerAdapter(Context context, List<CustomerModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CustomerModel model = modelList.get(position);
        if (!TextUtils.isEmpty(model.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + model.getPhoto()).fit().into(holder.head_image);
        } else {
            Picasso.with(context).load(R.drawable.img_default).fit().into(holder.head_image);
        }

        holder.name_tv.setText(model.getName());

        boolean tag = model.isTag();
        holder.label_tv.setText(model.getTagName());
        if (tag) {//已注册
            if (model.isMarketingStaff()) {
                holder.label_tv.setTextColor(context.getResources().getColor(R.color.red));
                holder.label_tv.setBackground(context.getResources().getDrawable(R.drawable.bg_staff_tip));
                holder.mStaffState.setVisibility(View.VISIBLE);
            } else {
                holder.label_tv.setTextColor(context.getResources().getColor(R.color.history_chart_text_color));
                holder.label_tv.setBackground(null);
                holder.mStaffState.setVisibility(View.GONE);
            }
        } else {//未注册
            holder.label_tv.setTextColor(context.getResources().getColor(R.color.white));
            holder.label_tv.setBackground(context.getResources().getDrawable(R.drawable.bg_tip));
            holder.mStaffState.setVisibility(View.GONE);
        }


        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("由 ");
        SpannableString str1 = new SpannableString(model.getCreator());
        int colorStaff;
        if (model.isMarketingStaff()) {
            colorStaff = R.color.black;
            holder.mContent.setBackground(context.getResources().getDrawable(R.color.colorStaff));
        } else {
            colorStaff = R.color.colorPrimary;
        }
        str1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorStaff)), 0, str1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(str1);
        builder.append(" 于 ");
        SpannableString str2 = new SpannableString(model.getCreatedTime());
        str2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorStaff)), 0, str2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(str2);

        if (!TextUtils.isEmpty(model.getDescription())) {
            builder.append(model.getDescription());
        }
        holder.desc_tv.setText(builder);


        return convertView;
    }

    private final class ViewHolder {
        public ViewHolder(View view) {
            head_image = view.findViewById(R.id.head_image);
            name_tv = view.findViewById(R.id.name_tv);
            label_tv = view.findViewById(R.id.label_tv);
            desc_tv = view.findViewById(R.id.desc_tv);
            mContent = view.findViewById(R.id.rl_content);
            mStaffState = view.findViewById(R.id.tv_staff_state);
        }

        CircleImageView head_image;
        TextView name_tv;
        TextView label_tv;
        TextView desc_tv;
        RelativeLayout mContent;
        TextView mStaffState;
    }
}
