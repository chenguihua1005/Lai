package com.softtek.lai.module.healthyreport.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.healthyreport.model.HistoryDataItemModel;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.utils.StringUtil;

import java.util.List;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataAdapter extends BaseAdapter{

    private List<HistoryDataItemModel> dataModels;
    private CheckBox cb_all;
    private Context context;

    public HistoryDataAdapter(Context context, List<HistoryDataItemModel> dataModels,CheckBox cb_all) {
        this.context=context;
        this.dataModels = dataModels;
        this.cb_all=cb_all;
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.history_data_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //填充数据
        final HistoryDataItemModel model=dataModels.get(position);
        if(model.isShow()){
            holder.cb_selecter.setVisibility(View.VISIBLE);
        }else{
            holder.cb_selecter.setVisibility(View.GONE);
        }

        holder.cb_selecter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setChecked(isChecked);
                if(!isChecked){
                    cb_all.setChecked(false);
                }else {
                    //检查是否是全部选中了
                    boolean all=true;
                    for(HistoryDataItemModel itemModel:dataModels){
                        if(!itemModel.isChecked()){
                            all=false;
                            break;
                        }
                    }
                    cb_all.setChecked(all);
                }
            }
        });
        holder.cb_selecter.setChecked(model.isChecked());
        HistoryDataModel.RecordsBean data=model.getDataModel();
        if(0==data.getSourceType()){
            //莱秤数据
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.laichen));
        }else if(1==data.getSourceType()){
            //复测
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.history_data_fuce));
        }else{
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.shoudongluru));
        }
        holder.week.setText(data.getWeek());
        holder.time.setText(data.getMeasuredTime());
        SpannableString spannableString=new SpannableString(StringUtil.getFloatValue(data.getWeight())+"斤");
        spannableString.setSpan(new AbsoluteSizeSpan(30),spannableString.length()-1,spannableString.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.loss_weight.setText(spannableString);
        SpannableString spannable=new SpannableString(StringUtil.getFloatValue(data.getBodyFatRate())+"%");
        spannable.setSpan(new AbsoluteSizeSpan(30),spannable.length()-1,spannable.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.physical.setText(spannable);
        holder.fat.setText(StringUtil.getFloatValue(data.getViscusFatIndex()));
        return convertView;
    }


    private static class ViewHolder{
        public CheckBox cb_selecter;
        public ImageView icon;
        public TextView week;
        public TextView time;
        public TextView loss_weight;
        public TextView physical;
        public TextView fat;

        public ViewHolder(View view){
            cb_selecter= (CheckBox) view.findViewById(R.id.cb_selecter);
            icon= (ImageView) view.findViewById(R.id.iv_icon);
            week= (TextView) view.findViewById(R.id.week);
            time= (TextView) view.findViewById(R.id.time);
            loss_weight= (TextView) view.findViewById(R.id.loss_weight);
            physical= (TextView) view.findViewById(R.id.physical);
            fat= (TextView) view.findViewById(R.id.fat);
        }
    }
}
