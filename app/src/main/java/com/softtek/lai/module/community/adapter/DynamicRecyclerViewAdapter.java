/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.community.model.PersonalListModel;
import com.softtek.lai.module.lossweightstory.view.PictureMoreActivity;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CustomGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by John on 2016/3/27.
 */
public class DynamicRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ITEM=1;
    private static final int FOOTER=2;
    private static final int EMPTY=3;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<PersonalListModel> infos;
    private Context context;

    public DynamicRecyclerViewAdapter(Context mContext, List infos) {
        this.context=mContext;
        this.infos = infos;
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persional_dynamic_item, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }else if(viewType==FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_load_view, parent, false);
            return new FooterHolder(view);
        }else if(viewType==EMPTY){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if(holder instanceof ViewHolder){
            PersonalListModel model=infos.get(position);
            ((ViewHolder) holder).tv_content.setText(model.getContent());
            int[] dates=DateUtil.getInstance().getDates(model.getCreateDate());
            Log.i("日期==="+dates[0]+":"+dates[1]+":"+dates[2]+":"+dates[3]+":"+dates[4]+":"+dates[5]+":"+dates[6]);
            if(position==0){//第一条
                ((ViewHolder) holder).tv_month.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tv_month.setText(getString(dates[2]+""+dates[1]+"月",18,11,2));
            }else {
                PersonalListModel previous=infos.get(position-1);
                int[] pdates=DateUtil.getInstance().getDates(previous.getCreateDate());
                //判断两条数据是否是同一天
                if(dates[1]==pdates[1]&&dates[2]==pdates[2]){//同一天
                    ((ViewHolder) holder).tv_month.setVisibility(View.GONE);
                }else {
                    ((ViewHolder) holder).tv_month.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_month.setText(getString(dates[2]+""+dates[1]+"月",18,11,2));
                }
            }
            ((ViewHolder) holder).tv_time.setText(dates[6]==0?"上午":"下午");
            ((ViewHolder) holder).tv_time.append(dates[3]+":"+dates[4]);
            final String[] imgs = model.getImgCollection().split(",");
            ((ViewHolder) holder).photos.setAdapter(new PhotosAdapter(Arrays.asList(imgs), context));
            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < imgs.length; i++) {
                list.add(imgs[i]);
            }
            ((ViewHolder) holder).photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent in = new Intent(context, PictureMoreActivity.class);
                    in.putStringArrayListExtra("images", list);
                    in.putExtra("position", position);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity((AppCompatActivity) context, in, optionsCompat.toBundle());
                }
            });
            //将数据保存在itemView的Tag中，以便点击时进行获取
            /*holder.itemView.setTag(position);*/
        }

    }

    private SpannableString getString(String value, int size1, int size2,int position) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new AbsoluteSizeSpan(size1,true), 0, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(size2,true), position, value.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return infos.size()==0?0:infos.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position+1==getItemCount()){
            type=getItemCount()<6?EMPTY:FOOTER;
        }else{
            type=ITEM;
        }
        return type;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_month,tv_time,tv_content,tv_delete;
        public CustomGridView photos;

        public ViewHolder(View view) {
            super(view);
            tv_month= (TextView) view.findViewById(R.id.tv_month);
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            tv_time= (TextView) view.findViewById(R.id.tv_time);
            tv_delete= (TextView) view.findViewById(R.id.tv_delete);
            photos= (CustomGridView) view.findViewById(R.id.photos);
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