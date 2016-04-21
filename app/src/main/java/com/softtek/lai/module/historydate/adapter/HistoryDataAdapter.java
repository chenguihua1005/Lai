package com.softtek.lai.module.historydate.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.historydate.model.HistoryData;
import com.softtek.lai.module.historydate.model.HistoryDataItemModel;
import com.softtek.lai.module.historydate.model.HistoryDataModel;

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
        if("1".equals(data.getISGuid())){
            //莱秤数据
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.laichen));
        }else if("0".equals(data.getISGuid())){
            holder.icon.setBackground(ContextCompat.getDrawable(context,R.drawable.shoudongluru));
        }
        String[] date=data.getCreateDate().split(" ");
        holder.tv_ymd.setText(date[0]);
        holder.tv_hm.setText(date[1]);
        return convertView;
    }

    static class ViewHolder{
        public CheckBox cb_selecter;
        public ImageView icon;
        public TextView tv_ymd;
        public TextView tv_hm;

        public ViewHolder(View view){
            cb_selecter= (CheckBox) view.findViewById(R.id.cb_selecter);
            icon= (ImageView) view.findViewById(R.id.iv_icon);
            tv_ymd= (TextView) view.findViewById(R.id.tv_ymd);
            tv_hm= (TextView) view.findViewById(R.id.tv_hm);
        }
    }
}