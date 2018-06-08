/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by John on 2016/3/27.
 */
public class LoadMoreRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int ITEM = 1;
    private static final int FOOTER = 2;
    private static final int EMPTY = 3;

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<HomeInfoModel> infos;
    private int width;

    public LoadMoreRecyclerViewAdapter(Context mContext, List<HomeInfoModel> infos) {
        this.mContext = mContext;
        this.infos = infos;
        width = DisplayUtil.getMobileWidth(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            view.setOnClickListener(this);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if (holder instanceof ViewHolder) {
            HomeInfoModel info = infos.get(position);
            Bitmap bitmap = ((ViewHolder) holder).iv_image.getDrawingCache();
            if (bitmap != null && bitmap.isRecycled()) {
                bitmap.recycle();
            }
            Picasso.with(mContext).load(info.getImg_Addr()).resize(width, DisplayUtil.dip2px(mContext, 150))
                    .centerInside().placeholder(R.drawable.default_icon_rect)
                    .error(R.drawable.default_icon_rect).into(((ViewHolder) holder).iv_image);
            ((ViewHolder) holder).tv_title.setText(info.getImg_Title());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return infos.size() == 0 ? 0 : infos.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position + 1 == getItemCount()) {
            type = getItemCount() < 6 ? EMPTY : FOOTER;
        } else {
            type = ITEM;
        }
        return type;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        public TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View view) {
            super(view);
        }
    }

    public class EmptyHolder extends RecyclerView.ViewHolder {

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