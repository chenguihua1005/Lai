package com.softtek.lai.module.personalPK.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.personalPK.model.PKListModel;
import com.softtek.lai.widgets.CircleImageView;

import java.util.List;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PKListAdapter extends BaseAdapter{

    private Context context;
    private List<PKListModel> datas;

    public PKListAdapter(Context context, List<PKListModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PKListHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.pklist_item,parent,false);
            holder=new PKListHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (PKListHolder) convertView.getTag();
        }
        //绑定数据
        return convertView;
    }

    static class PKListHolder{
        public TextView tv_status,tv_time,pk_name1,pk_name2;
        public CheckBox cb_zan_right,cb_zan_left;
        public ImageView iv_jiangli,sender1,sender2;
        public CircleImageView sender1_header,sender2_header;

        public PKListHolder(View view){
            tv_status= (TextView) view.findViewById(R.id.tv_status);
            tv_time= (TextView) view.findViewById(R.id.tv_time);
            pk_name1= (TextView) view.findViewById(R.id.tv_pk_name1);
            pk_name2= (TextView) view.findViewById(R.id.tv_pk_name2);
            cb_zan_right= (CheckBox) view.findViewById(R.id.cb_zan_right);
            cb_zan_left= (CheckBox) view.findViewById(R.id.cb_zan_left);
            iv_jiangli= (ImageView) view.findViewById(R.id.iv_jiangli);
            sender1= (ImageView) view.findViewById(R.id.sender1);
            sender2= (ImageView) view.findViewById(R.id.sender2);
            sender1_header= (CircleImageView) view.findViewById(R.id.sender1_header);
            sender2_header= (CircleImageView) view.findViewById(R.id.sender2_header);

        }
    }
}
