/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.ranking.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.ranking.event.RankZan;
import com.softtek.lai.module.ranking.model.OrderData;
import com.softtek.lai.module.ranking.net.RankingService;
import com.softtek.lai.module.sportchart.view.ChartActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/3/27.
 */
public class RankingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ITEM=1;
    private static final int FOOTER=2;
    private static final int EMPTY=3;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<OrderData> infos;
    private Context context;
    private boolean isRunGroup;

    public RankingRecyclerViewAdapter(Context mContext,boolean isRunGroup, List<OrderData> infos) {
        this.context=mContext;
        this.infos = infos;
        this.isRunGroup=isRunGroup;
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if(holder instanceof ViewHolder){
            final OrderData data=infos.get(position);
            if(TextUtils.isEmpty(data.getPhoto())){
                Picasso.with(context).load(R.drawable.img_default).into(((ViewHolder) holder).header_image);
            }else {
                Picasso.with(context).load(AddressManager.get("photoHost")+data.getPhoto())
                        .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(((ViewHolder) holder).header_image);
            }
            ((ViewHolder) holder).header_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(context,ChartActivity.class);
                    intent1.putExtra("isFocusid",data.getAccountId());
                    intent1.putExtra("step",Integer.parseInt(data.getStepCount()));
                    context.startActivity(intent1);
                }
            });
            ((ViewHolder) holder).tv_num.setText(data.get_order());
            ((ViewHolder) holder).tv_name.setText(data.getUserName());
            ((ViewHolder) holder).tv_step.setText(data.getStepCount());
            SpannableString ss=new SpannableString("步");
            ss.setSpan(new AbsoluteSizeSpan(9,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((ViewHolder) holder).tv_step.append(ss);
            if("0".equals(data.getIsPrasie())){
                //未点赞
                ((ViewHolder) holder).cb_zan.setEnabled(true);
                ((ViewHolder) holder).cb_zan.setChecked(false);
                ((ViewHolder) holder).cb_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewHolder) holder).cb_zan.setEnabled(false);
                        ((ViewHolder) holder).cb_zan.setChecked(true);
                        String prasieNum=String.valueOf(Integer.parseInt(data.getPrasieNum())+1);
                        ((ViewHolder) holder).cb_zan.setText(prasieNum);
                        data.setPrasieNum(prasieNum);
                        EventBus.getDefault().post(new RankZan(data.getAcStepGuid(),isRunGroup,false));
                        ZillaApi.NormalRestAdapter.create(RankingService.class)
                                .dayRankZan(UserInfoModel.getInstance().getToken(),
                                        UserInfoModel.getInstance().getUserId(),
                                        data.getAcStepGuid(),
                                        new RequestCallback<ResponseData>() {
                                            @Override
                                            public void success(ResponseData o, Response response) {

                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                ((ViewHolder) holder).cb_zan.setEnabled(true);
                                                ((ViewHolder) holder).cb_zan.setChecked(false);
                                                String prasieNum=String.valueOf(Integer.parseInt(data.getPrasieNum())-1);
                                                ((ViewHolder) holder).cb_zan.setText(prasieNum);
                                                data.setPrasieNum(prasieNum);
                                                super.failure(error);
                                            }
                                        });

                    }
                });
            }else {
                ((ViewHolder) holder).cb_zan.setEnabled(false);
                ((ViewHolder) holder).cb_zan.setChecked(true);
            }
            ((ViewHolder) holder).cb_zan.setText(data.getPrasieNum());
            OrderData firstDate=infos.get(0);
            float step = Float.parseFloat(firstDate.getStepCount());
            float stepPer=90/step;
            int currentStep=Integer.parseInt(data.getStepCount());
            ((ViewHolder) holder).progressBar.setProgress((int) (currentStep*stepPer));
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return infos.size()==0?0:infos.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position+1==getItemCount()){
            type=getItemCount()<10?EMPTY:FOOTER;
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
        public TextView tv_num,tv_name,tv_step;
        public CircleImageView header_image;
        public ProgressBar progressBar;
        public CheckBox cb_zan;


        public ViewHolder(View view) {
            super(view);
            tv_num= (TextView) view.findViewById(R.id.tv_num);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_step= (TextView) view.findViewById(R.id.tv_step);
            header_image= (CircleImageView) view.findViewById(R.id.header_image);
            progressBar= (ProgressBar) view.findViewById(R.id.progress_bar);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
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