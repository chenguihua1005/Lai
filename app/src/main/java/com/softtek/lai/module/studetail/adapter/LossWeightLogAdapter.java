package com.softtek.lai.module.studetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.utils.DateUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/3.
 */
public class LossWeightLogAdapter extends BaseAdapter{

    public static final String ZAN_OFF="0";
    public static final String ZAN_NO="1";

    private LayoutInflater inflater;
    private List<LossWeightLogModel> logs;
    private Context context;
    private IMemberInfopresenter memberInfopresenter;
    private int review_flag=0;

    public  LossWeightLogAdapter(Context context,List<LossWeightLogModel> logs,int review_flag){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.logs=logs;
        memberInfopresenter=new MemberInfoImpl(context,null);
        this.review_flag=review_flag;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LogHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.loss_weight_log_item,parent,false);
            holder=new LogHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (LogHolder) convertView.getTag();
        }
        final LossWeightLogModel log=logs.get(position);
        Log.i(log.toString());
        if(ZAN_OFF.equals(log.getIsClicked())){//未点赞
            holder.cb_zan.setChecked(false);
            holder.cb_zan.setEnabled(true);
            holder.cb_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.cb_zan.isChecked()){
                        log.setPriase(Integer.parseInt(log.getPriase())+1+"");
                        log.setIsClicked(ZAN_NO);
                        //向服务器提交
                        memberInfopresenter.doZan(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()),
                                Long.parseLong(log.getLossLogId()),
                                new Callback<ResponseData<Zan>>() {
                                    @Override
                                    public void success(ResponseData<Zan> zanResponseData, Response response) {}

                                    @Override
                                    public void failure(RetrofitError error) {
                                        log.setPriase(Integer.parseInt(log.getPriase())-1+"");
                                        log.setIsClicked(ZAN_OFF);
                                        notifyDataSetChanged();
                                        ZillaApi.dealNetError(error);
                                    }
                                });
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(ZAN_NO.equals(log.getIsClicked())){
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
        }
        if(review_flag==0){
            holder.cb_zan.setEnabled(false);
        }

        holder.cb_zan.setText(log.getPriase());
        if(StringUtils.isNotEmpty(log.getImgCollectionFirst())){
            Picasso.with(context).load(AddressManager.get("photoHost")+log.getImgCollectionFirst()).fit().placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(holder.iv_image);
        }
        holder.tv_log_title.setText(log.getLogTitle());
        holder.tv_content.setText(log.getLogContent());
        String date=log.getCreateDate();
        holder.tv_month.setText(DateUtil.getInstance().getMonth(date)+"月");
        holder.tv_day.setText(DateUtil.getInstance().getDay(date)+"");
        return convertView;
    }

    private static class LogHolder{

        public TextView tv_day,tv_month,tv_log_title,tv_content;
        public ImageView iv_image;
        public CheckBox cb_zan;

        public LogHolder(View view){
            tv_day= (TextView) view.findViewById(R.id.tv_day);
            tv_month= (TextView) view.findViewById(R.id.tv_month);
            tv_log_title= (TextView) view.findViewById(R.id.tv_log_title);
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
        }
    }
}
