package com.softtek.lai.module.historydate.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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
import com.softtek.lai.module.historydate.model.HistoryData;
import com.softtek.lai.module.historydate.model.HistoryDataItemModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.StringUtil;

import java.util.List;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<HistoryDataItemModel> dataModels;
    private CheckBox cb_all;
    private Context context;

    public HistoryDataAdapter(Context context, List<HistoryDataItemModel> dataModels,CheckBox cb_all) {
        this.context=context;
        this.inflater =LayoutInflater.from(context);
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
            convertView=inflater.inflate(R.layout.history_data_item,parent,false);
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
                }
            }
        });
        holder.cb_selecter.setChecked(model.isChecked());
        HistoryData data=model.getDataModel();
        if("0".equals(data.getSourcetype())){
            //莱秤数据
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.laichen));
        }else if("1".equals(data.getSourcetype())){
            //复测
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.history_data_fuce));
        }else if("2".equals(data.getSourcetype())){
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.shoudongluru));
        }
        String date=data.getCreateDate();
        DateUtil util=DateUtil.getInstance();
        int hour=util.getHour(date);
        int minute=util.getMinute(date);
        int month=util.getMonth(date);
        int day=util.getDay(date);
        holder.time.setText((month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute));
        SpannableString spannableString=new SpannableString(StringUtil.getFloatValue(data.getWeight())+"斤");
        spannableString.setSpan(new AbsoluteSizeSpan(30),spannableString.length()-1,spannableString.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.loss_weight.setText(spannableString);
        SpannableString spannable=new SpannableString(StringUtil.getFloatValue(data.getPysical())+"%");
        spannable.setSpan(new AbsoluteSizeSpan(30),spannable.length()-1,spannable.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.physical.setText(spannable);
        holder.fat.setText(StringUtil.getFloatValue(data.getFat()));
        return convertView;
    }

    static class ViewHolder{
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
