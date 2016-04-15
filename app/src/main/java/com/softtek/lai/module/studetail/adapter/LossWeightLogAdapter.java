package com.softtek.lai.module.studetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        LogHolder holder=null;
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
        }else if(ZAN_NO.equals(log.getIsClicked())){
            holder.cb_zan.setChecked(true);
            holder.cb_zan.setEnabled(false);
        }
        if(review_flag==0){
            holder.cb_zan.setEnabled(false);
        }
        holder.cb_zan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    log.setPriase(Integer.parseInt(log.getPriase())+1+"");
                    ((CheckBox)buttonView).setEnabled(false);
                    //向服务器提交
                    memberInfopresenter.doZan(3,Long.parseLong(log.getLossLogId()));
                }else{
                    log.setPriase(Integer.parseInt(log.getPriase())-1+"");
                }
                notifyDataSetChanged();
            }
        });
        holder.cb_zan.setText(log.getPriase());
       // Picasso.with(context).load(log.getImgCollectionFirst()).into(holder.iv_image);
        holder.tv_log_title.setText(log.getLogTitle());
        holder.tv_content.setText(log.getLogContent());
        String date=log.getCreateDate();
        String[] dates=date.split("/");
        holder.tv_month.setText(dates[0]);
        holder.tv_day.setText(dates[1]);
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
