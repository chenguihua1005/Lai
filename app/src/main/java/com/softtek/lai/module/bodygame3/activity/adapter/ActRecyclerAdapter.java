package com.softtek.lai.module.bodygame3.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.activity.model.TodayactModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by shelly.xu on 12/6/2016.
 */

public class ActRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<TodayactModel> partnersModels;
    private Context context;
    private int width;

    public ActRecyclerAdapter(Context mContext, List infos) {
        this.context = mContext;
        this.partnersModels = infos;
        width= DisplayUtil.getMobileWidth(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_load_view, parent, false);
            return new FooterHolder(view);
        } else if (viewType == EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //绑定数据
        if (holder instanceof ViewHolder) {
            Log.i("试图加载。/。。。。。。。。。。。。。。。。。。。。。。。。");
            TodayactModel todayactModel = partnersModels.get(position);
            ((ViewHolder) holder).activity_name.setText(todayactModel.getActivityName()+"("+todayactModel.getCount()+")");
             ((ViewHolder) holder).activity_time.setText("集合时间"+todayactModel.getActivityStartDate());
            if(!TextUtils.isEmpty(todayactModel.getActivityIcon())){
              Picasso.with(context).load(AddressManager.get("photoHost")+todayactModel.getActivityIcon()).into(((ViewHolder) holder).activityicon);
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      int position=holder.getPosition();
                        mOnItemClickListener.onItemClick(holder.itemView,position);
                    }
                });
            }
        }

    }

    private SpannableString getString(String value, int size1, int size2, int position) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new AbsoluteSizeSpan(size1, true), 0, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(size2, true), position, value.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return partnersModels.size() == 0 ? 0 : partnersModels.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        int type;
        if (position + 1 == getItemCount()) {
            type = getItemCount() < 10 ? EMPTY : FOOTER;
        } else {
            type = ITEM;
        }
        return type;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView activity_name;
        TextView activity_time;
        ImageView activityicon;
        public ViewHolder(View view) {
            super(view);
            activity_name=(TextView)view.findViewById(R.id.activity_name);
            activity_time=(TextView)view.findViewById(R.id.activity_time);
            activityicon=(ImageView)view.findViewById(R.id.activityicon);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View view) {
            super(view);
        }
    }

    private class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View view) {
            super(view);
        }
    }

    //定义点击接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

