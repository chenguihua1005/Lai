package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.widgets.CircleImageView;

import java.util.List;

/**
 * Created by jerry.guan on 7/12/2016.
 */
public class SPPCAdapter extends BaseAdapter{

    private List<SPPCMoldel> pc;
    private Context context;

    public SPPCAdapter(Context context,List<SPPCMoldel> pc) {
        this.pc = pc;
        this.context=context;
    }

    @Override
    public int getCount() {
        return pc.size();
    }

    @Override
    public Object getItem(int position) {
        return pc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private static class ViewHolder{
        TextView tv_order;
        CircleImageView civ_header;
        CheckBox cb_gender;
        TextView tv_name;
        TextView tv_who;
        TextView tv_total_weight;
        CheckBox cb_mingxing;
        CheckBox cb_fuce;
        TextView tv_xunzhang;

        public ViewHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            civ_header= (CircleImageView) view.findViewById(R.id.civ_header);
            cb_gender= (CheckBox) view.findViewById(R.id.cb_gender);
            
        }

    }
}
